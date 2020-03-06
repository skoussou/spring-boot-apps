package com.redhat.model.transactions;

import com.redhat.model.transactions.TransactionState;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class TransactionsStatusMessage implements Serializable {

    //public final String TRANSACTION_ID_PREFIX = "TRANSACTION_ID_";

    /** The PAM Service will handle in this message processes of this ID (Optional and required only when Starting a new Process) */
    private final String processId;

    /** The ProcessInstanceId PAM will handle in this message is correlated with transactionId passed in as correlationId */
    private final String correlationId;

    /* TRANSACTION STATE TO UPDATE PROCESS TO*/
    private final TransactionState state;

    

    private final String NEW_STATE = "0";

    public TransactionsStatusMessage(@JsonProperty("processId") String processId,
                         @JsonProperty("correlationId") String correlationId,
                         @JsonProperty("state") TransactionState state) {
        this.processId = processId;
        this.correlationId = correlationId;
        this.state = state;
    }

    public String getProcessId() {
        return processId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public TransactionState getState() {
        return state;
    }

    public Boolean isNewTransaction(){
        //return NEW_STATE.equals(this.getCorrelationId());
        return TransactionState.TRANSACTION_CREATED.equals(this.getState());
    }

    @Override
    public String toString() {
        return "TransactionsStatusMessage{" +
                "processId=" + processId + 
                ", correlationId=" + correlationId +
                ", state=" + state +
                '}';
    }
}
