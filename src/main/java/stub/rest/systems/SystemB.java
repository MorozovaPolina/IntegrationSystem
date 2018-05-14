package stub.rest.systems;

import javax.ws.rs.Path;

@Path("systemB")

public class SystemB extends AbstractSystem {

    public SystemB() {
        super("SystemB");
    }

}
