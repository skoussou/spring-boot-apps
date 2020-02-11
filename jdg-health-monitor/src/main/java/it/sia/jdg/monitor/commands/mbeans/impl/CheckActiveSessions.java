package it.sia.jdg.monitor.commands.mbeans.impl;

import java.io.IOException;
import java.net.InetAddress;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

@Deprecated
public class CheckActiveSessions {

    static String host = "localhost"; // replace it with ip address
    static int port = 9990; // Make sure that port offset is added in managemt port
    static String user = "admin"; // replace it with management username created with the help of add-user.sh
    static String password = "Password730!"; // respective password
    static String application_name = "Test.war";

    public static void main(String[] args) throws Exception, IOException, Exception {

        ModelControllerClient client = createClient(InetAddress.getByName(host), port, user, password);

        ModelNode activeSession = new ModelNode();
        activeSession.get("operation").set("read-attribute");
        activeSession.get("name").set("active-sessions");

        ModelNode address = activeSession.get("address");
        address.add("deployment", application_name);
        address.add("subsystem", "undertow");

        ModelNode returnHttp = client.execute(activeSession);
        System.out
                .println("Total Active sessions for " + application_name + ": " + returnHttp.get("result").toString());

        client.close();
    }

    private static final ModelControllerClient createClient(final InetAddress host, final int port,
            final String username, final String password) {
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