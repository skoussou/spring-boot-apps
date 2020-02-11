package it.sia.jdg.alerts.model.thresholds;

public abstract class JDGAlertsThresholdsContainer {

  private Long minNodePercentageFreeMemoryThreshold;

  private Integer minHotRodFreeThreadsThreshold;

  private Long maxCacheReadLatencyMillisThreshold;

  private Long maxCacheWriteLatencyMillisThreshold;

  private Integer minClusterSizeThreshold;

  private String nodeHealthStatusThreshold;

  // public JDGAlertsThresholdsContainer(Long
  // minNodePercentageFreeMemoryThreshold, Integer minHotRodFreeThreadsThreshold,
  // Long maxCacheReadLatencyMillisThreshold, Long
  // maxCacheWriteLatencyMillisThreshold) {
  // this.minNodePercentageFreeMemoryThreshold =
  // minNodePercentageFreeMemoryThreshold;
  // this.minHotRodFreeThreadsThreshold = minHotRodFreeThreadsThreshold;
  // this.maxCacheReadLatencyMillisThreshold = maxCacheReadLatencyMillisThreshold;
  // this.maxCacheWriteLatencyMillisThreshold =
  // maxCacheWriteLatencyMillisThreshold;
  // }

  public JDGAlertsThresholdsContainer(Long minNodePercentageFreeMemoryThreshold, Integer minHotRodFreeThreadsThreshold,
      Long maxCacheReadLatencyMillisThreshold, Long maxCacheWriteLatencyMillisThreshold,
      Integer minClusterSizeThreshold, String nodeHealthStatusThreshold) {
    this.minNodePercentageFreeMemoryThreshold = minNodePercentageFreeMemoryThreshold;
    this.minHotRodFreeThreadsThreshold = minHotRodFreeThreadsThreshold;
    this.maxCacheReadLatencyMillisThreshold = maxCacheReadLatencyMillisThreshold;
    this.maxCacheWriteLatencyMillisThreshold = maxCacheWriteLatencyMillisThreshold;
    this.minClusterSizeThreshold = minClusterSizeThreshold;
    this.nodeHealthStatusThreshold = nodeHealthStatusThreshold;
  }

  public Long getMinNodePercentageFreeMemoryThreshold() {
    return minNodePercentageFreeMemoryThreshold;
  }

  public void setMinNodePercentageFreeMemoryThreshold(Long minNodePercentageFreeMemoryThreshold) {
    this.minNodePercentageFreeMemoryThreshold = minNodePercentageFreeMemoryThreshold;
  }

  public Integer getMinHotRodFreeThreadsThreshold() {
    return minHotRodFreeThreadsThreshold;
  }

  public void setMinHotRodFreeThreadsThreshold(Integer minHotRodFreeThreadsThreshold) {
    this.minHotRodFreeThreadsThreshold = minHotRodFreeThreadsThreshold;
  }

  public Long getMaxCacheReadLatencyMillisThreshold() {
    return maxCacheReadLatencyMillisThreshold;
  }

  public void setMaxCacheReadLatencyMillisThreshold(Long maxCacheReadLatencyMillisThreshold) {
    this.maxCacheReadLatencyMillisThreshold = maxCacheReadLatencyMillisThreshold;
  }

  public Long getMaxCacheWriteLatencyMillisThreshold() {
    return maxCacheWriteLatencyMillisThreshold;
  }

  public void setMaxCacheWriteLatencyMillisThreshold(Long maxCacheWriteLatencyMillisThreshold) {
    this.maxCacheWriteLatencyMillisThreshold = maxCacheWriteLatencyMillisThreshold;
  }

  public Integer getMinClusterSizeThreshold() {
    return minClusterSizeThreshold;
  }

  public void setMinClusterSizeThreshold(Integer minClusterSizeThreshold) {
    this.minClusterSizeThreshold = minClusterSizeThreshold;
  }

  public String getNodeHealthStatusThreshold() {
    return nodeHealthStatusThreshold;
  }

  public void setNodeHealthStatusThreshold(String nodeHealthStatusThreshold) {
    this.nodeHealthStatusThreshold = nodeHealthStatusThreshold;
  }

  // @Override
  // public String toString() {
  // return "maxCacheReadLatencyMillisThreshold=" +
  // maxCacheReadLatencyMillisThreshold
  // + ", maxCacheWriteLatencyMillisThreshold=" +
  // maxCacheWriteLatencyMillisThreshold
  // + ", minHotRodFreeThreadsThreshold=" + minHotRodFreeThreadsThreshold + ",
  // minNodePercentageFreeMemoryThreshold="
  // + minNodePercentageFreeMemoryThreshold + "]";
  // }

  @Override
  public String toString() {
    return "JDGAlertsThresholdsContainer [maxCacheReadLatencyMillisThreshold=" + maxCacheReadLatencyMillisThreshold
        + ", maxCacheWriteLatencyMillisThreshold=" + maxCacheWriteLatencyMillisThreshold + ", minClusterSizeThreshold="
        + minClusterSizeThreshold + ", minHotRodFreeThreadsThreshold=" + minHotRodFreeThreadsThreshold
        + ", minNodePercentageFreeMemoryThreshold=" + minNodePercentageFreeMemoryThreshold
        + ", nodeHealthStatusThreshold=" + nodeHealthStatusThreshold + "]";
  }

}