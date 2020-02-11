package it.sia.jdg.monitor.commands.mbeans.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import it.sia.helper.TimeHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.CharSequence;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Reports the monitors into files
 * 
 * @author Stelios Kousouris
 */
@Component
public class CSVMonitorReporter {

    private FileWriter csvMetricsNode;
    private FileWriter csvMetricsHotRod;
    private FileWriter csvMetricsCaches;

    private static final String NODE_METRICS_FINELNAME = "jdgnode";
    private static final String HOTROD_METRICS_FINELNAME = "hotrodtransport";
    private static final String CACHES_METRICS_FINELNAME = "jdgcaches";
    private static final String CSV_EXTENSION = ".csv";

    @Value("${data.metrics.path}")
    private String metricsDir;

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void openMonitorFiles() throws IOException {
        System.out.println("======= openMonitorFiles ======================================");
        System.out.println("metricsDir --> [" + metricsDir + "]");
        System.out.println("profile --> [" + profile + "]");
        String timestamp = TimeHelper.fileTimestamp();

        String csvnodeMetricsFilename = metricsDir + File.separator + profile + "-" + NODE_METRICS_FINELNAME + "-"
                + timestamp + CSV_EXTENSION;
        String csvMetricsNodeFilename = metricsDir + File.separator + profile + "-" + HOTROD_METRICS_FINELNAME + "-"
                + timestamp + CSV_EXTENSION;
        String csvMetricsCachesFilename = metricsDir + File.separator + profile + "-" + CACHES_METRICS_FINELNAME + "-"
                + timestamp + CSV_EXTENSION;

        System.out.println("Filename for csvMetricsNode --> " + csvnodeMetricsFilename);
        System.out.println("Filename for csvMetricsHotRod --> " + csvMetricsNodeFilename);
        System.out.println("Filename for csvMetricsCaches --> " + csvMetricsCachesFilename);
        System.out.println("===============================================================");

        csvMetricsNode = new FileWriter(csvnodeMetricsFilename, true);
        csvMetricsHotRod = new FileWriter(csvMetricsNodeFilename, true);
        csvMetricsCaches = new FileWriter(csvMetricsCachesFilename, true);

    }

    @PreDestroy
    public void closeMonitorFiles() throws IOException {
        csvMetricsNode.close();
        csvMetricsHotRod.close();
        csvMetricsCaches.close();
    }

    public void writeNodeMonitorEntry(String monitorEntry) throws IOException {
        csvMetricsNode.append(toCharSequenceLine(monitorEntry));
        csvMetricsNode.flush();
    }

    public void writeHotRodeMonitorEntry(String monitorEntry) throws IOException {
        csvMetricsHotRod.append(toCharSequenceLine(monitorEntry));
        csvMetricsHotRod.flush();
    }

    public void writeCacheMonitorEntry(String monitorEntry) throws IOException {
        csvMetricsCaches.append(toCharSequenceLine(monitorEntry));
        csvMetricsCaches.flush();
    }

    private CharSequence toCharSequenceLine(String monitorEntry) {
        String newlineContent = monitorEntry + System.lineSeparator();
        return (CharSequence) newlineContent;
    }
}