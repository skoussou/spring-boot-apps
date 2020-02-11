package it.sia.jdg.alerts.model.rules;

import java.time.LocalDateTime;
import java.util.Map;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.data.JDGAlertsDataContainer;
import it.sia.jdg.alerts.model.thresholds.JDGAlertsThresholdsContainer;

public interface AlertRules {

        /**
         * Runs Rules to decide Alerts to be raised against the thresholds of captured
         * monitors. In the future can be replaced by Rules engine and Business Rules
         * 
         * @param thresholds
         * @param data
         * @param LocalDateTime
         * @return
         */
        @Deprecated
        Map<String, Alert> fireAllRules(JDGAlertsThresholdsContainer thresholds, JDGAlertsDataContainer data,
                        LocalDateTime monitorTimestamp);

        /**
         * * Runs Rules to decide Alerts to be raised against the thresholds of captured
         * monitors for a cache. In the future can be replaced by Rules engine and
         * Business Rules
         * 
         * @param alertthresholds
         * @param captureddata
         * @param monitorTimestamp
         * @param cacheName
         * @return
         */
        Map<String, Alert> fireAllCacheRules(JDGAlertsThresholdsContainer alertthresholds,
                        JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp, String cacheName);

        /**
         * * Runs Rules to decide Alerts to be raised against the thresholds of captured
         * monitors for a JDG Node and CacheContainer. In the future can be replaced by
         * Rules engine and Business Rules
         * 
         * @param alertthresholds
         * @param captureddata
         * @param monitorTimestamp
         * @param cacheName
         * @return
         */
        Map<String, Alert> fireAllNodeCacheContainerRules(JDGAlertsThresholdsContainer alertthresholds,
                        JDGAlertsDataContainer captureddata, LocalDateTime monitorTimestamp);
}
