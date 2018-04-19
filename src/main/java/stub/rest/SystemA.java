package stub.rest;

import stub.messages.DemoInObject;
import stub.messages.Response;
import stub.sendMessage.MessageSender;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static stub.sendMessage.MessageHandler.processMessage;

@Path("systemA")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SystemA extends MessageSender {


    @POST
    public Response handle(DemoInObject in) {
        System.out.println("Hey! System A here");
        String res=processMessage(in, "SystemA");
        System.out.println(res);
        return new Response(res);
    }
    /*@POST
    public Response handle(DemoInObject in) {
        //return new Integer(18013);
        System.out.println(in.getTransaction_type());
        return Response.status(18013).entity(in).build();
    }*/

}
