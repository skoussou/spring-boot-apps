package com.redhat.model.transactions;

public enum TransactionState {

    TRANSACTION_CREATED("TRANSACTION_CREATED"),
    CHEQUES_CREATED("CHEQUES_CREATED"),
    CHEQUES_SCANNED("CHEQUES_SCANNED"),
    CHEQUES_RESOLVED("CHEQUES_RESOLVED"),
    TRANSACTION_VALIDATED("TRANSACTION_VALIDATED"),
    MF_TRANSACTION_CREATED("MF_TRANSACTION_CREATED"),
    BACK_OFFICE_CONFIRMED("BACK_OFFICE_CONFIRMED"),
    RECEPTION_CREATED("RECEPTION_CREATED"),
    CUSTOMER_INFORMED("CUSTOMER_INFORMED");

  private String state;

  TransactionState(String state) {
      this.state = state;
  }

  public String getState() {
      return this.state;
  }

  public static TransactionState fromString(String state) {
      for (TransactionState b : TransactionState.values()) {
          if (b.state.equalsIgnoreCase(state)) {
              return b;
          }
      }
      return null;
  }
}