package stub.session;

import com.google.gson.Gson;
import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.DemoHelper;
import stub.messages.inMessages.AbstractInMessage;
import stub.messages.outMessages.CircuitBreakerOutMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Sessions;

public class CircuitBreakerSession extends AbstractSession{

    ArrayList<toReject> rejections;
    int secondsAfterRejection;
    boolean reject;
    Date lastMessageReceived;

    public CircuitBreakerSession(RequirementsTypes Requirement, String API, int secondsAfterRejection) {
        super(Requirement, API);
        rejections=new ArrayList<>();
        this.secondsAfterRejection=secondsAfterRejection;
        reject=false;
    }

    public void addRejection(toReject rejection){
        rejections.add(rejection);
    }

    public void addRejection(int ON, int NumberOfRejections){
        toReject rejection = new toReject();
        rejection.setNumberOfRejections(NumberOfRejections);
        rejection.setRejectionMessage(ON);
        addRejection(rejection);
    }

    public boolean checkRejection(int ON) {
        for(toReject rejection: rejections)
            if(rejection.getRejectionMessage()==ON) return true;
        return false;
    }

    public boolean reject(int ON){
        toReject rejection = getRejection(ON);
        System.out.println("rejection mes "+ rejection.getRejectionMessage()+" rej count "+ rejection.getNumberOfRejections() +" already rej "+ rejection.getAlreadyrejected());
        System.out.println(rejection.rejectionRequired());
        if(rejection.rejectionRequired()){
            rejection.reject();
            reject=true;
            return true;
        }
        return false;

    }

    public toReject getRejection(int ON){
        for(toReject rejection: rejections)
            if(rejection.getRejectionMessage()==ON) return rejection;
        return null;
    }

    public boolean isReject() {
        return reject;
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
                if(checkRejection(j))
                outObject = new CircuitBreakerOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j, i,
                        true, getRejection(j).getNumberOfRejections(), secondsAfterRejection);
                else outObject=new CircuitBreakerOutMessage(session_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j, i,
                        false,0, secondsAfterRejection);
                outObject.setMsg_count(step.numberOfMessagesToSend);
                j++;
                String gson = new Gson().toJson(outObject);
                callForSending(step.target, gson, API);
                Thread.sleep(1);
            }
        }
    }

    public int getSecondsAfterRejection() {
        return secondsAfterRejection;
    }

    public void setSecondsAfterRejection(int secondsAfterRejection) {
        this.secondsAfterRejection = secondsAfterRejection;
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
