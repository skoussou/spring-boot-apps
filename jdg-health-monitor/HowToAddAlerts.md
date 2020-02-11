# How Alerts work (Adding a new alert)

1. Define new thresholds in _application.properties_ files
![alt text](./icons/1a-definitionOfThresholds.png "Define thresholds in application-jdg7.properties") 
![alt text](./icons/1b-defintionOfThresholds.png "Define thresholds in application-jdg65.properties")
2. Define new member variables in _ThresholdIngenstionImpl.java_ for the ingestion of the thresholds
![alt text](./icons/2-IngestionOfThresholds.png "Ingest new thresholds")
3. Just know that the thresholds end-up in _JDG7HealthAlertsRepository.JDG7HealAlertsThresholdContainer_ (and the equivalent classes for JDG65)
![alt text](./icons/3-AlertsThresholdsRepository.png "Alerts Threshold Repository")
4. The threshold containers will require to be updated
![alt text](./icons/4a-DataMonitorsForAlderts.png "If a common JDG6/JDG7 Threshold")
![alt text](./icons/4b-DataMonitorsJDG7ForAlderts.png "If only for JDG7 Threshold (there is another for JDG6 as well")
5. Implement the rule in the correct category to fire (Implement in _JDGAlertRules.java_ for common, _JDG7AlertRules.java_ for JDG7 only, _JDG65AlertRules.java_ for JDG6 only)
![alt text](./icons/5-AlertRules.png "Cache or CacheContainer/Node/HotRod rule")
6. Apply rules and collect Alerts in _CacheContainerHealthJDG6.runMonitor()_ (or _CacheContainerHealthJDG7.runMonitor()_)
![alt text](./icons/6-Rules-RUnning-Alerts-Collecting.png "Logo Title Text 1")

