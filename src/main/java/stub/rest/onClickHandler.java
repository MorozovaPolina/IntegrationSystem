package stub.rest;


import stub.helpers.RequirementsTypes;
import stub.transaction.Step;
import stub.transaction.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.concurrent.ExecutionException;



@Path("RoutingHandler")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED, MediaType.WILDCARD})
public class onClickHandler {

    @GET
    public void handleGet(){
        System.out.println("Hello!");
    }

    @POST
    public void handlePost(java.lang.String request) throws IOException, InterruptedException, ExecutionException{
        System.out.println("Click!");
        System.out.println("Sleepy");
        if(request!=null) System.out.println(request);
        java.lang.String[] helparray = request.split(System.lineSeparator());
        if (helparray.length<4||(helparray.length-1)%3!=0)return;
        java.lang.String path;
        try {
            path= helparray[helparray.length-1].split("=")[1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return;
        }
        if(path==null || path.equals("") || path.length()==0)return;
        Transaction transaction = new Transaction(RequirementsTypes.Routing, path);
        for(int i=0; i<Math.floorDiv(helparray.length, 3); i++) {
            java.lang.String source = helparray[3*i].split("=")[1];
            java.lang.String target = helparray[3*i+1].split("=")[1];
            java.lang.String count = helparray[3*i+2].split("=")[1];
            if(source.equals("Source")|| target.equals("Target"))return;
            // System.out.println(path);
            // path=path.replace("%2F", "/");
            // path=path.replace("%3A", ":");
            //  System.out.println(path);

            transaction.addStep(new Step(source, target, Integer.parseInt(count)));
            System.out.println("Start");
        }
        transaction.startTransaction();
       // System.out.println(request.getParameterNames());
        //System.out.println(request.getSource()+" "+ request.getTarget()+" "+ request.getCount());

    //    Transaction transaction = new Transaction(RequirementsTypes.Routing, "http://localhost:8888/api/systemB");
      //  transaction.addStep(Source, Target, request.getCount());
      /*  transaction.addStep(SystemA, SystemB, 2);
        transaction.addStep(SystemD, SystemB, 2);
        transaction.addStep(SystemC, SystemB, 2);
        transaction.addStep(SystemB, SystemA, 2);
        transaction.addStep(SystemA, SystemC, 2);
        transaction.addStep(SystemC, SystemA, 2);
        transaction.addStep(SystemA, SystemD, 2);
        transaction.addStep(SystemD, SystemA, 2);
        transaction.addStep(SystemB, SystemC, 2);
        transaction.addStep(SystemB, SystemD, 2);
        transaction.addStep(SystemD, SystemC, 2);
        transaction.addStep(SystemC, SystemD, 2);*/

       /* try {
            transaction.startTransaction();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */
        //return new Response("OK");

    }
}
