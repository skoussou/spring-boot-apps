package it.sia.jdg.alerts.model.rules;

import java.time.LocalDateTime;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.AlertLevel;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.MonitorType;

/**
 * Colntains the JDG Alert Rules commong in JDG6 and JDG7
 * 
 * @author Stelios Kousouris
 */
public abstract class JDGAlertRules implements AlertRules {

  private LocalDateTime monitorTimestamp;

  public LocalDateTime getMonitorTimestamp() {
    return monitorTimestamp;
  }

  public void setMonitorTimestamp(LocalDateTime monitorTimestamp) {
    this.monitorTimestamp = monitorTimestamp;
  }

  /**
   * Rule on the state of Cache Container Health cachecontainer
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  protected Alert clusterContainerHealthAlertRule(String threshold, String monitoredData) {

    if (threshold != null && monitoredData != null) {

      if (!monitoredData.equals(threshold)) {
        System.out.println("***** RULE [clusterContainerHealthAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = MonitorKey.clusterHealth.name();
        String healthAlertMsg = "CacheContainer Cluster Health [" + monitoredData + "] != Threshold [" + threshold
            + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.ERROR, MonitorType.CACHE_CONTAINER, monitorTimestamp,
            MonitorKey.clusterHealth.name());
      }
    }
    return null;
  }

  /**
   * Rule on the number of JDG Nodes recorded in a cluster for a given
   * cachecontainer
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  protected Alert minCacheContainerClusterSizeAlertRule(Integer threshold, Integer monitoredData) {

    if (threshold != null && monitoredData != null) {
      if (monitoredData < threshold) {
        System.out.println("***** RULE [minCacheContainerClusterSizeAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = MonitorKey.numberOfNodes.name();
        String healthAlertMsg = "CacheContainer Cluster Size [" + monitoredData + "] < Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.ERROR, MonitorType.CACHE_CONTAINER, monitorTimestamp,
            MonitorKey.clustersize.name());
      }
    }
    return null;
  }

  /**
   * Rule on free Hot Rod Threads in the Hot Rod Server. Checking if free threads
   * are less than threshold
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  protected Alert minFreeHotRodThreadsAlertRule(Integer threshold, Integer monitoredData) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData < threshold) {
        System.out.println("***** RULE [minFreeHotRodThreadsAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = MonitorKey.freehotrodtreads.name();
        String healthAlertMsg = "Hot Rod Available Threads [" + monitoredData + "] < Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.WARNING, MonitorType.HOTROD_TRANSPORT, monitorTimestamp,
            MonitorKey.freehotrodtreads.name());
      }
    }
    return null;
  }

  /**
   * Rule on percentage of free memory in the Node
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  protected Alert minPercentNodeFreeMemAlertRule(Long threshold, Long monitoredData) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData < threshold) {
        System.out.println("***** RULE [minPercentNodeFreeMemAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = MonitorKey.freeMemoryPercent.name();
        String healthAlertMsg = "Node Free Memory Percentage [" + monitoredData + "] < Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.CRITICAL, MonitorType.JDG_NODE, monitorTimestamp,
            MonitorKey.freeMemoryPercent.name());
      }
    }
    return null;
  }

  /**
   * Rule on averageReadTime, in Milliseconds, to Cache
   * 
   * @param threshold
   * @param monitoredData
   * @param cacheName
   * @return
   */
  protected Alert readCacheLatencyMillisAlertRule(Long threshold, Long monitoredData, String cacheName) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData > threshold) {
        System.out.println("***** RULE [readCacheLatencyMillisAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = cacheName;
        String healthAlertMsg = "Read Cache Latency [" + monitoredData + "] > Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.CRITICAL, MonitorType.CACHE, monitorTimestamp,
            MonitorKey.averageReadTime.name());
      }
    }
    return null;
  }

  /**
   * Rule on averageReadTime, in Milliseconds, from Cache
   * 
   * @param threshold
   * @param monitoredData
   * @return
   */
  protected Alert writeCacheLatencyMillisAlertRule(Long threshold, Long monitoredData, String cacheName) {
    if (threshold != null && monitoredData != null) {
      if (monitoredData > threshold) {
        System.out.println("***** RULE [writeCacheLatencyMillisAlertRule] fired threshold [" + threshold
            + "] monitoredData[" + monitoredData + "]*****");

        String monitorKey = cacheName;
        String healthAlertMsg = "Write Cache Latency [" + monitoredData + "] > Threshold [" + threshold + "]";
        return new Alert(monitorKey, healthAlertMsg, AlertLevel.CRITICAL, MonitorType.CACHE, monitorTimestamp,
            MonitorKey.averageWriteTime.name());
      }
    }
    return null;
  }

}