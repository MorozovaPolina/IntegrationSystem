package stub.rest.systems;

import javax.ws.rs.*;


@Path("systemA")
public class SystemA extends AbstractSystem {

    public SystemA() {
        super("SystemA");
    }

}
