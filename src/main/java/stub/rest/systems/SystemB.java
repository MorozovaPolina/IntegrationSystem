package stub.rest.systems;

import stub.messages.inMessages.CircuitBreakerInMessage;
import stub.messages.inMessages.QueueInMessage;
import stub.rest.systems.AbstractSystem;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.IOException;

@Path("systemB")
    public class SystemB extends AbstractSystem {
    public SystemB() {
        super("SystemB");
    }

}
