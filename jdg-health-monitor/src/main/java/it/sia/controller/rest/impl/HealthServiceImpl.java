package it.sia.controller.rest.impl;

import it.sia.controller.rest.HealthService;
import it.sia.jdg.alerts.repository.JDGHealthAlertsRepository;
import it.sia.jdg.connection.DMRConnection;
import it.sia.jdg.connection.JMXConnection;
import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;
import it.sia.jdg.monitor.model.cacheContainer.JDG65CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG7CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.JDG65HotRodTransportMonitor;
import it.sia.jdg.monitor.model.transport.JDG7HotRodTransportMonitor;
import it.sia.jdg.monitor.repository.JDGHealthMonitorsRepository;
import it.sia.model.Health;
import it.sia.model.JDGHealthNodeDTO;
import it.sia.model.JDGMBeanNamesDTO;
import it.sia.model.Profile;
import java.io.IOException;
import java.lang.String;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import javax.ws.rs.core.Response;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Annotations used for SWAGGER Docs building, REST APIs formed in HealthService
@RestController
@RequestMapping(path = "monitor/health")
public class HealthServiceImpl implements HealthService {

    @Autowired
    private JDGMBeanDetailsReposistory mbeanNames;

    @Autowired
    private JMXConnection jmxConnection;

    @Autowired
    private JDGHealthMonitorsRepository healthMonitorRepository;

    @Autowired
    private Environment env;

    @Autowired
    private JDGHealthAlertsRepository healthAlertsRepository;

    @Override
    public Response getCacheContainerHealth() {
        if (env.getActiveProfiles() != null) {

            List<String> envsList = new ArrayList<String>(Arrays.asList(env.getActiveProfiles()));

            if (envsList.contains(Profile.jdg6.getName())) {
                JDG65CacheContainerNodeMonitor monitor = (JDG65CacheContainerNodeMonitor) healthMonitorRepository
                        .getNodeAndCacheContainerMonitor();

                // TODO - IMportant below is an example of how to get a generic READINESS status
                // in JDG 6 (It doesn't have the
                // same weight of cluster health in JDG7 and can therefore be further enhanced
                // with other checks
                if (monitor.getCacheContainerStatus() != null && monitor.getCacheContainerStatus().equals("RUNNING")) {
                    JSONObject payload = new JSONObject();
                    payload.put("status", Health.STATUS.RUNNING);
                    return Response.ok(payload.toString()).build();
                } else {
                    JSONObject payload = new JSONObject();
                    payload.put("status", Health.STATUS.NOT_READY);
                    return Response.ok(payload.toString()).build();
                }

            } else if (envsList.contains(Profile.jdg7.getName())) {
                JDG7CacheContainerNodeMonitor monitor = (JDG7CacheContainerNodeMonitor) healthMonitorRepository
                        .getNodeAndCacheContainerMonitor();

                JSONObject payload = new JSONObject();
                payload.put("status", monitor.getClusterHealth());
                return Response.ok(payload.toString()).build();

            } else {
                return Response.status(500,
                        "None of the checked Profiles was defined when running the app. Check if other profile options need to be added")
                        .build();
            }

        }
        return Response.status(500, "No valid Profile was defined when running the app").build();
    }

    @Override
    public Response getNodeHealthDetails() {
        if (env.getActiveProfiles() != null) {

            List<String> envsList = new ArrayList<String>(Arrays.asList(env.getActiveProfiles()));

            if (envsList.contains(Profile.jdg6.getName())) {
                JDG65CacheContainerNodeMonitor monitor = (JDG65CacheContainerNodeMonitor) healthMonitorRepository
                        .getNodeAndCacheContainerMonitor();

                JDG65HotRodTransportMonitor transportMonitor = (JDG65HotRodTransportMonitor) healthMonitorRepository
                        .getCacheHotRodTransportHealthMonitors();

                JDGHealthNodeDTO dtoNodeHealth = new JDGHealthNodeDTO(monitor, transportMonitor);

                return Response.ok(dtoNodeHealth).build();

            } else if (envsList.contains(Profile.jdg7.getName())) {
                JDG7CacheContainerNodeMonitor monitor = (JDG7CacheContainerNodeMonitor) healthMonitorRepository
                        .getNodeAndCacheContainerMonitor();

                JDG7HotRodTransportMonitor transportMonitor = (JDG7HotRodTransportMonitor) healthMonitorRepository
                        .getCacheHotRodTransportHealthMonitors();

                JDGHealthNodeDTO dtoNodeHealth = new JDGHealthNodeDTO(monitor, transportMonitor);

                return Response.ok(dtoNodeHealth).build();
            } else {
                return Response.status(500,
                        "None of the checked Profiles was defined when running the app. Check if other profile options need to be added")
                        .build();
            }

        }
        return Response.status(500, "No valid Profile was defined when running the app").build();

    }

    @Override
    public Response getAllCachesHealthDetails() {
        return Response.ok(healthMonitorRepository.getAllCachesHealthMonitors()).build();
    }

    @Override
    public Response getCacheHealthDetails(final String cacheName) {
        return Response.ok(healthMonitorRepository.getSingleCacheHealthMonitors(cacheName)).build();
    }

    @Override
    public Response getHealthAlerts() {
        // TODO Auto-generated method stub
        return Response.ok(healthAlertsRepository.getGeneratedAlerts()).build();
    }

    @Override
    public Response putHealthMetricThreshold(final String metricName, final long threshold) {
        // TODO Auto-generated method stub
        Logger.getGlobal().info("********* Updating Metric [" + metricName + "] *****************");
        Logger.getGlobal().info("* Threshold Metric [" + metricName + "] -> [" + threshold + "]");
        return Response.ok().build();
    }

    public static final String PLATFORM_MBEAN_TYPE = "mbeantype";

    // How to access MBeanServerConnection from remote JMX Client in JBoss EAP 7
    // (https://access.redhat.com/solutions/2604501)
    // How do I access the MBeanServer or MBeanServerConnection from inside JBoss
    // EAP (https://access.redhat.com/solutions/3304291)

    @Override
    public Response getJDGConnectionStatusJMX() {

        JDGMBeanNamesDTO dto = new JDGMBeanNamesDTO(mbeanNames);
        System.out.println("REFERENCED JDGMBeanNames BEAN " + mbeanNames);

        try {

            // jmxConnection = new JMXConnection();

            int count = jmxConnection.getClient().getMBeanCount();
            System.out.println(count);

            // jmxConnector.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            // if (jmxConnection != null)
            // jmxConnection.destroyClient();
        }
        return Response.ok(dto).build();

    }

    // How to connect EAP 7 CLI in Domain mode using DMR API?
    // (https://access.redhat.com/solutions/3304291)
    @Override
    public Response getJDGConnectionStatusDMR() {
        DMRConnection cliConnection = null;
        try {
            cliConnection = new DMRConnection();

            // type=memory
            ModelNode cliCommand = monitorCommand(new HashMap() {
                {
                    put(PLATFORM_MBEAN_TYPE, "memory");
                }
            });

            ModelControllerClient cliClient = cliConnection.getClient();
            ModelNode returnMemInfo = cliClient.execute(cliCommand);
            System.out.println(returnMemInfo.get("result").toString());

            System.out.println("**************************************************************");

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } finally {
            if (cliConnection != null)
                cliConnection.destroyClient();
        }
        return Response.ok().build();

    }

    /**
     * /core-service=platform-mbean/type=DEFINE:read-resource(include-runtime=true)
     * 
     * @param params
     * @return
     * @throws IOException
     */
    public ModelNode monitorCommand(Map<String, String> params) throws IOException {
        ModelNode readMemInfo = new ModelNode();
        readMemInfo.get("operation").set("read-resource");
        readMemInfo.get("recursive").set(true);
        readMemInfo.get("include-runtime").set(true);
        ModelNode addressMemInfo = readMemInfo.get("address");
        addressMemInfo.add("core-service", "platform-mbean");
        addressMemInfo.add("type", params.get(PLATFORM_MBEAN_TYPE));

        return readMemInfo;
    }

    private final ModelControllerClient createClient(final InetAddress host, final int port, final String username,
            final String password) {
        final CallbackHandler callackHandler = new CallbackHandler() {
            @Override
            public void handle(Callback[] callback) throws IOException, UnsupportedCallbackException {
                for (Callback current : callback) {
                    if (current instanceof NameCallback) {
                        NameCallback ncb = (NameCallback) current;
                        ncb.setName(username);
                    } else if (current instanceof PasswordCallback) {
                        PasswordCallback pcb = (PasswordCallback) current;
                        pcb.setPassword(password.toCharArray());
                    } else if (current instanceof RealmCallback) {
                        RealmCallback rcb = (RealmCallback) current;
                        rcb.setText(rcb.getDefaultText());
                    } else {
                        throw new UnsupportedCallbackException(current);
                    }
                }
            }
        };
        return ModelControllerClient.Factory.create(host, port, callackHandler);
    }

}
