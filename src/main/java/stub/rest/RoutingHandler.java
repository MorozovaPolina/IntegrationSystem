package stub.rest;


import com.google.gson.Gson;
import stub.messages.request.*;
import stub.session.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Log;
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
        if(request.getApi()==null|| request.getApi().length()==0
                ||request.getMaxSuitableMessageProcessingTime()<0
                ||request.getMinSuitableMessageProcessingTime()<0) return;
        QueueSession session = new QueueSession(RequirementsTypes.Queues,
                request.getApi(),
                request.getMaxSuitableMessageProcessingTime(),
                request.getMinSuitableMessageProcessingTime());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        session.startSession();

    }

    @POST
    @Path("CBHandler")
    public void CBHandler (UserCircuitBreakerRequest request) throws ExecutionException, InterruptedException {
        System.out.println("CB Handler");
        if(request.getApi()==null|| request.getApi().length()==0||request.getRejection().length==0) return;
        CircuitBreakerSession session = new CircuitBreakerSession(RequirementsTypes.CircuitBreaker,
                request.getApi(), request.getSecondsAfterRejection());
        for (Step step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target")||step.getNumberOfMessagesToSend()<=0)return;
            session.addStep(step);
        }
        System.out.println(request.getRejection().length);
        for(toReject rejection : request.getRejection()){
            if(rejection.getNumberOfRejections()==0) return;

            session.addRejection(rejection);
            System.out.println(rejection.getNumberOfRejections()+" "+rejection.getRejectionMessage());
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
    @Path("NewMes")
    public Response newMesHandler(){
       // System.out.println("Hello, log!");
        Gson gson = new Gson();
        String log = gson.toJson(NewMessagesLog);
        //System.out.println(log);
        if(Log.size()>10000) Log.clear();
        Log.addAll(NewMessagesLog);
        //System.out.println(Log.size());
        NewMessagesLog.clear();
        return Response.status(Response.Status.OK).entity(log).build();
    }

    @GET
    @Path("LogMes")
    public Response logHandler(){
        Gson gson = new Gson();
        System.out.println("you called log");
        String log = gson.toJson(Log);
       // System.out.println("log "+log);
        return Response.status(Response.Status.OK).entity(log).build();
    }

    @GET
    @Path("deleteLog")
    public Response deleteLog(){
        Log.clear();
        return Response.status(Response.Status.OK).entity("").build();
    }

}
