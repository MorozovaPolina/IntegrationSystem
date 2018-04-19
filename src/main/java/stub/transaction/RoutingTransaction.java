package stub.transaction;

import stub.exceptions.AlreadyGotTheMes;
import stub.exceptions.MesONisOutOfBounds;
import stub.helpers.RequirementsTypes;
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

public class RoutingTransaction extends AbstractTransaction {

    public RoutingTransaction(RequirementsTypes Requirement, String API) {
        super(Requirement, API);
    }
}
