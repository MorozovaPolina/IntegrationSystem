package stub.rest;


import com.google.gson.Gson;
import stub.messages.request.UserCircuitBreakerRequest;
import stub.messages.request.UserTransformationRequest;
import stub.session.*;
import stub.messages.request.UserQueueRequest;
import stub.messages.request.UserRoutingRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.NewMessagesLog;

@Path("")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RoutingHandler {

    @POST
    @Path("RoutingHandler")
    public void routingHandle(UserRoutingRequest request) throws IOException, InterruptedException, ExecutionException{

        if(request.getApi()==null|| request.getApi().length()==0) return;
        RoutingSession session = new RoutingSession(RequirementsTypes.Routing, request.getApi());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        session.startSession();

    }

    @POST
    @Path("QueueHandler")
    public void queueHandler(UserQueueRequest request) throws ExecutionException, InterruptedException {
        System.out.println("Queue");
        if(request.getApi()==null|| request.getApi().length()==0||request.getSeconds_to_wait()<0) return;
        QueueSession session = new QueueSession(RequirementsTypes.Queues, request.getApi(), request.getSeconds_to_wait());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        session.startSession();

    }

    @POST
    @Path("CBHandler")
    public void CBHandler (UserCircuitBreakerRequest request) throws ExecutionException, InterruptedException {
        if(request.getApi()==null|| request.getApi().length()==0||request.getOn_of_message_to_be_rejected()<0||
                request.getRejection_quantity()<=0) return;
        CircuitBreakerSession session = new CircuitBreakerSession(RequirementsTypes.CircuitBreaker,
                request.getApi(), request.getOn_of_message_to_be_rejected(), request.getRejection_quantity());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        session.startSession();
    }

    @POST
    @Path("Transformation")
    public void transformationHandler(UserTransformationRequest request) throws ExecutionException, InterruptedException {
        if(request.getApi()==null|| request.getApi().length()==0) return;
        TransformationSession session = new TransformationSession(RequirementsTypes.Routing, request.getApi());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        session.startSession();
    }

    @GET
    @Path("log")
    public Response logHandler(){
        System.out.println("Hello, log!");
        Gson gson = new Gson();
        String log = gson.toJson(NewMessagesLog);
        System.out.println(log);
        NewMessagesLog.clear();
        return Response.status(Response.Status.OK).entity(log).build();
    }
}
