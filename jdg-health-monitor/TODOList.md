
## TODO
1. [DONE] Create SpringBoot APP & Expose JAX-RS Rest-APIs
	- https://examples.javacodegeeks.com/enterprise-java/spring/boot/spring-boot-jax-rs-example/
	- https://dzone.com/articles/using-jax-rs-with-spring-boot-instead-of-mvc
	- https://dzone.com/articles/spring-boot-restful-web-service-complete-example
	- https://www.baeldung.com/rest-with-spring-series
	- https://spring.io/guides/gs/rest-service/
	- https://howtodoinjava.com/spring-boot2/rest/rest-api-example/
	- /swagger-ui.html
2. [DONE] Use Profiles to switch between JDG 6 and JDG 7 JMX Beans consumption/monitoring
	- https://www.baeldung.com/spring-boot-devtools
	- https://www.baeldung.com/spring-profiles
	- https://dzone.com/articles/spring-boot-profiles-1
	- https://bitbucket.org/sovanm/profiles/src/master/
3. [PARTIAL] Actuator
	- https://www.baeldung.com/spring-boot-actuators#boot-2x-actuator
	- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints
	- https://howtodoinjava.com/spring-boot/actuator-endpoints-example/
4. [DONE] Connect stats captured to REST API (preferably prometheus format)
5. [DONE] Health API also for the monitor app itself (actuator/health)
6. [DONE] Save into file the history (CSV)
	- timestamp, cachename, jdgservernode, 
7. FIX Test class .... for now -DskipTests
8. [DONE] SWAGGER UI
9. [DONE] Read application-jdg65.properties,application-jdg7.properties, externally to .jar
10. [DONE]Get thresholds from file (eg. memory threshold, transport threads threshold, read/write latency threshold)
	- https://www.baeldung.com/spring-properties-file-outside-jar	
11. Get thresholds from PUT {metric}/{threshold} API
12. [DONE] CREATE ALERTS on specific thresholds
13. [DONE] Write statistics for cache, node/cachecontainer in CSV format into file (timestampt)
14. [DONE] Write alerts into file (timestampt)
15. JVM MOnitor [GC etc.] (timestamp)
