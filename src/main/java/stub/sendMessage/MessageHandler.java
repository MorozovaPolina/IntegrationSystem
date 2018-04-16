package stub.sendMessage;

import stub.exceptions.*;
import stub.helpers.RequirementsTypes;
import stub.messages.DemoInObject;
import stub.systems.ExistingSystem;
import stub.transaction.Transaction;
import stub.transaction.TransactionStatus;

import java.time.LocalDateTime;
import java.util.Date;

import static stub.helpers.Constants.SecondsToWaitForAnOrdinaryMessage;
import static stub.helpers.GlobalValues.Transactions;
import static stub.systems.ExistingSystem.getSystem;
import static stub.transaction.TransactionStatus.*;

public class MessageHandler {




    public static String processMessage(DemoInObject in, String recieverName){
        Transaction Transaction=null;
        RequirementsTypes Requirement;
        ExistingSystem Target;
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
            Requirement = getRequirement(in.getRequirement());
        } catch (IncorrectRequirement incorrectRequirement) {
            incorrectRequirement.printStackTrace();
            Transaction.markAsFailed();
            return incorrectRequirement.getMessage().toString();
        }
        try {
            Target = getTarget(Requirement, in.getTarget(), recieverName);
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
            Transaction.markAsFailed();
            return noSuchSystem.getMessage().toString();
        } catch (IncorrectRequirementsystemMaping incorrectRequirementsystemMaping) {
            incorrectRequirementsystemMaping.printStackTrace();
            Transaction.markAsFailed();
            return incorrectRequirementsystemMaping.getMessage().toString();
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
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }

        try {
            checkWaitingTime(in, SecondsToWaitForAnOrdinaryMessage);
        } catch (WaitingTimeExceeded waitingTimeExceeded) {
            waitingTimeExceeded.printStackTrace();
            Transaction.markAsFailed();
            return  waitingTimeExceeded.getMessage();
        }

        return "Successfully";
    }

    public static Transaction getTransaction(String transaction_id) throws NoTransactionFound, AlreadyFailedTransaction, AlreadyClosedTransaction {
        int transactionID = Integer.parseInt(transaction_id);
        if(!Transactions.containsKey(transactionID)){
            //check in DB shall be added. Because some of transactions could be successfully saved in DB
            throw new NoTransactionFound(transactionID);
        }
        else {
            Transaction Transaction = Transactions.get(transactionID);
            switch (Transaction.Status){
                case Failed: throw new AlreadyFailedTransaction(transactionID);
                case SuccessfullyEnded: Transaction.markAsFailed();
                    throw new AlreadyClosedTransaction(transactionID);
                case Opened: return Transaction;
            }
        }
        return null;
    }

    public static ExistingSystem getTarget(RequirementsTypes Requirement, String Target, String recieverName) throws NoSuchSystem, IncorrectRequirementsystemMaping, WrongMessageReceiver {
        ExistingSystem target;
        try {
            target=getSystem(Target);
        } catch (NoSuchSystem noSuchSystem) {
            throw noSuchSystem;
        }
        if (!target.getAvailableRequirements().contains(Requirement)) throw new IncorrectRequirementsystemMaping(Requirement.toString(), Target);
        if(!target.getSystemName().equals(recieverName)){
            throw new WrongMessageReceiver();
        }
        return target;
    }

    public static RequirementsTypes getRequirement (String requirement) throws IncorrectRequirement {
        RequirementsTypes Requirement= RequirementsTypes.valueOf(requirement);
        if(Requirement==null) throw new IncorrectRequirement(requirement);
        return Requirement;
    }

    public static void checkInMessageAndTransaction(DemoInObject in, Transaction Transaction) throws DifferenceInTransactionAndMesageRequirements {
        if(!in.getRequirement().equals(Transaction.Requirement.toString())) throw  new DifferenceInTransactionAndMesageRequirements(Transaction.transaction_id,
                Transaction.Requirement.toString(), in.getRequirement());
    }

    public static void checkWaitingTime(DemoInObject in, int secondsToWait) throws WaitingTimeExceeded {
        if(new Date().getTime() - Date.parse(in.getTime())> secondsToWait)throw new WaitingTimeExceeded();
    }

}
