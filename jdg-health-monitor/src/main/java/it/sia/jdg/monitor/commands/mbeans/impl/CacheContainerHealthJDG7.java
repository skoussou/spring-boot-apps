package it.sia.jdg.monitor.commands.mbeans.impl;

import it.sia.controller.file.impl.CSVAlertReporter;
import it.sia.helper.TimeHelper;
import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.data.JDG7AlertsDataContainer;
import it.sia.jdg.alerts.model.rules.JDG7AlertRules;
import it.sia.jdg.alerts.repository.JDGHealthAlertsRepository;
import it.sia.jdg.connection.JMXConnection;
import it.sia.jdg.monitor.commands.mbeans.CacheContainerHealth;
import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.cache.JDG7CacheMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG7CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.JDG7HotRodTransportMonitor;
import it.sia.jdg.monitor.repository.JDGHealthMonitorsRepository;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.jboss.dmr.ModelNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * jboss.datagrid-infinispan:type=CacheManager,name="clustered",component=CacheContainerHealth
 */
@Profile("jdg7")
@Component
public class CacheContainerHealthJDG7 implements CacheContainerHealth {

        @Value("${display.console.cache.logs:True}")
        private Boolean displayConsoleCacheLogs;

        @Autowired
        private JDGMBeanDetailsReposistory mbeanNames;

        @Autowired
        private JMXConnection jmxConnection;

        @Autowired
        private JDGHealthMonitorsRepository healthMonitorRepository;

        @Autowired
        private CSVMonitorReporter metricsReporter;

        @Autowired
        private JDGHealthAlertsRepository healthAlertsRepository;

        @Autowired
        private CSVAlertReporter alertsReporter;

        public CacheContainerHealthJDG7() {
                System.out.println(CacheContainerHealthJDG7.class.getName() + " - Default Constructor");
        }

        public CacheContainerHealthJDG7(JMXConnection jmxConnection, JDGMBeanDetailsReposistory mbeanNames) {
                this.jmxConnection = jmxConnection;
                this.mbeanNames = mbeanNames;

                System.out.println(CacheContainerHealthJDG7.class.getName() + " - Custom Constructor");
        }

        @Override
        public void runMonitor() {

                LocalDateTime monitorTimestamp = TimeHelper.localTimestamp();
                ObjectName cacheContainerHealthMBean = null;
                ObjectName cacheManagerMBean = null;
                ObjectName hotRodTransportMBean = null;

                // Clean alerts generated in the previous run
                healthAlertsRepository.clearGeneratedAlerts();

                try {
                        cacheContainerHealthMBean = new ObjectName(
                                        JDGMBeanDetailsReposistoryJDG7.cacheManagerClusteredCacheContainerHealthMBean);
                        cacheManagerMBean = new ObjectName(JDGMBeanDetailsReposistoryJDG7.cacheManagerClusteredMBean);
                        hotRodTransportMBean = new ObjectName(JDGMBeanDetailsReposistoryJDG7.hotRodTransportMBean);

                        String nodeName = (String) jmxConnection.getClient().getAttribute(cacheManagerMBean,
                                        "nodeAddress");
                        String clusterMembers = (String) jmxConnection.getClient().getAttribute(cacheManagerMBean,
                                        "clusterMembers");

                        Logger.getGlobal().info("*   Cache Container Health: ");

                        String[] healthMbeanAttributesList = new String[] { "numberOfNodes", "cacheHealth",
                                        MonitorKey.clusterHealth.name(), "freeMemoryKb", "totalMemoryKb" };
                        AttributeList healthMbeanList = jmxConnection.getClient()
                                        .getAttributes(cacheContainerHealthMBean, healthMbeanAttributesList);

                        long totalMem = 0;
                        long freeMem = 0;
                        for (Attribute a : healthMbeanList.asList()) {
                                // System.out.println(a.getValue().getClass());
                                if (a.getName().equals("totalMemoryKb"))
                                        totalMem = (Long) a.getValue();
                                if (a.getName().equals("freeMemoryKb"))
                                        freeMem = (Long) a.getValue();
                                Logger.getGlobal().info("*         " + a.getName() + " : " + a.getValue());
                        }
                        Logger.getGlobal().info("*         Nodes in cluster :" + clusterMembers);
                        Logger.getGlobal().info("*         Available Memory on Node [" + nodeName + "] : "
                                        + ((freeMem * 100) / totalMem) + "%");

                        /*
                         * *************** Metrics Capture for CacheContainer + Nodes ****************
                         */
                        System.out.println(healthMbeanList);

                        JDG7CacheContainerNodeMonitor nodeMetrics = new JDG7CacheContainerNodeMonitor(monitorTimestamp,
                                        (Integer) jmxConnection.getClient().getAttribute(
                                                        cacheContainerHealthMBean, MonitorKey.numberOfNodes.name()),
                                        clusterMembers, nodeName,
                                        (String) jmxConnection.getClient().getAttribute(cacheContainerHealthMBean,
                                                        MonitorKey.clusterHealth.name()),
                                        (Long) jmxConnection.getClient().getAttribute(cacheContainerHealthMBean,
                                                        "freeMemoryKb"),
                                        (Long) jmxConnection.getClient().getAttribute(cacheContainerHealthMBean,
                                                        "totalMemoryKb"));

                        // Latest Metrics In Memory Storage
                        healthMonitorRepository.addCacheContainerAndNodeHealthMonitors(nodeMetrics);
                        // Store in File for historical metrics track
                        metricsReporter.writeNodeMonitorEntry(nodeMetrics.convertToCSV());

                        /* *************** Metrics Capture for Hot Rod Transport **************** */
                        JDG7HotRodTransportMonitor hotRodMetrics = new JDG7HotRodTransportMonitor(monitorTimestamp,
                                        (Integer) jmxConnection.getClient().getAttribute(hotRodTransportMBean,
                                                        "activeCount"),
                                        (Integer) jmxConnection.getClient().getAttribute(hotRodTransportMBean,
                                                        "largestPoolSize"),
                                        (Integer) jmxConnection.getClient().getAttribute(hotRodTransportMBean,
                                                        "maximumPoolSize"));

                        // Latest Metrics In Memory Storage
                        healthMonitorRepository.addCacheHotRodTransportHealthMonitors(hotRodMetrics);
                        // Store in File for historical metrics track
                        metricsReporter.writeHotRodeMonitorEntry(hotRodMetrics.convertToCSV());
                        // Process captured data to gnerate possible alerts
                        processNodeContainerAlerting(
                                        new JDG7AlertsDataContainer(nodeMetrics.getAvailableMemoryOnNodeNum(),
                                                        (hotRodMetrics.getHotRodThreads()
                                                                        - hotRodMetrics.getActiveCount()),
                                                        hotRodMetrics.getLargestPoolSize(),
                                                        nodeMetrics.getClusterSize(), nodeMetrics.getClusterHealth()),
                                        monitorTimestamp);

                        /* *************** Metrics Capture per Cache **************** */
                        for (String cacheMBeanName : mbeanNames.getCacheMBeanNamesUniqueStartStrList()) {

                                // boss.datagrid-infinispan:type=Cache,name="AUTOTRADER_PRD_CL12_QUOTE(dist_sync)"
                                String cacheName = cacheMBeanName.substring(cacheMBeanName.lastIndexOf("=") + 2,
                                                cacheMBeanName.length() - 1);

                                ObjectName cacheRpcManagerMBean = new ObjectName(
                                                cacheMBeanName + ",manager=\"clustered\",component=RpcManager");
                                ObjectName cacheStateTransferManagerMBean = new ObjectName(cacheMBeanName
                                                + ",manager=\"clustered\",component=StateTransferManager");
                                ObjectName cacheClusterCacheStatsMBean = new ObjectName(
                                                cacheMBeanName + ",manager=\"clustered\",component=ClusterCacheStats");

                                JDG7CacheMonitor cacheMetrics = new JDG7CacheMonitor(monitorTimestamp, cacheName,
                                                (String) jmxConnection.getClient().getAttribute(
                                                                cacheStateTransferManagerMBean, "rebalancingStatus"),
                                                (Long) jmxConnection.getClient().getAttribute(
                                                                cacheClusterCacheStatsMBean,
                                                                MonitorKey.averageReadTime.name()),
                                                (Long) jmxConnection.getClient().getAttribute(
                                                                cacheClusterCacheStatsMBean,
                                                                MonitorKey.averageWriteTime.name()),
                                                (Long) jmxConnection.getClient().getAttribute(
                                                                cacheClusterCacheStatsMBean,
                                                                MonitorKey.averageReadTimeNanos.name()),
                                                (Long) jmxConnection.getClient().getAttribute(
                                                                cacheClusterCacheStatsMBean,
                                                                MonitorKey.averageWriteTimeNanos.name()));

                                if (displayConsoleCacheLogs) {
                                        displayCacheMonitors(cacheMetrics, nodeName, (String) jmxConnection.getClient()
                                                        .getAttribute(cacheRpcManagerMBean, "committedViewAsString"));
                                }

                                // Latest Metrics In Memory Storage
                                healthMonitorRepository.addCacheHealthMonitors(cacheMetrics);
                                // Store in File for historical metrics track
                                metricsReporter.writeCacheMonitorEntry(cacheMetrics.convertToCSV());
                                // Process captured data to gnerate possible alerts
                                processCacheAlerting(
                                                new JDG7AlertsDataContainer(
                                                                cacheMetrics.getClusteredCacheAvgReadTimeMillis(),
                                                                cacheMetrics.getClusteredCacheAvgWriteTimeMillis(),
                                                                cacheMetrics.getClusteredCacheAvgReadTimeNanos(),
                                                                cacheMetrics.getClusteredCacheAvgWriteTimeNanos()),
                                                monitorTimestamp, cacheName);

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

        private void processCacheAlerting(JDG7AlertsDataContainer captureddata, LocalDateTime monitorTimestamp,
                        String cacheName) {

                JDG7AlertRules alertRules = new JDG7AlertRules();
                Map<String, Alert> alertNodeContainerResults = alertRules.fireAllCacheRules(
                                healthAlertsRepository.getAlertThresholds(), captureddata, monitorTimestamp, cacheName);

                healthAlertsRepository.addGeneratedAlerts(alertNodeContainerResults);
        }

        private void processNodeContainerAlerting(JDG7AlertsDataContainer captureddata,
                        LocalDateTime monitorTimestamp) {

                JDG7AlertRules alertRules = new JDG7AlertRules();
                Map<String, Alert> alertNodeContainerResults = alertRules.fireAllNodeCacheContainerRules(
                                healthAlertsRepository.getAlertThresholds(), captureddata, monitorTimestamp);

                healthAlertsRepository.addGeneratedAlerts(alertNodeContainerResults);
        }

        private void reportAlerts() throws IOException {
                Map<String, Alert> generatedAlerts = healthAlertsRepository.getGeneratedAlerts();

                for (Entry<String, Alert> alertEntry : generatedAlerts.entrySet()) {
                        alertsReporter.writeAlertsMonitorEntry(alertEntry.getValue().convertToCSV());
                }
        }

        private void displayCacheMonitors(JDG7CacheMonitor cacheMetrics, String nodeName,
                        String committedClusterViewAsString) {
                Logger.getGlobal().info("");
                Logger.getGlobal().info("*---------CACHE [" + cacheMetrics.getCacheName()
                                + "]-------------------------------------" + "(" + nodeName + ")-----------");
                Logger.getGlobal().info(
                                "*            JDG Cache Nodes View             : " + committedClusterViewAsString);
                Logger.getGlobal().info("*            Cache Nodes Rebalaning Status    : "
                                + cacheMetrics.getCacheRebalancingStatus());
                Logger.getGlobal().info("*            Cache AVG Read Latency (nanos)   : "
                                + cacheMetrics.getClusteredCacheAvgReadTimeNanos());
                Logger.getGlobal().info("*            Cache AVG Read Latency (millis)  : "
                                + cacheMetrics.getClusteredCacheAvgReadTimeMillis());
                Logger.getGlobal().info("*            Cache AVG Write Latency (nanos)  : "
                                + cacheMetrics.getClusteredCacheAvgWriteTimeNanos());
                Logger.getGlobal().info("*            Cache AVG Write Latency (millis) : "
                                + cacheMetrics.getClusteredCacheAvgWriteTimeMillis());
        }
}