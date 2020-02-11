package it.sia.jdg.monitor.commands.dmr;

import it.sia.jdg.connection.DMRConnection;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.jboss.dmr.ModelNode;



/**
 * 
 * 
 * 
 * @author stelios@redhat.com
 *
 */
public class MemoryMonitor implements DMRResourceMonitor<String, String> {

	public static final String PLATFORM_MBEAN_TYPE = "mbeantype";

	/* For testing purposes */
	public static void main(String[] args) {
		String user = "admin";
		String password = "Password730!";
		String host = "127.0.0.1";
		int port = 9999;

		DMRConnection cliConnection = null;
		try {
			cliConnection = new DMRConnection(host, port, user, password);

			MemoryMonitor ms = new MemoryMonitor();
			// String threadPoolExecName = ms.findWebExecutorHelper(new
			// HashMap(){{put(CONNECTOR, "http");}}, cliConnection.getClient());

			// type=memory
			ModelNode cliCommand = ms.monitorCommand(new HashMap() {
				{
					put(PLATFORM_MBEAN_TYPE, "memory");
				}
			});
			ModelNode returnMemInfo = cliConnection.getClient().execute(cliCommand);
			System.out.println(returnMemInfo.get(RESULT).toString());

			System.out.println("**************************************************************");

			// type=memory-pool
			cliCommand = ms.monitorCommand(new HashMap() {
				{
					put(PLATFORM_MBEAN_TYPE, "memory-pool");
				}
			});
			returnMemInfo = cliConnection.getClient().execute(cliCommand);
			System.out.println(returnMemInfo.get(RESULT).toString());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cliConnection.destroyClient();
		}

	}

	/**
	 * /core-service=platform-mbean/type=DEFINE:read-resource(include-runtime=true)
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@Override
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

}
