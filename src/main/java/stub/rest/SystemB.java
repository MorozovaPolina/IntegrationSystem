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

@Path("systemB")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public class SystemB extends MessageSender{
        @POST
        public Response handle(DemoInObject in) {
            System.out.println("Hey! System B here");
            String res=processMessage(in, "SystemB");
            return new Response(res);
        }

    }
