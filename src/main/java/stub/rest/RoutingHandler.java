package stub.rest;


import stub.messages.request.UserCircuitBreakerRequest;
import stub.messages.request.UserTransformationRequest;
import stub.transaction.*;
import stub.messages.request.UserQueueRequest;
import stub.messages.request.UserRoutingRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Path("")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RoutingHandler {

    @POST
    @Path("RoutingHandler")
    public void routingHandle(UserRoutingRequest request) throws IOException, InterruptedException, ExecutionException{

        if(request.getApi()==null|| request.getApi().length()==0) return;
        RoutingTransaction transaction = new RoutingTransaction(RequirementsTypes.Routing, request.getApi());
        for (Step step: request.getScenario()){
            if(step.source.equals("Source")||step.target.equals("Target")||step.numberOfMessagesToSend<=0)return;
            transaction.addStep(step);
        }
        transaction.startTransaction();

    }

    @POST
    @Path("QueueHandler")
    public void queueHandler(UserQueueRequest request) throws ExecutionException, InterruptedException {
        System.out.println("Queue");
        if(request.getApi()==null|| request.getApi().length()==0||request.getSeconds_to_wait()<0) return;
        QueueTransaction transaction = new QueueTransaction(RequirementsTypes.Queues, request.getApi(), request.getSeconds_to_wait());
        for (Step step: request.getScenario()){
            if(step.source.equals("Source")||step.target.equals("Target")||step.numberOfMessagesToSend<=0)return;
            transaction.addStep(step);
        }
        transaction.startTransaction();

    }

    @POST
    @Path("CBHandler")
    public void CBHandler (UserCircuitBreakerRequest request) throws ExecutionException, InterruptedException {
        if(request.getApi()==null|| request.getApi().length()==0||request.getOn_of_message_to_be_rejected()<0||
                request.getRejection_quantity()<=0) return;
        CircuitBreakerTransaction Transaction = new CircuitBreakerTransaction(RequirementsTypes.CircuitBreaker,
                request.getApi(), request.getOn_of_message_to_be_rejected(), request.getRejection_quantity());
        for (Step step: request.getScenario()){
            if(step.source.equals("Source")||step.target.equals("Target")||step.numberOfMessagesToSend<=0)return;
            Transaction.addStep(step);
        }
        Transaction.startTransaction();
    }

    @POST
    @Path("Transformation")
    public void transformationHandler(UserTransformationRequest request) throws ExecutionException, InterruptedException {
        if(request.getApi()==null|| request.getApi().length()==0) return;
        TransformationTransaction transaction = new TransformationTransaction(RequirementsTypes.Routing, request.getApi());
        for (Step step: request.getScenario()){
            if(step.source.equals("Source")||step.target.equals("Target")||step.numberOfMessagesToSend<=0)return;
            transaction.addStep(step);
        }
        transaction.startTransaction();
    }
}
