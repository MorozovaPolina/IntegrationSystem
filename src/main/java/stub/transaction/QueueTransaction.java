package stub.transaction;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.RequirementsTypes;
import stub.messages.DemoInObject;

public class QueueTransaction  extends AbstractTransaction{

    public int maxSuitableMessageProcessingTime;


    public QueueTransaction(RequirementsTypes Requirement, String API, int maxSuitableMessageProcessingTime) {
        super(Requirement, API);
        this.maxSuitableMessageProcessingTime=maxSuitableMessageProcessingTime;
    }

}
