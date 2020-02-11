package it.sia.task;

import it.sia.jdg.connection.JMXConnection;
import it.sia.jdg.monitor.commands.mbeans.CacheContainerHealth;
import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.management.ObjectInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMonitorCollectionTask {

	@Autowired
	private JDGMBeanDetailsReposistory mbeanNames;

	@Autowired
	private JMXConnection jmxConnection;

	@Autowired
	private CacheContainerHealth cch;

	private static final Logger log = LoggerFactory.getLogger(ScheduledMonitorCollectionTask.class);

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 5000)
	public void reportCacheContainerHealth() {

		try {
			Set<ObjectInstance> instances = jmxConnection.getClient().queryMBeans(null, null);

			mbeanNames.findCacheMBeansforMonitor(instances);

			cch.runMonitor();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void printEnvPropertyConfigs() {
		// System.out.println("env.getProperty(\"jmx.host\"); -->) " +
		// env.getProperty("jmx.host"));
		// System.out.println("env.getProperty(\"jmx.url\"); -->) " +
		// env.getProperty("jmx.url"));
		// System.out.println("env.getProperty(\"jmx.user\"); -->) " +
		// env.getProperty("jmx.user"));
		// System.out.println("env.getProperty(\"jmx.password\"); -->) " +
		// env.getProperty("jmx.password"));

		// System.out.println("");
		// System.out.println("host --> [" + host + "]");
		// System.out.println("port --> [" + port + "]");
		// System.out.println("url --> [" + url + "]");
		// System.out.println("user --> [" + user + "]");
		// System.out.println("password --> [" + password + "]");
	}
}
