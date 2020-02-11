package it.sia.controller.file.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import it.sia.controller.file.ThresholdsIngestion;
import it.sia.jdg.alerts.model.thresholds.JDG65AlertsThresholdsContainer;
import it.sia.jdg.alerts.model.thresholds.JDG7AlertsThresholdsContainer;
import it.sia.jdg.alerts.repository.JDGHealthAlertsRepository;
import it.sia.model.Profile;

/**
 * Ingests thresholds provided by application.properties (filename depndent on
 * profile jdg7|jdg65) and adds them to a repository for checks throughtout the
 * health monitor run
 */
@Component
public class ThresholdsIngestionImpl implements ThresholdsIngestion {

        @Value("${threshold.node.min.free.memory.percent}")
        private Long minNodePercentageFreeMemoryThreshold;

        @Value("${threshold.hotrod.min.free.threads}")
        private Integer minHotRodFreeThreadsThreshold;

        @Value("${threshold.cache.max.read.latency.millis}")
        private Long maxCacheReadLatencyMillisThreshold;

        @Value("${threshold.cache.max.write.latency.millis}")
        private Long maxCacheWriteLatencyMillisThreshold;

        @Value("${threshold.cache.max.read.latency.nanos}")
        private Long maxCacheReadLatencyNanosThreshold;

        @Value("${threshold.cache.max.write.latency.nanos}")
        private Long maxCacheWriteLatencyNanosThreshold;

        @Value("${threshold.cachecontainer.min.clustersize}")
        private Integer minClusterSizeThreshold;

        @Value("${threshold.node.health}")
        private String nodeHealthStatusThreshold;

        @Autowired
        private Environment env;

        @Autowired
        JDGHealthAlertsRepository alertsRepository;

        @PostConstruct
        @Override
        public void runIngestion() {

                List<String> envsList = new ArrayList<String>(Arrays.asList(env.getActiveProfiles()));

                if (envsList.contains(Profile.jdg6.getName())) {
                        debugLogJDG6();

                        alertsRepository.addAlertThresholds(new JDG65AlertsThresholdsContainer(
                                        minNodePercentageFreeMemoryThreshold, minHotRodFreeThreadsThreshold,
                                        maxCacheReadLatencyMillisThreshold, maxCacheWriteLatencyMillisThreshold,
                                        minClusterSizeThreshold, nodeHealthStatusThreshold));

                } else if (envsList.contains(Profile.jdg7.getName())) {
                        debugLogJDG7();

                        alertsRepository.addAlertThresholds(new JDG7AlertsThresholdsContainer(
                                        minNodePercentageFreeMemoryThreshold, minHotRodFreeThreadsThreshold,
                                        maxCacheReadLatencyMillisThreshold, maxCacheWriteLatencyMillisThreshold,
                                        maxCacheReadLatencyNanosThreshold, maxCacheWriteLatencyNanosThreshold,
                                        minClusterSizeThreshold, nodeHealthStatusThreshold));
                } else {
                        throw new RuntimeException("Could not identify a valid Profile based on this values ["
                                        + Profile.values() + "] provided to the application");
                }

        }

        private void debugLogJDG6() {
                System.out.println("++++" + Profile.jdg6.getName()
                                + "++++++++++++ ALERT THRESHOLDS INGESTED +++++++++++++++++++++++++");
                System.out.println("    threshold.node.min.free.memory.percent -> ["
                                + minNodePercentageFreeMemoryThreshold + "]");
                System.out.println("    threshold.hotrod.min.free.threads -> [" + minHotRodFreeThreadsThreshold + "]");
                System.out.println("    threshold.cache.max.read.latency.millis -> ["
                                + maxCacheReadLatencyMillisThreshold + "]");
                System.out.println("    threshold.cache.max.write.latency.millis -> ["
                                + maxCacheWriteLatencyMillisThreshold + "]");
                System.out.println(" threshold.cachecontainer.min.clustersize -> [" + minClusterSizeThreshold + "]");
                System.out.println(" threshold.node.health -> [" + nodeHealthStatusThreshold + "]");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        private void debugLogJDG7() {
                System.out.println("++++" + Profile.jdg7.getName()
                                + "++++++++++++ ALERT THRESHOLDS INGESTED +++++++++++++++++++++++++");
                System.out.println("    threshold.node.min.free.memory.percent -> ["
                                + minNodePercentageFreeMemoryThreshold + "]");
                System.out.println("    threshold.hotrod.min.free.threads -> [" + minHotRodFreeThreadsThreshold + "]");
                System.out.println("    threshold.cache.max.read.latency.millis -> ["
                                + maxCacheReadLatencyMillisThreshold + "]");
                System.out.println("    threshold.cache.max.write.latency.millis -> ["
                                + maxCacheWriteLatencyMillisThreshold + "]");
                System.out.println("    threshold.cache.max.read.latency.nanos -> [" + maxCacheReadLatencyNanosThreshold
                                + "]");
                System.out.println("    threshold.cache.max.write.latency.nanos -> ["
                                + maxCacheWriteLatencyNanosThreshold + "]");
                System.out.println(" threshold.cachecontainer.min.clustersize -> [" + minClusterSizeThreshold + "]");
                System.out.println(" threshold.node.health -> [" + nodeHealthStatusThreshold + "]");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

}