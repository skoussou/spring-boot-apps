package it.sia.jdg.alerts.model.rules;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.AlertLevel;
import it.sia.jdg.alerts.model.data.JDG65AlertsDataContainer;
import it.sia.jdg.alerts.model.data.JDG7AlertsDataContainer;
import it.sia.jdg.alerts.model.data.JDGAlertsDataContainer;
import it.sia.jdg.alerts.model.thresholds.JDG65AlertsThresholdsContainer;
import it.sia.jdg.alerts.model.thresholds.JDG7AlertsThresholdsContainer;
import it.sia.jdg.alerts.model.thresholds.JDGAlertsThresholdsContainer;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.MonitorType;

/**
 * 
 */
public class JDG65AlertRules extends JDGAlertRules {

  private Map<String, Alert> healthAlerts = new HashMap<String, Alert>();

  @Override
  public Map<String, Alert> fireAllCacheRules(JDGAlertsThresholdsContainer alertthresholds,
      JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp, String cacheName) {

    JDG65AlertsThresholdsContainer thresholds = (JDG65AlertsThresholdsContainer) alertthresholds;
    JDG65AlertsDataContainer data = (JDG65AlertsDataContainer) captureddata;
    super.setMonitorTimestamp(monitorTimestamp);

    /** CACHE RULES */
    assignAlertOnRule(readCacheLatencyMillisAlertRule(thresholds.getMaxCacheReadLatencyMillisThreshold(),
        data.getCacheReadLatencyMillis(), cacheName));
    assignAlertOnRule(writeCacheLatencyMillisAlertRule(thresholds.getMaxCacheWriteLatencyMillisThreshold(),
        data.getCacheWriteLatencyMillis(), cacheName));

    return this.healthAlerts;
  }

  @Override
  public Map<String, Alert> fireAllNodeCacheContainerRules(JDGAlertsThresholdsContainer alertthresholds,
      JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp) {

    JDG65AlertsThresholdsContainer thresholds = (JDG65AlertsThresholdsContainer) alertthresholds;
    JDG65AlertsDataContainer data = (JDG65AlertsDataContainer) captureddata;
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
      System.out.println("***> JDG65AlertRules adding alert for " + alertInstance.toString());

    }
  }

}