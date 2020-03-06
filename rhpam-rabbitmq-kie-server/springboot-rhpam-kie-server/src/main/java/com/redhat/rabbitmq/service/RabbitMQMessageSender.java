package com.redhat.rabbitmq.service;

import com.redhat.model.transactions.TransactionsStatusMessage;
import com.redhat.model.transactions.TransactionState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.redhat.app.Application.EXCHANGE_NAME;
import static com.redhat.app.Application.QUEUE_ROUTINGKEY;
import static com.redhat.app.Application.QUEUE_NAME;

@Service
public class RabbitMQMessageSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQMessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    private Random random = new Random();

    public RabbitMQMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // @Scheduled(fixedDelay = 10000L)
    // public void sendMessage() {
    //    int number = random.nextInt(1000) + 1;

    //    TransactionsStatusMessage message = null;;
    //    if ( number != 0) {
    //    message = new TransactionsStatusMessage("new-hire", String.valueOf(number), TransactionsStatusMessage.TransactionState.TRANSACTION_CREATED);
    // } else {
    //   message = new TransactionsStatusMessage("new-hire", String.valueOf(number), TransactionsStatusMessage.TransactionState.CHEQUES_CREATED);
    // }

    //     System.out.println("Sending message...["+message.toString()+"]");
    //     rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY, message);
    // }

    public void sendMessage(String processId, String transactionId, TransactionState state) {

      TransactionsStatusMessage message = new TransactionsStatusMessage(processId, transactionId, state);
 
      System.out.println("[RabbitMQMessageSender] Sending message...["+message.toString()+"]");
      rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY, message);
   }
    
}