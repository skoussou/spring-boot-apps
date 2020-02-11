package it.sia.jdg.alerts.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.thresholds.JDG65AlertsThresholdsContainer;
import it.sia.jdg.alerts.model.thresholds.JDGAlertsThresholdsContainer;

/**
 * Repository of Health Alert Rules thresholds and encountered threshold
 * violation Alerts for JDG6 monitors. Maintains - long-term (daily or restart)
 * Alert Rules threshold values - short-term (per schedule rules firing) any
 * enncountered alerts
 */
@Profile("jdg65")
@Scope(value = "singleton")
@Component
public class JDG65HealthAlertsRepository implements JDGHealthAlertsRepository {

  private JDG65AlertsThresholdsContainer healthAlertThresholds;

  private Map<String, Alert> healthAlerts = new HashMap<String, Alert>();

  @Override
  public void addAlertThresholds(JDGAlertsThresholdsContainer thresholdsContainer) {
    this.healthAlertThresholds = (JDG65AlertsThresholdsContainer) thresholdsContainer;
    System.out.println("ALERT THRESHOLDS NOW STORED AS --> " + this.healthAlertThresholds);
  }

  @Override
  public JDGAlertsThresholdsContainer getAlertThresholds() {
    return this.healthAlertThresholds;
  }

  @Override
  public void addGeneratedAlerts(Map<String, Alert> alertNodeContainerResults) {
    this.healthAlerts.putAll(alertNodeContainerResults);

  }

  @Override
  public Map<String, Alert> getGeneratedAlerts() {
    return this.healthAlerts;
  }

  @Override
  public void clearGeneratedAlerts() {
    this.healthAlerts.clear();
  }
}
