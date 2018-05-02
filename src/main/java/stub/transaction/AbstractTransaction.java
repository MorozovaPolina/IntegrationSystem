package stub.transaction;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.messages.inMessages.AbstractInMessage;
import stub.rest.systems.SystemA;
import stub.rest.systems.SystemB;
import stub.rest.systems.SystemC;
import stub.rest.systems.SystemD;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.TransactionNumber;

public abstract class AbstractTransaction {
    public int transaction_id;
    public RequirementsTypes Requirement;
    public LocalDateTime start_time;
    public LocalDateTime end_time;
    public int expected_message_number;
    public int received_message_number;
    public ArrayList<Step> Scenario;
    public TransactionStatus Status;
    public String API;
    public AbstractInMessage[] inMessages;

    public AbstractTransaction(RequirementsTypes Requirement, String API) {
        this.transaction_id=TransactionNumber;
        TransactionNumber++;
        this.Requirement=Requirement;
        this.start_time=LocalDateTime.now();
        this.Scenario=new ArrayList<>();
        this.expected_message_number = 0;
        received_message_number=0;
        this.API=API;
    }

    public abstract void startTransaction() throws InterruptedException, ExecutionException ;

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

        if(index>=expected_message_number) throw new MesONisOutOfBounds(index, transaction_id, Requirement.toString());
        else if(checkIfMessageAlreadyReceived(index)) throw new AlreadyGotTheMes(index, transaction_id, Requirement.toString());
        received_message_number++;
        inMessages[in.getOrder_number()] = in;
        System.out.println("Add message "+ received_message_number+" ON= "+ in.getOrder_number());
    }

    public boolean checkIfMessageAlreadyReceived(int index){
       // System.out.println("Abstract "+inMessages[index]+" "+ index);
        System.out.println(index);
        return (inMessages[index]!=null);
    }

    public boolean checkIfClose(){
        if(Status==TransactionStatus.Opened){
            System.out.println("what to expect and what tp get "+expected_message_number+" "+ received_message_number);
            if(expected_message_number==received_message_number){
                closeTransaction();
                return true;
            }
        }
        return false;
    }

    /* всe, вроде, завершилось благополучно*/
    public void closeTransaction(){
        Status=TransactionStatus.SuccessfullyEnded;
        end_time=LocalDateTime.now();
    }
    /* возникла ошибка, из-за которой считается, что шина не покрывает требование*/
    public void markAsFailed(){
        Status=TransactionStatus.Failed;
        end_time=LocalDateTime.now();
    }

    public void callForSending(String target, String gson, String API) throws ExecutionException, InterruptedException {
        if ("SystemA".equals(target)) {
            SystemA.sendMessage(gson, API);
        } else if ("SystemB".equals(target)) {
            SystemB.sendMessage(gson, API);
        } else if ("SystemC".equals(target)) {
            SystemC.sendMessage(gson, API);
        } else if ("SystemD".equals(target)) {
            SystemD.sendMessage(gson, API);
        }
    }

}
