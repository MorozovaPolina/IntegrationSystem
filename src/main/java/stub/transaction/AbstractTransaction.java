package stub.transaction;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.RequirementsTypes;
import stub.messages.AbstractInMessage;
import stub.messages.DemoInObject;
import stub.rest.SystemA;
import stub.rest.SystemB;
import stub.rest.SystemC;
import stub.rest.SystemD;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.TransactionNumber;
import static stub.helpers.GlobalValues.Transactions;

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

    public void startTransaction() throws InterruptedException, ExecutionException {
        Transactions.put(transaction_id, this);
        Status = TransactionStatus.Opened;
        System.out.println("startTransaction");
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status==TransactionStatus.Failed) break;
            if ("SystemA".equals(step.target)) {
                j = SystemA.sendMessage(transaction_id, step.numberOfMessagesToSend, step.source, step.target, API, Requirement, j);
            } else if ("SystemB".equals(step.target)) {
                j= SystemB.sendMessage(transaction_id, step.numberOfMessagesToSend, step.source, step.target, API, Requirement, j);
            } else if ("SystemC".equals(step.target)) {
                j = SystemC.sendMessage(transaction_id, step.numberOfMessagesToSend, step.source, step.target, API, Requirement, j);
            } else if ("SystemD".equals(step.target)) {
                j = SystemD.sendMessage(transaction_id, step.numberOfMessagesToSend, step.source, step.target, API, Requirement, j);
            }
        }
    }

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
        inMessages[in.getOrder_number()] = in;
        received_message_number++;
    }

    public boolean checkIfMessageAlreadyReceived(int index){
        if(inMessages[index]==null) return false;
        else return true;
    }

    public boolean checkIfClose(){
        if(Status==TransactionStatus.Opened){
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

}
