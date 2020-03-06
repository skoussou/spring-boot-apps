package com.redhat.controller.rest.transactions.service;

import com.redhat.rabbitmq.service.RabbitMQMessageSender;
import com.redhat.model.transactions.TransactionState;
import io.swagger.annotations.*;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.ProcessService;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Additional Endpoints", produces = MediaType.APPLICATION_JSON)
@Path("pam")
public class CustomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomController.class);

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private RabbitMQMessageSender msgSender;


    @ApiOperation(value = "Complete a task in Ready or Reserved state")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task completed")
    })
    @POST
    @Path(value = "/tasks/{taskId}/{actor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response autoCompleteTask(
            @ApiParam(value = "task id", required = true) @PathParam("taskId") Long taskId,
            @ApiParam(value = "name of the actor", required = true) @PathParam("actor") String actor) {
        Task task = userTaskService.getTask(taskId);
        if(task != null) {
            LOGGER.info("Task {} status {}", task.getId(), task.getTaskData().getStatus());
            if(task.getTaskData().getStatus() == Status.Reserved) {
                userTaskService.start(task.getId(), actor);
                userTaskService.complete(task.getId(), actor, null);
            }
            else if(task.getTaskData().getStatus() == Status.Ready) {
                userTaskService.claim(task.getId(), actor);
                userTaskService.start(task.getId(), actor);
                userTaskService.complete(task.getId(), actor, null);
            }
        }

        return Response.ok().build();
    }

    @ApiOperation(value = "Start a process")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Prcess Started")
    })
    @POST
    @Path(value = "/process/start/{containerId}/{processId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response autoStartProcess (
        @ApiParam(value = "Container ID", required = true) @PathParam("containerId") String containerId, 
        @ApiParam(value = "Process ID", required = true) @PathParam("processId") String processId)
    {
        //        System.out.println("******* STARTING new-hire process *********");
        System.out.println("[REST CustomController] ******* STARTING "+processId+" process  in container "+containerId+" *********");

        //Long id = processService.startProcess("com.redhat:new-hire:2.0.0", "new-hire");
        Long id = processService.startProcess(containerId, processId);

	    //System.out.println("******* STARTED new-hire process ["+id+"]*********");
        System.out.println("[REST CustomController] ******* STARTED "+processId+" process ["+id+"]*********");

        return Response.ok().build();
    }


    @ApiOperation(value = "Start a process via Message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Prcoess Started")
    })
    @POST
    @Path(value = "/process/message/start/{processId}/{transactionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response messageToStartProcess(
        @ApiParam(value = "BPM Process ID", required = true) @PathParam("processId") String processId, 
        @ApiParam(value = "Bank Transaction ID", required = true) @PathParam("transactionId") String transactionId)  {
     
        //System.out.println("******* STARTING new-hire process via REST/MESSSAGE ("+processId+"/"+transactionId+")*********");
        System.out.println("[REST CustomController] ******* STARTING chequestransactions process via REST/MESSSAGE ("+processId+"/"+transactionId+")*********");

        msgSender.sendMessage(processId, transactionId, TransactionState.TRANSACTION_CREATED);

        return Response.ok().build();
    }    


    @ApiOperation(value = "Continue a process via Message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Process Updated")
    })
    @POST
    @Path(value = "/process/message/continue/{transactionId}/{state}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response messageToContinueProcess(
        @ApiParam(value = "Bank Transaction ID", required = true) @PathParam("transactionId") String transactionId, 
        @ApiParam(value = "Transaction State", required = true) @PathParam("state") String state)  {
     
	    System.out.println("[REST CustomController] ******* UPDATING TRANSACTION "+transactionId+" to process state ["+state+"]*********");
        msgSender.sendMessage("VOID", transactionId, TransactionState.fromString(state));

        return Response.ok().build();
    } 

}

