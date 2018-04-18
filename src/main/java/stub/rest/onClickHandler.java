package stub.rest;


import stub.helpers.RequirementsTypes;
import stub.messages.RequestStep;
import stub.messages.UserRequest;
import stub.transaction.Step;
import stub.transaction.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.concurrent.ExecutionException;



@Path("RoutingHandler")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class onClickHandler {

    @POST
    public void handlePost(UserRequest request) throws IOException, InterruptedException, ExecutionException{
        System.out.println("Click!");
        if(request.getApi()==null|| request.getApi().length()==0) return;
        Transaction transaction = new Transaction(RequirementsTypes.Routing, request.getApi());
        for (RequestStep step: request.getScenario()){
            if(step.getSource().equals("Source")||step.getTarget().equals("Target"))return;
            transaction.addStep(step.getSource(), step.getTarget(), Integer.parseInt(step.getCount()));
        }
        transaction.startTransaction();
    }
}
