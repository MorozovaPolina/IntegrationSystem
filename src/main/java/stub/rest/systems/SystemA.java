package stub.rest.systems;

import stub.messages.inMessages.CircuitBreakerInMessage;
import stub.messages.inMessages.QueueInMessage;
import stub.rest.systems.AbstractSystem;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.IOException;


@Path("systemA")
/*@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/
public class SystemA extends AbstractSystem {

    public SystemA() {
        super("SystemA");
    }



   /* @Override
    public Response queueHandler(QueueInMessage in) throws IOException {
        return null;
    }

    @Override
    public Response circuitBreakerHandler(CircuitBreakerInMessage in) throws IOException {
        return null;
    }

    @POST
    @Path("transformation")
    public Response transformationHandler(){
        return new Response("");
    }*/
    /*@POST
    public Response handle(DemoInObject in) {
        //return new Integer(18013);
        System.out.println(in.getTransaction_type());
        return Response.status(18013).entity(in).build();
    }*/


}
