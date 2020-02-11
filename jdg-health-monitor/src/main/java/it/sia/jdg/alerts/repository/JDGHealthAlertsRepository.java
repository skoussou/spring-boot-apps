package it.sia.jdg.alerts.repository;

import java.util.Map;

import it.sia.jdg.alerts.model.Alert;
import it.sia.jdg.alerts.model.thresholds.JDGAlertsThresholdsContainer;

public interface JDGHealthAlertsRepository {

  /**
   * Adds & Updates the Thresholds
   * 
   * @param cacheName
   */
  void addAlertThresholds(JDGAlertsThresholdsContainer thresholdsContainer);

  /**
   * Retrieve the thresholds
   * 
   * @return {@code JDGAlertsThresholdsContainer}
   */
  JDGAlertsThresholdsContainer getAlertThresholds();

  /**
   * Receives and adds to the repository generatd Alerts
   * 
   * @param alertNodeContainerResults
   */
  void addGeneratedAlerts(Map<String, Alert> alertNodeContainerResults);

  /**
   * Retrieves the generated Alerts
   * 
   * @return Map<String, Alert>
   */
  Map<String, Alert> getGeneratedAlerts();

  /**
   * Periodically called to nulify past alerts
   * 
   * @param alertNodeContainerResults
   */
  void clearGeneratedAlerts();
}
