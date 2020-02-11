package it.sia.jdg.monitor.commands.mbeans.impl;

import it.sia.controller.file.impl.CSVAlertReporter;
import it.sia.helper.TimeHelper;
import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.data.JDG65AlertsDataContainer;
import it.sia.jdg.alerts.model.rules.JDG65AlertRules;
import it.sia.jdg.alerts.repository.JDGHealthAlertsRepository;
import it.sia.jdg.connection.JMXConnection;
import it.sia.jdg.monitor.commands.mbeans.CacheContainerHealth;
import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.cache.JDG65CacheMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG65CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.JDG65HotRodTransportMonitor;
import it.sia.jdg.monitor.repository.JDGHealthMonitorsRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * jboss.datagrid-infinispan:type=CacheManager,name="clustered",component=CacheContainerHealth
 */
@Profile("jdg65")
@Component
public class CacheContainerHealthJDG6 implements CacheContainerHealth {

  @Value("${display.console.cache.logs:True}")
  private Boolean displayConsoleCacheLogs;

  @Autowired
  private JDGMBeanDetailsReposistory mbeanNames;

  @Autowired
  private JMXConnection jmxConnection;

  @Autowired
  private Environment env;

  @Autowired
  private JDGHealthMonitorsRepository healthMonitorRepository;

  @Autowired
  private CSVMonitorReporter metricsReporter;

  @Autowired
  private JDGHealthAlertsRepository healthAlertsRepository;

  @Autowired
  private CSVAlertReporter alertsReporter;

  public CacheContainerHealthJDG6() {
    System.out.println(CacheContainerHealthJDG6.class.getName() + " - Default Constructor");
  }

  public CacheContainerHealthJDG6(JMXConnection jmxConnection, JDGMBeanDetailsReposistory mbeanNames) {
    this.jmxConnection = jmxConnection;
    this.mbeanNames = mbeanNames;
    System.out.println(CacheContainerHealthJDG6.class.getName() + " - Custom Constructor");
  }

  @Override
  public void runMonitor() {

    LocalDateTime monitorTimestamp = TimeHelper.localTimestamp();
    ObjectName cacheManagerMBean = null;
    ObjectName hotRodTransportMBean = null;

    // Clean alerts generated in the previous run
    healthAlertsRepository.clearGeneratedAlerts();

    try {
      cacheManagerMBean = new ObjectName(JDGMBeanDetailsReposistoryJDG65.cacheManagerClusteredMBeanJDG6);
      hotRodTransportMBean = new ObjectName(JDGMBeanDetailsReposistoryJDG65.hotRodTransportMBeanJDG6);

      String nodeName = (String) jmxConnection.getClient().getAttribute(cacheManagerMBean, "nodeAddress");
      String clusterMembers = (String) jmxConnection.getClient().getAttribute(cacheManagerMBean, "clusterMembers");

      Logger.getGlobal().info("*   Cache Container Health: ");
      Logger.getGlobal().info("*         Nodes in cluster :" + clusterMembers);

      /* *************** Metrics Capture for CacheContainer & Node **************** */
      JDG65CacheContainerNodeMonitor nodeMetrics = new JDG65CacheContainerNodeMonitor(monitorTimestamp,
          (Integer) jmxConnection.getClient().getAttribute(cacheManagerMBean, "clusterSize"),
          (String) jmxConnection.getClient().getAttribute(cacheManagerMBean, "clusterMembers"), nodeName,
          (String) jmxConnection.getClient().getAttribute(cacheManagerMBean, "cacheManagerStatus"),
          (String) jmxConnection.getClient().getAttribute(cacheManagerMBean, "runningCacheCount"));

      // Latest Metrics In Memory Storage
      healthMonitorRepository.addCacheContainerAndNodeHealthMonitors(nodeMetrics);
      // Store in File for historical metrics track
      metricsReporter.writeNodeMonitorEntry(nodeMetrics.convertToCSV());

      /* *************** Metrics Capture for HotRod Transport **************** */
      JDG65HotRodTransportMonitor hotRodMetrics = new JDG65HotRodTransportMonitor(monitorTimestamp,
          (Integer) jmxConnection.getClient().getAttribute(hotRodTransportMBean, "numberOfLocalConnections"),
          (String) jmxConnection.getClient().getAttribute(hotRodTransportMBean, "numberWorkerThreads"));

      // Latest Metrics In Memory Storage
      healthMonitorRepository.addCacheHotRodTransportHealthMonitors(hotRodMetrics);
      // Store in File for historical metrics track
      metricsReporter.writeHotRodeMonitorEntry(hotRodMetrics.convertToCSV());
      // Process captured data to gnerate possible alerts
      processNodeContainerAlerting(new JDG65AlertsDataContainer(null,
          (hotRodMetrics.getHotRodThreads() - hotRodMetrics.getNumberOfLocalConnections()), null,
          nodeMetrics.getClusterSize(), nodeMetrics.getCacheContainerStatus()), monitorTimestamp);

      /* *************** Metrics Capture per Cache **************** */
      for (String cacheMBeanName : mbeanNames.getCacheMBeanNamesUniqueStartStrList()) {

        String cacheName = cacheMBeanName.substring(cacheMBeanName.lastIndexOf("=") + 2, cacheMBeanName.length() - 1);

        ObjectName cacheRpcManagerMBean = new ObjectName(
            cacheMBeanName + ",manager=\"clustered\",component=RpcManager");
        ObjectName cacheStateTransferManagerMBean = new ObjectName(
            cacheMBeanName + ",manager=\"clustered\",component=StateTransferManager");
        ObjectName cacheClusterCacheStatsMBean = new ObjectName(
            cacheMBeanName + ",manager=\"clustered\",component=ClusterCacheStats");

        JDG65CacheMonitor cacheMetrics = new JDG65CacheMonitor(monitorTimestamp, cacheName,
            (Boolean) jmxConnection.getClient().getAttribute(cacheStateTransferManagerMBean, "joinComplete")
                ? "COMPLETE"
                : "IN_PROGRESS",
            (Long) jmxConnection.getClient().getAttribute(cacheClusterCacheStatsMBean,
                MonitorKey.averageReadTime.name()),
            (Long) jmxConnection.getClient().getAttribute(cacheClusterCacheStatsMBean,
                MonitorKey.averageWriteTime.name()));

        if (displayConsoleCacheLogs) {
          displayCacheMonitors(cacheName, nodeName,
              (String) jmxConnection.getClient().getAttribute(cacheRpcManagerMBean, "committedViewAsString"),
              cacheMetrics);
        }

        // Latest Metrics In Memory Storage
        healthMonitorRepository.addCacheHealthMonitors(cacheMetrics);
        // Store in File for historical metrics track
        metricsReporter.writeCacheMonitorEntry(cacheMetrics.convertToCSV());
        // Process captured data to gnerate possible alerts
        processCacheAlerting(new JDG65AlertsDataContainer(cacheMetrics.getClusteredCacheAvgReadTimeMillis(),
            cacheMetrics.getClusteredCacheAvgWriteTimeMillis()), monitorTimestamp, cacheName);
      }

      // Writes Alerts in file
      reportAlerts();

    } catch (MalformedObjectNameException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstanceNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // } catch (IntrospectionException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
    } catch (ReflectionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (AttributeNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MBeanException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void processCacheAlerting(JDG65AlertsDataContainer captureddata, LocalDateTime monitorTimestamp,
      String cacheName) {
    JDG65AlertRules alertRules = new JDG65AlertRules();
    Map<String, Alert> alertNodeContainerResults = alertRules
        .fireAllCacheRules(healthAlertsRepository.getAlertThresholds(), captureddata, monitorTimestamp, cacheName);

    healthAlertsRepository.addGeneratedAlerts(alertNodeContainerResults);
  }

  private void processNodeContainerAlerting(JDG65AlertsDataContainer captureddata, LocalDateTime monitorTimestamp) {
    JDG65AlertRules alertRules = new JDG65AlertRules();
    Map<String, Alert> alertNodeContainerResults = alertRules
        .fireAllNodeCacheContainerRules(healthAlertsRepository.getAlertThresholds(), captureddata, monitorTimestamp);

    healthAlertsRepository.addGeneratedAlerts(alertNodeContainerResults);
  }

  private void reportAlerts() throws IOException {
    Map<String, Alert> generatedAlerts = healthAlertsRepository.getGeneratedAlerts();

    for (Entry<String, Alert> alertEntry : generatedAlerts.entrySet()) {
      alertsReporter.writeAlertsMonitorEntry(alertEntry.getValue().convertToCSV());
    }

  }

  private void displayCacheMonitors(String cacheName, String nodeName, String committedClusterViewAsString,
      JDG65CacheMonitor cacheMetrics) {
    Logger.getGlobal().info("");
    Logger.getGlobal().info(
        "*---------CACHE [" + cacheName + "]-------------------------------------" + "(" + nodeName + ")-----------");
    Logger.getGlobal().info("*            JDG Cache Nodes View             : " + committedClusterViewAsString);
    Logger.getGlobal()
        .info("*            Cache Nodes Rebalaning Status    : " + cacheMetrics.getCacheRebalancingStatus());
    Logger.getGlobal()
        .info("*            Cache AVG Read Latency (millis)  : " + cacheMetrics.getClusteredCacheAvgReadTimeMillis());
    Logger.getGlobal()
        .info("*            Cache AVG Write Latency (millis) : " + cacheMetrics.getClusteredCacheAvgWriteTimeMillis());
  }
}