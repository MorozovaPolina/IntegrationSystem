package stub.transaction;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.IncorrectRequirementsystemMaping;
import stub.exceptions.MesONisOutOfBounds;
import stub.exceptions.NoSuchSystem;
import stub.helpers.RequirementsTypes;
import stub.messages.DemoInObject;
import stub.rest.SystemA;
import stub.rest.SystemB;
import stub.rest.SystemC;
import stub.rest.SystemD;
import stub.systems.ExistingSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.TransactionNumber;
import static stub.helpers.GlobalValues.Transactions;

public class Transaction {
    public int transaction_id;
    public RequirementsTypes Requirement;
    public LocalDateTime start_time;
    public LocalDateTime end_time;
    public int expected_message_number;
    public int recieved_message_number;
    public DemoInObject[] inMessages;
    public ArrayList<Step> Scenario;
    public TransactionStatus Status;
    public int NumberOfSteps;
    public String API;

    public Transaction(RequirementsTypes Requirement, String API) {
        this.transaction_id=TransactionNumber;
        TransactionNumber++;
        this.Requirement=Requirement;
        this.start_time=LocalDateTime.now();
        this.Scenario=new ArrayList<>();
        this.expected_message_number = 0;
        NumberOfSteps=0;
        recieved_message_number=0;
        this.API=API;
    }


    public void addStep(Step Step){
        Scenario.add(Step);
        NumberOfSteps++;
        expected_message_number+=Step.NumberOfMessagesToSend;
        inMessages = new DemoInObject[expected_message_number];
    }
    public void addStep(ExistingSystem Source, ExistingSystem Target, int NumberOfMessagesToSend){
        Scenario.add( new Step(Source, Target, NumberOfMessagesToSend));
        expected_message_number+=NumberOfMessagesToSend;
        NumberOfSteps++;
        inMessages = new DemoInObject[expected_message_number];
    }

    public void deleteStep(Step Step){
       if(Scenario.contains(Step)) {
           NumberOfSteps--;
           expected_message_number -= Step.NumberOfMessagesToSend;
           Scenario.remove(Step);

           inMessages = new DemoInObject[expected_message_number];
       }
    }

    public void startTransaction() throws InterruptedException, ExecutionException, NoSuchSystem, IncorrectRequirementsystemMaping {
        Transactions.put(transaction_id, this);
        Status = TransactionStatus.Opened;
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status==TransactionStatus.Failed) break;
            if ("SystemA".equals(step.Target.getSystemName())) {
                j =SystemA.sendMessage(transaction_id, step.NumberOfMessagesToSend, step.Source, step.Target, API, Requirement, j);
            } else if ("SystemB".equals(step.Target.getSystemName())) {
                j= SystemB.sendMessage(transaction_id, step.NumberOfMessagesToSend, step.Source, step.Target, API, Requirement, j);
            } else if ("SystemC".equals(step.Target.getSystemName())) {
                j = SystemC.sendMessage(transaction_id, step.NumberOfMessagesToSend, step.Source, step.Target, API, Requirement, j);
            } else if ("SystemD".equals(step.Target.getSystemName())) {
                j = SystemD.sendMessage(transaction_id, step.NumberOfMessagesToSend, step.Source, step.Target, API, Requirement, j);
            }
        }

    }

    public void addMessage(DemoInObject in) throws MesONisOutOfBounds, AlreadyGotTheMes {
        int index = Integer.parseInt(in.getOrder_number());
        if(index>=expected_message_number) throw new MesONisOutOfBounds(index, transaction_id);

        else if(inMessages[index]!=null) throw new AlreadyGotTheMes(index, transaction_id);
        inMessages[Integer.parseInt(in.getOrder_number())] = in;
        recieved_message_number++;
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
