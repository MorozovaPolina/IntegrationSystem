package stub.rest.systems;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.container.ContainerResponse;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.eclipse.jetty.server.session.Session;
import stub.exceptions.AlreadyClosedSession;
import stub.exceptions.AlreadyFailedSession;
import stub.exceptions.NoSessionFound;
import stub.messages.inMessages.AbstractInMessage;
import stub.messages.inMessages.RoutingInMessage;
import stub.messages.inMessages.TransformationInMessage;
import stub.session.AbstractSession;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static stub.rest.systems.AbstractSystem.addToLog;
//import static stub.rest.systems.MessageHandler.getSession;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {
    @Override

    public Response toResponse(UnrecognizedPropertyException e) {
        String result="No "+e.getUnrecognizedPropertyName()+". ";

       if(e.getReferringClass().isInstance(new TransformationInMessage())) {
           TransformationInMessage in;
           result += "Wrong incomming message format. Transformation Testing failed. ";
           int id;
           if (e.getUnrecognizedPropertyName() != "session_id") {
               System.out.println("Error");
               System.out.println( e.getReferringClass().getMethods().length);
               try {
                   TransformationInMessage mes=new TransformationInMessage();
                   System.out.println(" invoke "+e.getReferringClass().getMethod("getRequirement", null).invoke(mes));
                   System.out.println("mes "+mes.getSession_id());
               }catch (NoSuchMethodException e1) {
                   e1.printStackTrace();
               } catch (IllegalAccessException e1) {
                   e1.printStackTrace();
               } catch (InvocationTargetException e1) {
                   e1.printStackTrace();
               }
               addToLog(e.getMessage());
           }
       }
       else System.out.println(result+" "+e.getReferringClass());

        return Response.status(Response.Status.OK).entity(e.getMessage()).build();
    }
}
