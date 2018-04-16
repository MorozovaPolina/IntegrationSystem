package stub;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import stub.helpers.RequirementsTypes;
import stub.systems.ExistingSystem;
import stub.rest.SystemA;


import java.util.Locale;

import static stub.Initialization.initialization;
import static stub.sendMessage.MessageSender.sendMessage;


public class Launcher {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Server server = new Server(8888);
        WebAppContext context = new WebAppContext("src/main/webapp", "/");

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            // Fix for Windows, so Jetty doesn't lock files
            context.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        }

        server.setHandler(context);
        server.start();
        initialization();

    }
}
