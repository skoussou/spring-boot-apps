package com.redhat.rabbitmq.service;

import com.redhat.model.transactions.TransactionsStatusMessage;
import com.redhat.model.transactions.TransactionState;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.jbpm.services.api.ProcessService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationKey;
import org.kie.internal.process.CorrelationKeyFactory;
import org.kie.internal.process.CorrelationKey;


import static com.redhat.app.Application.EXCHANGE_NAME;
import static com.redhat.app.Application.QUEUE_ROUTINGKEY;
import static com.redhat.app.Application.QUEUE_NAME;


@Component
public class RabbitMQReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Value("${SB_KIE_MODULE_GAV}")
    @Value("${kie.module.gav}")
    private String kieModuleGav;       

    private Integer counter = 0;

    @Autowired
    private ProcessService processService;    

    // @RabbitListener(bindings = @QueueBinding(value = @Queue(value = Application.QUEUE_NAME, durable = "true"),
    //         exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
    //         key = QUEUE_ROUTINGKEY))
    // public void receiveMessage(String message) {
    //     counter++;
    //     System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"}> msg with content[" + convertInputStreamToString(is)+"]");

    // }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QUEUE_NAME, durable = "true"),
    exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
    key = QUEUE_ROUTINGKEY))
    public void receiveMessage(final TransactionsStatusMessage customMessage) {

        CorrelationKeyFactory factory = KieInternalServices.Factory.get().newCorrelationKeyFactory();
        
        counter++;


        System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"}> msg with content[" + customMessage.toString()+"]");

        if (customMessage.isNewTransaction()) {
            CorrelationKey key = factory.newCorrelationKey(customMessage.getCorrelationId());

            System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"} Creating NEW Process Instance for Process Id=["+customMessage.getProcessId()+"] "
            + "and corellation/transactionId=["+customMessage.getCorrelationId()+"]-["+key+"]");

            //Long pid = processService.startProcess("com.redhat:new-hire:2.0.0", customMessage.getProcessId(), key);
            //Long pid = processService.startProcess("com.redhat:payments:4.6.0", customMessage.getProcessId(), key);
            System.out.println("RabbitMQReceiver PAM: USING KJAR ["+kieModuleGav+"]");
            Long pid = processService.startProcess(kieModuleGav, customMessage.getProcessId(), key);
            
            System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"} Created NEW Process Instance ID ["+pid+"]");

        } else {
            CorrelationKey key = factory.newCorrelationKey(customMessage.getCorrelationId());

            System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"} Finding  Process Instance "
            +" for corellation/transactionId=["+customMessage.getCorrelationId()+"]-["+key+"]");

            ProcessInstance pInstance = processService.getProcessInstance(key);
            if (pInstance != null){
                System.out.println("RabbitMQReceiver PAM: Received <{"+counter+"} Found "
                + " for corellation/transactionId=["+customMessage.getCorrelationId()+"]-["+key+"] pInstance ["+pInstance.getId()+"]");

                System.out.println("RabbitMQReceiver PAM: Signaled Process Instance ["+pInstance.getId()+"] --> ["+customMessage.getState()+"]");
                processService.signalProcessInstance(+pInstance.getId(), customMessage.getState().getState(), null);

            }



        }
    }

    public Integer getCounter() {
        return counter;
    }

    public void initCounter() {
        this.counter = 0;
    }

}
