package stub.transaction;

import com.google.gson.Gson;
import stub.helpers.DemoHelper;
import stub.messages.outMessages.CircuitBreakerOutMessage;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Transactions;

public class CircuitBreakerTransaction extends AbstractTransaction {

    int messageToReject;
    int numberOfRejections;
    int alreadyRejected;

    public CircuitBreakerTransaction(RequirementsTypes Requirement, String API, int messageToReject, int numberOfRejections) {
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
    public void startTransaction() throws InterruptedException, ExecutionException {
        Transactions.put(transaction_id, this);
        Status = TransactionStatus.Opened;
        System.out.println("startTransaction");
        int j=0; //итератор для подсчета номера сообщения в транзакции, а не в рамках одного шага.
        for(Step step: Scenario) {
            if(Status==TransactionStatus.Failed) break;
            for (int i = 0; i < step.numberOfMessagesToSend; i++) {
                CircuitBreakerOutMessage outObject;
                if(messageToReject==j)
                outObject = new CircuitBreakerOutMessage(transaction_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j, i,
                        true, numberOfRejections);
                else outObject=new CircuitBreakerOutMessage(transaction_id, Requirement.toString(),
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
