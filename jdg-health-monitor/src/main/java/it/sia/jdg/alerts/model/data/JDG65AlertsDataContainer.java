package it.sia.jdg.alerts.model.data;

public class JDG65AlertsDataContainer extends JDGAlertsDataContainer {

	// /* NOT Used for until */
	// public JDG65AlertsDataContainer(Long nodeFreeMemory, Integer
	// hotRodFreeThreads, Integer hotRodUsedThreads,
	// Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis) {
	// // super(nodeFreeMemory, hotRodFreeThreads, hotRodUsedThreads,
	// // cacheReadLatencyMillis, cacheWriteLatencyMillis);
	// super(nodeFreeMemory, hotRodFreeThreads, hotRodUsedThreads,
	// cacheReadLatencyMillis, cacheWriteLatencyMillis);
	// }

	/* Used for cache related alerts in JDG65 */
	public JDG65AlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads, Integer hotRodUsedThreads,
			Integer clusterSize, String containerHealth) {
		super(nodeFreeMemory, hotRodFreeThreads, hotRodUsedThreads, clusterSize, containerHealth);
	}

	/* Used for cache related alerts in JDG65 */
	public JDG65AlertsDataContainer(Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis) {
		super();
		super.setCacheReadLatencyMillis(cacheReadLatencyMillis);
		super.setCacheWriteLatencyMillis(cacheWriteLatencyMillis);
	}
}