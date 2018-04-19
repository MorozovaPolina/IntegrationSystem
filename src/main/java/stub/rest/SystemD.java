package stub.rest;

import stub.messages.DemoInObject;
import stub.messages.Response;
import stub.sendMessage.MessageSender;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static stub.sendMessage.MessageHandler.processMessage;

@Path("systemD")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SystemD extends MessageSender  {

    @POST
    public Response handle(DemoInObject in) {
        System.out.println("Hey! System D here");
        return new Response(processMessage(in, "SystemD"));
    }
}
