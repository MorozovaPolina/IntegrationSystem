package stub.rest.systems;

import stub.messages.inMessages.CircuitBreakerInMessage;
import stub.messages.inMessages.QueueInMessage;
import stub.rest.systems.AbstractSystem;

import javax.ws.rs.Path;

import java.io.IOException;

@Path("systemD")
/*@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/

public class SystemD extends AbstractSystem {

    public SystemD() {
        super("SystemD");
    }

}
