package stub.session;

import com.google.gson.Gson;
import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.DemoHelper;
import stub.messages.inMessages.AbstractInMessage;
import stub.messages.outMessages.QueueOutMessage;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Sessions;


public class QueueSession  extends AbstractSession{

    int maxSuitableMessageProcessingTime;
    int minSuitableMessageProcessingTime;
    Date lastMessageReceived;


    public QueueSession(RequirementsTypes Requirement, String API, int maxSuitableMessageProcessingTime, int minSuitableMessageProcessingTime) {
        super(Requirement, API);
        this.maxSuitableMessageProcessingTime=maxSuitableMessageProcessingTime;
        this.minSuitableMessageProcessingTime=minSuitableMessageProcessingTime;
    }

    @Override
    public void startSession() throws InterruptedException, ExecutionException {
        Sessions.put(session_id, this);
        Status = SessionStatus.Opened;
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status== SessionStatus.Failed) break;
            for (int i = 0; i < step.numberOfMessagesToSend; i++) {
                QueueOutMessage outObject = new QueueOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j,
                        i, maxSuitableMessageProcessingTime, minSuitableMessageProcessingTime);
                outObject.setMsg_count(step.numberOfMessagesToSend);
                j++;
                String gson = new Gson().toJson(outObject);
                callForSending(step.target, gson, API);
                Thread.sleep(1);
            }
        }
    }

    public int getMaxSuitableMessageProcessingTime() {
        return maxSuitableMessageProcessingTime;
    }

    public void setMaxSuitableMessageProcessingTime(int maxSuitableMessageProcessingTime) {
        this.maxSuitableMessageProcessingTime = maxSuitableMessageProcessingTime;
    }

    public int getMinSuitableMessageProcessingTime() {
        return minSuitableMessageProcessingTime;
    }

    public void setMinSuitableMessageProcessingTime(int minSuitableMessageProcessingTime) {
        this.minSuitableMessageProcessingTime = minSuitableMessageProcessingTime;
    }

    public Date getLastMessageReceived() {
        return lastMessageReceived;
    }

    public void receiveMessage(){
        lastMessageReceived=new Date();
    }

    @Override
    public void addMessage(AbstractInMessage in) throws MesONisOutOfBounds, AlreadyGotTheMes {
    super.addMessage(in);
    receiveMessage();
    }
}
