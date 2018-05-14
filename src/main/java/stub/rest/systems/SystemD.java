package stub.rest.systems;

import javax.ws.rs.Path;

@Path("systemD")

public class SystemD extends AbstractSystem {

    public SystemD() {
        super("SystemD");
    }

}
