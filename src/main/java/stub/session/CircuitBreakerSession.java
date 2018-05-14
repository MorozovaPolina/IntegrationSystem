package stub.session;

import com.google.gson.Gson;
import stub.helpers.DemoHelper;
import stub.messages.outMessages.CircuitBreakerOutMessage;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Sessions;

public class CircuitBreakerSession extends AbstractSession{

    int messageToReject;
    int numberOfRejections;
    int alreadyRejected;

    public CircuitBreakerSession(RequirementsTypes Requirement, String API, int messageToReject, int numberOfRejections) {
        super(Requirement, API);
        this.messageToReject=messageToReject;
        this.numberOfRejections=numberOfRejections;
        alreadyRejected=0;
    }

    public int getMessageToReject() {
        return messageToReject;
    }

    public void setMessageToReject(int messageToReject) {
        this.messageToReject = messageToReject;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public int getAlreadyRejected() {
        return alreadyRejected;
    }

    public void setAlreadyRejected(int alreadyRejected) {
        this.alreadyRejected = alreadyRejected;
    }
    public boolean checkRejectionQuantity(){
        return alreadyRejected==numberOfRejections;
    }

    public void addRejection(){
        alreadyRejected++;
    }

    @Override
    public void startSession() throws InterruptedException, ExecutionException {
        Sessions.put(session_id, this);
        Status = SessionStatus.Opened;
        System.out.println("startSession");
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status== SessionStatus.Failed) break;
            for (int i = 0; i < step.numberOfMessagesToSend; i++) {
                CircuitBreakerOutMessage outObject;
                if(messageToReject==j)
                outObject = new CircuitBreakerOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j, i,
                        true, numberOfRejections);
                else outObject=new CircuitBreakerOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j, i,
                        false,0);
                outObject.setMsg_count(step.numberOfMessagesToSend);
                j++;
                String gson = new Gson().toJson(outObject);
                callForSending(step.target, gson, API);
                Thread.sleep(1);
            }
        }
    }
}
