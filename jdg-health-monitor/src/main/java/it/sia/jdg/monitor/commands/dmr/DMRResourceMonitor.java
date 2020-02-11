package it.sia.jdg.monitor.commands.dmr;

import java.io.IOException;
import java.util.Map;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;


/**
 * 
 * @author stelios@redhat.com
 * @param <V>
 *
 */
public interface DMRResourceMonitor<K, V> {

	public static final String RESULT = "result";

	ModelNode monitorCommand(Map<K, V> params) throws IOException;
}
