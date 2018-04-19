package stub.sendMessage;

import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import stub.helpers.DemoHelper;
import stub.helpers.RequirementsTypes;
import stub.messages.DemoOutObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MessageSender {
    public static int sendMessage(int transaction_id, int MesNum, String from, String to, String API, RequirementsTypes requirementsType, int FRSTNumber ) throws InterruptedException, ExecutionException {
        Client client = new Client();
        AsyncWebResource webRes = client.asyncResource(API);
        int LSTNumber=FRSTNumber;
        for (int i = 0; i < MesNum; i++) {

            DemoOutObject outObject = new DemoOutObject(transaction_id, requirementsType.toString(),DemoHelper.DateTimeFormatter.get().format(new Date()), from, to, LSTNumber, i );
            outObject.setMsg_count(MesNum);
            LSTNumber++;
            Future<ClientResponse> response = webRes.type("application/json").post(ClientResponse.class, outObject.toJSon());
           // System.out.println("got" + response.getEntity(String.class));
            System.out.println("resp "+response.get().getEntity(String.class)+"");

        }
        return LSTNumber;
    }


}
