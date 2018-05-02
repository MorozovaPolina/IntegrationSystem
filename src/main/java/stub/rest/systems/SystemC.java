package stub.rest.systems;

import stub.messages.inMessages.CircuitBreakerInMessage;
import stub.messages.inMessages.QueueInMessage;
import stub.rest.systems.AbstractSystem;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.IOException;

@Path("systemC")
/*@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/

public class SystemC extends AbstractSystem {

    public SystemC() {
        super("SystemC");
    }

}
