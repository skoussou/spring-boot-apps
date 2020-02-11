package it.sia.controller.file.impl;

import it.sia.helper.TimeHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.CharSequence;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Reports the alerts into files
 * 
 * @author Stelios Kousouris
 */
@Component
public class CSVAlertReporter {

    private FileWriter csvAlerts;

    private static final String ALERTS_FINELNAME = "alerts";
    private static final String CSV_EXTENSION = ".csv";

    @Value("${data.alerts.path}")
    private String alertsDir;

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void openMonitorFiles() throws IOException {
        System.out.println("======= openAlertFiles ======================================");
        System.out.println("alertsDir --> [" + alertsDir + "]");
        String timestamp = TimeHelper.fileTimestamp();

        String csvAlertsFilename = alertsDir + File.separator + profile + "-" + ALERTS_FINELNAME + "-" + timestamp
                + CSV_EXTENSION;

        System.out.println("Filename for csvAlerts --> " + csvAlertsFilename);
        System.out.println("===============================================================");

        csvAlerts = new FileWriter(csvAlertsFilename, false);

    }

    @PreDestroy
    public void closeMonitorFiles() throws IOException {
        csvAlerts.close();
    }

    public void writeAlertsMonitorEntry(String alertEntry) throws IOException {
        csvAlerts.append(toCharSequenceLine(alertEntry));
        csvAlerts.flush();
    }

    private CharSequence toCharSequenceLine(String alertEntry) {
        String newlineContent = alertEntry + System.lineSeparator();
        return (CharSequence) newlineContent;
    }
}