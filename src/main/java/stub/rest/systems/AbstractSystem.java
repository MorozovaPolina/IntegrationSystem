package stub.rest.systems;

import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stub.helpers.DemoHelper;
import stub.messages.inMessages.TransformationInMessage;
import stub.messages.outMessages.MessageProcResult;
import stub.messages.inMessages.CircuitBreakerInMessage;
import stub.messages.inMessages.QueueInMessage;
import stub.messages.inMessages.RoutingInMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static stub.helpers.GlobalValues.NewMessagesLog;
import static stub.rest.systems.MessageHandler.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractSystem {
    String name;
    public static Logger logger = LoggerFactory.getLogger("requestLogger");
    public AbstractSystem(String name){
        this.name=name;
    }

    public static void sendMessage(String message, String API) throws InterruptedException, ExecutionException {
        Client client = new Client();
        AsyncWebResource webRes = client.asyncResource(API);
        addToLog("send to " + API+" message "+message);
        webRes.type("application/json").post(ClientResponse.class, message);
    }

    public static void addToLog(String message) {
        logger.info(message);
        NewMessagesLog.add(DemoHelper.DateTimeFormatter.get().format(new Date())+" "+message);

    }


    @Path("routing")
    @POST
    public Response routingHandle(RoutingInMessage in) throws IOException, UnrecognizedPropertyException
    {

        System.out.println("routing");
        String resp = routingProcessMessage(in, name);
        addToLog(resp);
        MessageProcResult result = new MessageProcResult(resp);
        return Response.status(Status.OK).entity(result.toJSON()).build();
    };

    @Path("queue")
    @POST
    public Response queueHandler(QueueInMessage in) throws IOException, UnrecognizedPropertyException {
        System.out.println("in queue");
        String resp = queueProcessMessage(in, name);
        addToLog(resp);
        MessageProcResult result = new MessageProcResult(resp);
        return Response.status(Status.OK).entity(result.toJSON()).build();
    }

    @POST
    @Path("circuitBreaker")
    public Response circuitBreakerHandler(CircuitBreakerInMessage in) throws IOException, UnrecognizedPropertyException {
        String resp = circuitBreakerProcessMessage(in, name);
        if(resp==null){
            addToLog("Reject message to "+ name);
            return Response.status(Status.BAD_REQUEST).entity("").build();
        }
        else{
            MessageProcResult result = new MessageProcResult(resp);
            addToLog(resp);
            return Response.status(Status.OK).entity(result.toJSON()).build();
        }
    }

    @POST
    @Path("transformation")
    public Response transformationHandler(TransformationInMessage in) throws UnrecognizedPropertyException{
        String resp = transformationProcessMessage(in, name);
        addToLog(resp);
        MessageProcResult result = new MessageProcResult(resp);
        return Response.status(Status.OK).entity(result.toJSON()).build();

    }


}
