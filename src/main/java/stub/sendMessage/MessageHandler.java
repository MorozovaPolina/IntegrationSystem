package stub.sendMessage;

import stub.exceptions.*;
import stub.helpers.RequirementsTypes;
import stub.messages.AbstractInMessage;
import stub.messages.DemoInObject;
import stub.transaction.AbstractTransaction;
import stub.transaction.Transaction;

import java.util.Date;

import static stub.helpers.Constants.SecondsToWaitForAnOrdinaryMessage;
import static stub.helpers.GlobalValues.Transactions;

public class MessageHandler {

    public static String ordinaryProcessMessage(DemoInObject in, String receiverName){
        AbstractTransaction Transaction=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
        } catch (IncorrectRequirement incorrectRequirement) {
            incorrectRequirement.printStackTrace();
            Transaction.markAsFailed();
            return incorrectRequirement.getMessage().toString();
        }
        try {
           Transaction = getTransaction(in.getTransaction_id());
        } catch (AlreadyClosedTransaction alreadyClosedTransaction) {
            alreadyClosedTransaction.printStackTrace();
            Transaction.markAsFailed();
            return alreadyClosedTransaction.getMessage().toString();
        } catch (AlreadyFailedTransaction alreadyFailedTransaction) {
            alreadyFailedTransaction.printStackTrace();
            return alreadyFailedTransaction.getMessage().toString();
        } catch (NoTransactionFound noTransactionFound) {
            noTransactionFound.printStackTrace();
            return noTransactionFound.getMessage().toString();
        }
        try {
            checkTarget(Requirement, in.getTarget(), receiverName);
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            wrongMessageReceiver.printStackTrace();
            Transaction.markAsFailed();
            return wrongMessageReceiver.getMessage();
        }
        try {
            checkInMessageAndTransaction(in, Transaction);
        } catch (DifferenceInTransactionAndMesageRequirements differenceInTransactionAndMesageRequirements) {
            differenceInTransactionAndMesageRequirements.printStackTrace();
            Transaction.markAsFailed();
            return differenceInTransactionAndMesageRequirements.getMessage().toString();
        }
        try {
            Transaction.addMessage(in);
        } catch (MesONisOutOfBounds mesONisOutOfBounds) {
            mesONisOutOfBounds.printStackTrace();
            Transaction.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            alreadyGotTheMes.printStackTrace();
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }

        try {
            checkMessageSequence(Transaction, in);
        } catch (IncorrectMessageSequence incorrectMessageSequence) {
            incorrectMessageSequence.printStackTrace();
            Transaction.markAsFailed();
            return incorrectMessageSequence.getMessage();
        }
        return "Successfully";
    }

    public static AbstractTransaction getTransaction(int transaction_id) throws NoTransactionFound, AlreadyFailedTransaction, AlreadyClosedTransaction {
        int transactionID = transaction_id;
        if(!Transactions.containsKey(transactionID)){
            throw new NoTransactionFound(transactionID);
        }
        else {

            AbstractTransaction Transaction = Transactions.get(transactionID);
            switch (Transaction.Status){
                case Failed: throw new AlreadyFailedTransaction(transactionID, Transaction.Requirement.toString());
                case SuccessfullyEnded: throw new AlreadyClosedTransaction(transactionID, Transaction.Requirement.toString());
                case Opened: return Transaction;
            }
        }
        return null;
    }

    public static void checkTarget(RequirementsTypes Requirement, String Target, String receiverName) throws WrongMessageReceiver {

        if(!Target.equals(receiverName)){
            throw new WrongMessageReceiver(receiverName, Target, Requirement.toString());
        }
    }

    public static RequirementsTypes getRequirement (String requirement) throws IncorrectRequirement {
        RequirementsTypes Requirement= RequirementsTypes.valueOf(requirement);
        if(Requirement==null) throw new IncorrectRequirement(requirement);
        return Requirement;
    }

    public static void checkInMessageAndTransaction(DemoInObject in, AbstractTransaction Transaction) throws DifferenceInTransactionAndMesageRequirements {
        if(!in.getRequirement().equals(Transaction.Requirement.toString())) throw  new DifferenceInTransactionAndMesageRequirements(Transaction.transaction_id,
                Transaction.Requirement.toString(), in.getRequirement());
    }

    public static void checkWaitingTime(DemoInObject in, int secondsToWait, AbstractTransaction transaction) throws WaitingTimeExceeded {
        if(new Date().getTime() - Date.parse(in.getTime())> secondsToWait)
            throw new WaitingTimeExceeded(transaction.transaction_id, transaction.Requirement.toString());
    }

    public static void checkMessageSequence(AbstractTransaction transaction,AbstractInMessage in) throws IncorrectMessageSequence {
        if (in.getOrder_number_in_step()!=0&&in.getOrder_number_in_step()!=in.getMsg_count()-1) {
            if (transaction.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)
                    && !transaction.checkIfMessageAlreadyReceived(in.getOrder_number() + 1)) return;
        }
        else if(in.getOrder_number_in_step()==0&&in.getOrder_number_in_step()==in.getMsg_count()-1) return;
        else if(in.getOrder_number_in_step()==0){
            if(!transaction.checkIfMessageAlreadyReceived(in.getOrder_number()))return;
        }
        else if(transaction.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)) return;
        throw new IncorrectMessageSequence(transaction.Requirement.toString());
    }

}
