# jdg-health-monitor

## Purpose

A standalone Java application to monitor JDG Cluster and its caches healthiness

- Runs on primary DC side by side with one JDG Cluster to monitor it
- Exposes REST APIs to retrieve healthiness 
  1. status of a all caches found in the node (ready to receive. Is it clustered)
  2. status of a cache (ready to receive. Is it clustered)
  3. Latency
     - Writes (correlation with threads, memory)
     - Reads (correlation with entries number, threads, memory)
  4. status of threadpools on the server (reaching point of utilization). 
  5. JVM/GC Behavior
 
- Health REST API exposes only the latest info and push the job of keeping historical data out to another tool (JMX/REST)
- Application keeps locally in a file the historical information about:
  1. Monitors of JDG Node and cachecontainer ("clustered")
  2. HotRod transport
  3. Cache (for all caches) info on latency etc.


## How to Run

To provide external configuration of the JDG Servers you can provide, relative to the path you will run the following 2 commands, a _'/config'_ directory with application-[jdg65|jdg7].properties file defining the configurations similar to the [/config](/config) directory in this repository

- For Development Use
```
mvn spring-boot:run -Dspring-boot.run.profiles=[jdg65|jdg7]
```
- For Production Use
```
java -jar -Dspring.profiles.active=[jdg65|jdg7] target/jdg-health-monitor-0.0.1-SNAPSHOT.jar
```
### Configure It
- Application Configuration
	- server.port=<PORT TO EXPOSE THE REST APIS>
	- display.console.cache.logs=f<LOG VERBOSLY CACHE Monitors)
- JDG JMX Connection 
	- jmx.host=<Management URL for the JDG/EAP nodE)
	- jmx.port=<Management port>
	- jmx.url=<see link below>
	- jmx.user=<admin user>
	- jmx.password=<admin password>
	- [How to access MBeanServerConnection from remote JMX Client in JBoss EAP 7](https://access.redhat.com/solutions/2604501)
	- [How do I access the MBeanServer or MBeanServerConnection from inside JBoss EAP](https://access.redhat.com/solutions/3304291)
	- [How to connect EAP 7 CLI in Domain mode using DMR API?](https://access.redhat.com/solutions/3304291)
- Health Metrics Storage 
	- data.metrics.path=<Path to directory to saeve METRICS HIstorical Data>
	- data.alerts.path=<Path to directory to saeve ALERTS HIstorical Data>
- Health Metric Thresholds 
	- A list of thresholds to be checked against realtime monitored stats see [How to add an alert](HowToAddAlerts.md)


## How to Use

### REST APIs
- Swagger API: http://localhost:<server.port>/swagger-ui.html

### Monitor Files
- JDG Node & CacheContainer Monitor
    * ./metrics/<provile-prefix>-jdgnode-<timestamp>.csv
- JDG HotROd Transport Monitor
    * ./metrics/<provile-prefix>-hotrodtransport-<timestamp>.csv
- JDG Caches MOnitor
    * ./metrics/<provile-prefix>-jdgcaches-<timestamp>.csv
### Alert Files
- All Alerts
    * ./metrics/<provile-prefix>-alerts-<timestamp>.csv

### Implemented Alerts/Thresholds Files

- JDG65
	- threshold.node.min.free.memory.percent=50
	- threshold.hotrod.min.free.threads=10
	- threshold.cache.max.read.latency.millis=0
	- threshold.cache.max.write.latency.millis=0
	- threshold.cache.max.read.latency.nanos=700000
	- threshold.cache.max.write.latency.nanos=900000
	- threshold.node.min.clustersize=2
	- threshold.cachecontainer.health=RUNNING
- JDG7
	- threshold.node.min.free.memory.percent=40
	- threshold.hotrod.min.free.threads=180
	- threshold.cache.max.read.latency.millis=0
	- threshold.cache.max.write.latency.millis=0
	- threshold.cache.max.read.latency.nanos=500000
	- threshold.cache.max.write.latency.nanos=700000
	- threshold.node.min.clustersize=2
	- threshold.cachecontainer.health=HEALTHY


## JMX MBeans by JDG Version

### 6.x 
- https://docs.jboss.org/infinispan/6.0/apidocs/jmxComponents.html#StateTransferManager

### 7.x 
- https://docs.jboss.org/infinispan/9.4/apidocs/jmxComponents.html#CacheContainerHealth
- https://access.redhat.com/documentation/en-us/red_hat_data_grid/7.1/html/administration_and_configuration_guide/jmx_mbeans_in_red_hat_jboss_data_grid


## Source Code Guidance
* it.sia.task.ScheduledMonitorCollectionTask.reportCacheContainerHealth() - schedules every 5000 millis a cache container health check
* it.sia.jdg.monitor.commands.mbeans.impl.CacheContainerHealthJDG6 & CacheContainerHealthJDG7
	- implement runMonitor()
	- collect stats, maintain repositories of them (latest view)
	- report on Disk files the history (it.sia.jdg.monitor.commands.mbeans.impl.CSVMonitorReporter)
	- run rules for alerts & report on disk file (it.sia.controller.file.impl.CSVAlertReporter)
* it.sia.jdg.monitor.repository.JDG7HealthMonitorRepository & it.sia.jdg.monitor.repository.JDG65HealthMonitorRepository
	- Maintain 3 repositories
		- Map<String, CacheMonitor> cachesHealthMonitors              - Map of Cache Monitors (per cache)
		- JDG7CacheContainerNodeMonitor cachesNodeAndContainerMonitor - jdgNode & CacheContainer monitors
		- JDG7HotRodTransportMonitor hotRodTransportMonitor           - hotrod monitores
* See [How to add an alert](HowToAddAlerts.md) on how alerts work
* Controllers
	- it.sia.controller.rest.impl.HealthServiceImpl
		- Implements REST APIs to expose health monitors and alerts
		- Reads from the repositories (monitor and alert values)
	- it.sia.controller.file.impl.ThresholdsIngestionImpl
		- Ingests thresholds



