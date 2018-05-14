package stub.rest.systems;

import javax.ws.rs.Path;

@Path("systemC")

public class SystemC extends AbstractSystem {

    public SystemC() {
        super("SystemC");
    }

}
