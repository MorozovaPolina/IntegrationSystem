package stub.session;

import com.google.gson.Gson;
import stub.helpers.DemoHelper;
import stub.messages.outMessages.TransformationOutMessage;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Sessions;

public class TransformationSession extends AbstractSession {
    public TransformationSession(RequirementsTypes Requirement, String API) {
        super(Requirement, API);
    }

    @Override
    public void startSession() throws InterruptedException, ExecutionException {
        Sessions.put(session_id, this);
        Status = SessionStatus.Opened;
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status== SessionStatus.Failed) break;
            for (int i = 0; i < step.numberOfMessagesToSend; i++) {
                TransformationOutMessage outObject = new TransformationOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j,
                        i);
                outObject.setMsg_count(step.numberOfMessagesToSend);
                j++;
                String gson = new Gson().toJson(outObject);
                callForSending(step.target, gson, API);
                Thread.sleep(1);
            }
        }
    }
}
