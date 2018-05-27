package stub.session;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.messages.inMessages.AbstractInMessage;
import stub.rest.systems.SystemA;
import stub.rest.systems.SystemB;
import stub.rest.systems.SystemC;
import stub.rest.systems.SystemD;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.SessionNumber;

public abstract class AbstractSession {
    int session_id;
    RequirementsTypes Requirement;
    Date start_time;
    Date end_time;
    int expected_message_number;
    int received_message_number;
    ArrayList<Step> Scenario;
    SessionStatus Status;
    String API;
    AbstractInMessage[] inMessages;

    public AbstractSession(RequirementsTypes Requirement, String API) {
        this.session_id=SessionNumber;
        SessionNumber++;
        this.Requirement=Requirement;
        this.start_time=new Date();
        this.Scenario=new ArrayList<>();
        this.expected_message_number = 0;
        received_message_number=0;
        this.API=API;
    }

    public abstract void startSession() throws InterruptedException, ExecutionException ;

    public void addStep(Step Step){
        Scenario.add(Step);
        expected_message_number+=Step.numberOfMessagesToSend;
        inMessages = new AbstractInMessage[expected_message_number];
    }
    public void addStep(String Source, String Target, int NumberOfMessagesToSend){
        Step step =  new Step();
        step.source=Source;
        step.target=Target;
        step.numberOfMessagesToSend=NumberOfMessagesToSend;
        Scenario.add(step);
        expected_message_number+=NumberOfMessagesToSend;
        inMessages = new AbstractInMessage[expected_message_number];
    }

    public void deleteStep(Step Step){
        if(Scenario.contains(Step)) {
            expected_message_number -= Step.numberOfMessagesToSend;
            Scenario.remove(Step);
            inMessages = new AbstractInMessage[expected_message_number];
        }
    }

    public void addMessage(AbstractInMessage in) throws MesONisOutOfBounds, AlreadyGotTheMes {
        int index = in.getOrder_number();

        if(index>=expected_message_number) throw new MesONisOutOfBounds(index, session_id, Requirement.toString());
        else if(checkIfMessageAlreadyReceived(index)) throw new AlreadyGotTheMes(index, session_id, Requirement.toString());
        received_message_number++;
        inMessages[in.getOrder_number()] = in;
        System.out.println("Add message "+ received_message_number+" ON= "+ in.getOrder_number());
    }

    public boolean checkIfMessageAlreadyReceived(int index){
       // System.out.println("Abstract "+inMessages[index]+" "+ index);
        return (inMessages[index]!=null);
    }

    public boolean checkIfClose(){
        if(Status== SessionStatus.Opened){
            System.out.println("what to expect and what tp get "+expected_message_number+" "+ received_message_number);
            if(expected_message_number==received_message_number){
                closeSession();
                return true;
            }
        }
        return false;
    }

    /* всe, вроде, завершилось благополучно*/
    public void closeSession(){
        Status= SessionStatus.SuccessfullyEnded;
        end_time=new Date();
    }
    /* возникла ошибка, из-за которой считается, что шина не покрывает требование*/
    public void markAsFailed(){
        Status= SessionStatus.Failed;
        end_time=new Date();
    }

    public void callForSending(String source, String gson, String API) throws ExecutionException, InterruptedException {
        if ("SystemA".equals(source)) {
            SystemA.sendMessage(gson, API);
        } else if ("SystemB".equals(source)) {
            SystemB.sendMessage(gson, API);
        } else if ("SystemC".equals(source)) {
            SystemC.sendMessage(gson, API);
        } else if ("SystemD".equals(source)) {
            SystemD.sendMessage(gson, API);
        }
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public RequirementsTypes getRequirement() {
        return Requirement;
    }

    public void setRequirement(RequirementsTypes requirement) {
        Requirement = requirement;
    }

    public SessionStatus getStatus() {
        return Status;
    }

    public void setStatus(SessionStatus status) {
        Status = status;
    }

    public String getAPI() {
        return API;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public ArrayList<Step> getScenario() {
        return Scenario;
    }

    public void setScenario(ArrayList<Step> scenario) {
        Scenario = scenario;
    }

    public AbstractInMessage[] getInMessages() {
        return inMessages;
    }

    public void setInMessages(AbstractInMessage[] inMessages) {
        this.inMessages = inMessages;
    }

    public int getExpected_message_number() {
        return expected_message_number;
    }

    public void setExpected_message_number(int expected_message_number) {
        this.expected_message_number = expected_message_number;
    }

    public int getReceived_message_number() {
        return received_message_number;
    }

    public void setReceived_message_number(int received_message_number) {
        this.received_message_number = received_message_number;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
}
