package it.sia.jdg.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import org.jboss.as.controller.client.ModelControllerClient;



/**
 * Required server config
 * 
 *  <subsystem xmlns="urn:jboss:domain:jmx:1.3">
        <expose-resolved-model/>
        <expose-expression-model/>
        <remoting-connector use-management-endpoint="true"/>
    </subsystem>
    <subsystem xmlns="urn:jboss:domain:naming:2.0">
        <remote-naming/>
    </subsystem>
    <subsystem xmlns="urn:jboss:domain:remoting:4.0">
        <http-connector name="http-remoting-connector" connector-ref="default" security-realm="ApplicationRealm"/>
    </subsystem>
 * 
 *  use-management-endpoint="true" due to https://stackoverflow.com/questions/48942206/can-not-use-jconsole-to-connect-to-jboss-eap7-1/48958210
 * @ Stelios Kousouris
 */

public class DMRConnection {
    private String host = "127.0.0.1"; // replace it with ip address
    private int port = 9990; // Make sure that port offset is added in managemt port (if any)
    private String user = "admin"; // replace it with management username created with the help of add-user.sh
    private String password = "Password730!"; // respective password
    private ModelControllerClient client;

    public DMRConnection(String host, int port, String user, String password) throws UnknownHostException {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;

        printConnectionDetails();
        client = createClient(InetAddress.getByName(host), port, user, password);

    }

    public DMRConnection() throws UnknownHostException {
        printConnectionDetails();
        client = createClient(InetAddress.getByName(host), port, user, password);
    }

    public ModelControllerClient getClient() {
        return client;
    }

    public void destroyClient() {
        try {
            client.close();
        } catch (IOException e) {
            Logger.getGlobal().info("Failed to close CLI Client");
        }
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

    private void printConnectionDetails() {
        Logger.getGlobal().info("*************************CLI CONNECTION DETAILS ********************************");
        Logger.getGlobal().info("*   HOST: " + host + "");
        Logger.getGlobal().info("*   PORT: " + port + "");
        Logger.getGlobal().info("*   USER: " + user + "");
        Logger.getGlobal().info("********************************************************************************");
    }
}