package it.sia.jdg.alerts.model.rules;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.AlertLevel;
import it.sia.jdg.alerts.model.data.JDG7AlertsDataContainer;
import it.sia.jdg.alerts.model.data.JDGAlertsDataContainer;
import it.sia.jdg.alerts.model.thresholds.JDG7AlertsThresholdsContainer;
import it.sia.jdg.alerts.model.thresholds.JDGAlertsThresholdsContainer;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.MonitorType;

/**
 * 
 */
public class JDG7AlertRules extends JDGAlertRules {

  private Map<String, Alert> healthAlerts = new HashMap<String, Alert>();

  @Override
  public Map<String, Alert> fireAllCacheRules(JDGAlertsThresholdsContainer alertthresholds,
      JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp, String cacheName) {

    JDG7AlertsThresholdsContainer thresholds = (JDG7AlertsThresholdsContainer) alertthresholds;
    JDG7AlertsDataContainer data = (JDG7AlertsDataContainer) captureddata;
    super.setMonitorTimestamp(monitorTimestamp);

    /** CACHE RULES */
    assignAlertOnRule(readCacheLatencyMillisAlertRule(thresholds.getMaxCacheReadLatencyMillisThreshold(),
        data.getCacheReadLatencyMillis(), cacheName));
    assignAlertOnRule(writeCacheLatencyMillisAlertRule(thresholds.getMaxCacheWriteLatencyMillisThreshold(),
        data.getCacheWriteLatencyMillis(), cacheName));
    assignAlertOnRule(readCacheLatencyNanosAlertRule(thresholds.getMaxCacheReadLatencyNanosThreshold(),
        data.getCacheReadLatencyNanos(), cacheName));
    assignAlertOnRule(writeCacheLatencyNanosAlertRule(thresholds.getMaxCacheWriteLatencyNanosThreshold(),
        data.getCacheWriteLatencyNanos(), cacheName));

    return this.healthAlerts;
  }

  @Override
  public Map<String, Alert> fireAllNodeCacheContainerRules(JDGAlertsThresholdsContainer alertthresholds,
      JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp) {

    JDG7AlertsThresholdsContainer thresholds = (JDG7AlertsThresholdsContainer) alertthresholds;
    JDG7AlertsDataContainer data = (JDG7AlertsDataContainer) captureddata;
    super.setMonitorTimestamp(monitorTimestamp);

    /** JDG SERVER NODE RULES */
    assignAlertOnRule(
        minPercentNodeFreeMemAlertRule(thresholds.getMinNodePercentageFreeMemoryThreshold(), data.getNodeFreeMemory()));

    /** CACHE CONTAINER RULES */
    assignAlertOnRule(
        minCacheContainerClusterSizeAlertRule(thresholds.getMinClusterSizeThreshold(), data.getClusterSize()));
    assignAlertOnRule(
        clusterContainerHealthAlertRule(thresholds.getNodeHealthStatusThreshold(), data.getContainerHealth()));

    /** HOTROD TRANSPORT RULES */
    assignAlertOnRule(
        minFreeHotRodThreadsAlertRule(thresholds.getMinHotRodFreeThreadsThreshold(), data.getHotRodFreeThreads()));

    return this.healthAlerts;
  }

  @Override
  @Deprecated
  public Map<String, Alert> fireAllRules(JDGAlertsThresholdsContainer thresholds, JDGAlertsDataContainer data,
      LocalDateTime monitorTimestamp) {
    // DO NOT USE
    return null;
  }

  private void assignAlertOnRule(Alert alertInstance) {
    if (alertInstance != null) {
      this.healthAlerts.put(alertInstance.getMonitorKey(), alertInstance);
      System.out.println("***> JDG7AlertRules adding alert for " + alertInstance.toString());
    }
  }

  /**
   * Rule on averageReadTime, in Nanoseconds, to Cache
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  private Alert readCacheLatencyNanosAlertRule(Long threshold, Long monitoredData, String cacheName) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData > threshold) {
        System.out.println("***** RULE [readCacheLatencyNanosAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");
        String monitorKey = cacheName;
        String healthAlertMsg = "Read Cache Latency in Nanos [" + monitoredData + "] > Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.CRITICAL, MonitorType.CACHE,
            super.getMonitorTimestamp(), MonitorKey.averageReadTimeNanos.name());
      }
    }
    return null;
  }

  /**
   * Rule on averageReadTime, in Nanoseconds, from Cache
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  private Alert writeCacheLatencyNanosAlertRule(Long threshold, Long monitoredData, String cacheName) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData > threshold) {
        System.out.println("***** RULE [readCacheLatencyNanosAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = cacheName;
        String healthAlertMsg = "Write Cache Latency in Nanos [" + monitoredData + "] > Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.CRITICAL, MonitorType.CACHE,
            super.getMonitorTimestamp(), MonitorKey.averageWriteTimeNanos.name());
      }
    }
    return null;
  }

}