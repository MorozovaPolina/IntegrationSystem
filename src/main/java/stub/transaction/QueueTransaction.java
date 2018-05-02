package stub.transaction;

import com.google.gson.Gson;
import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.DemoHelper;
import stub.messages.inMessages.AbstractInMessage;
import stub.messages.outMessages.QueueOutMessage;
import stub.messages.outMessages.TransformationOutMessage;
import stub.rest.systems.SystemA;
import stub.rest.systems.SystemB;
import stub.rest.systems.SystemC;
import stub.rest.systems.SystemD;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Transactions;

public class QueueTransaction  extends AbstractTransaction{

    public int maxSuitableMessageProcessingTime;


    public QueueTransaction(RequirementsTypes Requirement, String API, int maxSuitableMessageProcessingTime) {
        super(Requirement, API);
        this.maxSuitableMessageProcessingTime=maxSuitableMessageProcessingTime;
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
                QueueOutMessage outObject = new QueueOutMessage(transaction_id, Requirement.toString(),
                        DemoHelper.DateTimeFormatter.get().format(new Date()), step.source, step.target, j,
                        i, maxSuitableMessageProcessingTime);
                outObject.setMsg_count(step.numberOfMessagesToSend);
                j++;
                String gson = new Gson().toJson(outObject);
                callForSending(step.target, gson, API);
                Thread.sleep(1);
            }
        }
    }
}
