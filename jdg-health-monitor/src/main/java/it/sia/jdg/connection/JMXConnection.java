package it.sia.jdg.connection;

import it.sia.task.ScheduledMonitorCollectionTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Creates a JMX Connection from the details provided in application.properties
 */
@Component
public class JMXConnection {

    @Autowired
    private Environment env;

    @Value("${jmx.host}")
    private String host;

    @Value("${jmx.port}")
    private int port;

    @Value("${jmx.url}")
    private String url;

    @Value("${jmx.user}")
    private String user;

    @Value("${jmx.password}")
    private String password;

    // private String urlString = "service:jmx:remote+http://" + host + ":" + port;
    // private String urlString = "service:jmx:remoting-jmx://" + host + ":" + port;

    private MBeanServerConnection client;

    private JMXConnector jmxConnector;

    public MBeanServerConnection getClient() throws MalformedURLException, UnknownHostException, IOException {

        if (client == null) {
            printConnectionDetails();
            this.client = createClient(InetAddress.getByName(host), port, user, password);
        }
        return this.client;
    }

    public void destroyClient() {
        try {
            jmxConnector.close();
        } catch (IOException e) {
            Logger.getGlobal().info("Failed to close JMX Connector");
        }
    }

    private MBeanServerConnection createClient(InetAddress byName, int port2, String user2, String password2)
            throws java.net.MalformedURLException, java.io.IOException {

        JMXServiceURL serviceURL = new JMXServiceURL(url + host + ":" + port);

        Map map = new HashMap();
        String[] credentials = new String[] { user, password };
        map.put("jmx.remote.credentials", credentials);

        jmxConnector = JMXConnectorFactory.connect(serviceURL, map);

        return jmxConnector.getMBeanServerConnection();
    }

    private void printConnectionDetails() {

        Logger.getGlobal().info("****" + ScheduledMonitorCollectionTask.dateFormat.format(new Date())
                + "*********************CLI CONNECTION DETAILS ********************************");
        Logger.getGlobal().info("*   HOST: " + host + "");
        Logger.getGlobal().info("*   PORT: " + port + "");
        Logger.getGlobal().info("*   USER: " + user + "");
        Logger.getGlobal().info("*   urlString: " + url + host + ":" + port + "");
        Logger.getGlobal().info("********************************************************************************");
    }
}
