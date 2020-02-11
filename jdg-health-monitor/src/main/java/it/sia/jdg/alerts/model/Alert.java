package it.sia.jdg.alerts.model;

import java.time.LocalDateTime;

import it.sia.helper.Constants;
import it.sia.jdg.monitor.model.MonitorKey;
import it.sia.jdg.monitor.model.MonitorType;

/**
 * Container of all Alert Information
 * 
 * @pauthor Stelios Kousouris
 */
public class Alert {

  private String monitorKey;
  private MonitorType monitorType;
  private String monitorTypeName;
  private String msg;
  private AlertLevel level;
  private LocalDateTime timestamp;

  // public Alert(String monitorKey, String healthAlertMsg, AlertLevel level,
  // MonitorType monitorType,
  // LocalDateTime timestamp) {
  // this.monitorKey = monitorKey;
  // this.msg = healthAlertMsg;
  // this.level = level;
  // this.monitorType = monitorType;
  // this.timestamp = timestamp;
  // }

  public Alert(String monitorKey, String healthAlertMsg, AlertLevel level, MonitorType monitorType,
      LocalDateTime monitorTimestamp, String monitorTypeName) {
    this.monitorKey = monitorKey;
    this.msg = healthAlertMsg;
    this.level = level;
    this.monitorType = monitorType;
    this.timestamp = monitorTimestamp;
    this.monitorTypeName = monitorTypeName;
  }

  public String getMonitorKey() {
    return monitorKey;
  }

  public void setMonitorKey(String monitorKey) {
    this.monitorKey = monitorKey;
  }

  public MonitorType getMonitorType() {
    return monitorType;
  }

  public void setMonitorType(MonitorType monitorType) {
    this.monitorType = monitorType;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public AlertLevel getLevel() {
    return level;
  }

  public void setLevel(AlertLevel level) {
    this.level = level;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMonitorTypeName() {
    return monitorTypeName;
  }

  public void setMonitorTypeName(String monitorTypeName) {
    this.monitorTypeName = monitorTypeName;
  }

  public String convertToCSV() {
    return this.getTimestamp().toString() + Constants.COMMA + this.getLevel() + Constants.COMMA + this.getMonitorType()
        + Constants.COMMA + this.getMonitorKey() + Constants.COMMA + this.getMonitorTypeName() + Constants.COMMA
        + this.getMsg();
  }

  @Override
  public String toString() {
    return "Alert [level=" + level + ", monitorKey=" + monitorKey + ", monitorType=" + monitorType
        + ", monitorTypeName=" + monitorTypeName + ", msg=" + msg + ", timestamp=" + timestamp + "]";
  }

}
