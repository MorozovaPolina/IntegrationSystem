package stub.rest;


import com.sun.org.apache.xpath.internal.operations.String;
import stub.exceptions.IncorrectRequirement;
import stub.exceptions.IncorrectRequirementsystemMaping;
import stub.exceptions.NoSuchSystem;
import stub.helpers.RequirementsTypes;
import stub.messages.UserRequest;
import stub.systems.ExistingSystem;
import stub.transaction.Step;
import stub.transaction.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static stub.systems.ExistingSystem.getSystem;


@Path("onClickHandler")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED, MediaType.WILDCARD})
public class onClickHandler{

    @GET
    public void handleGet(){
        System.out.println("Hello!");
    }
    @POST
    public void handle(java.lang.String request) throws IOException, NoSuchSystem, InterruptedException, ExecutionException, IncorrectRequirementsystemMaping {
        System.out.println("Click!");
        java.lang.String[] helparray = request.split(System.lineSeparator());
        java.lang.String source = helparray[0].split("=")[1];
        java.lang.String target = helparray[1].split("=")[1];
        java.lang.String count = helparray[2].split("=")[1];
        java.lang.String path= helparray[3].split("=")[1];
       // System.out.println(path);
       // path=path.replace("%2F", "/");
       // path=path.replace("%3A", ":");
      //  System.out.println(path);
        ExistingSystem Source = getSystem(source);
        ExistingSystem Target = getSystem(target);
        Transaction transaction = new Transaction(RequirementsTypes.Routing, path);
        transaction.addStep(new Step(Source, Target, Integer.parseInt(count)));
        transaction.startTransaction();
       // System.out.println(request.getParameterNames());
        //System.out.println(request.getSource()+" "+ request.getTarget()+" "+ request.getCount());

        /*ExistingSystem SystemA = null;
        try {
            SystemA = getSystem(request.getSource());
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemB = null;
        try {
            SystemB = getSystem("SystemB");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemC = null;
        try {
            SystemC = getSystem("SystemC");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemD = null;
        try {
            SystemD = getSystem("SystemD");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }*/
    /*    ExistingSystem Source = null;
        try {
            Source = getSystem(request.getSource());
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem Target = null;
        try {
            Target = getSystem(request.getTarget());
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        Transaction transaction = new Transaction(RequirementsTypes.Routing, "http://localhost:8888/api/systemB");
        transaction.addStep(Source, Target, request.getCount());
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
        } catch (IncorrectRequirementsystemMaping incorrectRequirementsystemMaping) {
            incorrectRequirementsystemMaping.printStackTrace();
            System.out.println("Проблемы с согласованием требований и возможностей системы");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
            System.out.println("Нет такой системы");
        }*/
        //return new Response("OK");

    }
}
