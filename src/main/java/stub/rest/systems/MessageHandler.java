package stub.rest.systems;

import stub.exceptions.*;
import stub.messages.inMessages.*;
import stub.transaction.*;

import java.util.Date;

import static stub.helpers.GlobalValues.Transactions;

public class MessageHandler {

    public static String routingProcessMessage(RoutingInMessage in, String receiverName){
        RoutingTransaction Transaction=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Transaction = (RoutingTransaction)getTransaction(in.getTransaction_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndTransaction(in, Transaction);
            Transaction.addMessage(in);

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedTransaction alreadyClosedTransaction) {
            Transaction.markAsFailed();
            return alreadyClosedTransaction.getMessage();
        } catch (AlreadyFailedTransaction alreadyFailedTransaction) {
            return alreadyFailedTransaction.getMessage();
        } catch (NoTransactionFound noTransactionFound) {
            if(in.isInternal()) {
                return noTransactionFound.getMessage();
            }
            else{
                    Transaction = new RoutingTransaction(RequirementsTypes.Routing, "");
                    Transactions.put(in.getTransaction_id(), Transaction);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Transaction.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInTransactionAndMesageRequirements differenceInTransactionAndMesageRequirements) {
            Transaction.markAsFailed();
            return differenceInTransactionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Transaction.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }
       return tryToCloseTransaction(Transaction);
    }

    public static String queueProcessMessage(QueueInMessage in, String receiverName){
        QueueTransaction Transaction=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Transaction = (QueueTransaction) getTransaction(in.getTransaction_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndTransaction(in, Transaction);
            Transaction.addMessage(in);
            checkMessageSequence(Transaction, in);

            checkWaitingTime(in, Transaction.maxSuitableMessageProcessingTime, Transaction);
            System.out.println("all way");

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedTransaction alreadyClosedTransaction) {
            Transaction.markAsFailed();
            return alreadyClosedTransaction.getMessage();
        } catch (AlreadyFailedTransaction alreadyFailedTransaction) {
            return alreadyFailedTransaction.getMessage();
        } catch (NoTransactionFound noTransactionFound) {
            if(in.isInternal()) {
                return noTransactionFound.getMessage();
            }
            else{
                Transaction = new QueueTransaction(RequirementsTypes.Queues, "", 10000);
                Transactions.put(in.getTransaction_id(), Transaction);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Transaction.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInTransactionAndMesageRequirements differenceInTransactionAndMesageRequirements) {
            Transaction.markAsFailed();
            return differenceInTransactionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Transaction.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        } catch (IncorrectMessageSequence incorrectMessageSequence) {
            Transaction.markAsFailed();
            return incorrectMessageSequence.getMessage();
        } catch (WaitingTimeExceeded waitingTimeExceeded) {
            Transaction.markAsFailed();
            return waitingTimeExceeded.getMessage();
        }
        return tryToCloseTransaction(Transaction);
    }

    public static String transformationProcessMessage(TransformationInMessage in, String receiverName){
        TransformationTransaction Transaction=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Transaction = (TransformationTransaction) getTransaction(in.getTransaction_id());
            checkTarget(Requirement, in.getStep().target, receiverName);
            checkInMessageAndTransaction(in, Transaction);
            Transaction.addMessage(in);

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedTransaction alreadyClosedTransaction) {
            Transaction.markAsFailed();
            return alreadyClosedTransaction.getMessage();
        } catch (AlreadyFailedTransaction alreadyFailedTransaction) {
            return alreadyFailedTransaction.getMessage();
        } catch (NoTransactionFound noTransactionFound) {
            if(in.isInternal()) {
                return noTransactionFound.getMessage();
            }
            else{
                Transaction = new TransformationTransaction(RequirementsTypes.Transformation, "");
                Transactions.put(in.getTransaction_id(), Transaction);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Transaction.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInTransactionAndMesageRequirements differenceInTransactionAndMesageRequirements) {
            Transaction.markAsFailed();
            return differenceInTransactionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Transaction.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }
        return tryToCloseTransaction(Transaction);
    }

    public static String circuitBreakerProcessMessage(CircuitBreakerInMessage in, String receiverName){
        CircuitBreakerTransaction Transaction=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Transaction = (CircuitBreakerTransaction) getTransaction(in.getTransaction_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndTransaction(in, Transaction);
            if(!in.isToBeRejected())
            Transaction.addMessage(in);
            else {
                isRejectionRight(Transaction, in);
                if(!Transaction.checkRejectionQuantity()) {
                    Transaction.addRejection();
                    return null;
                }
            }
        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedTransaction alreadyClosedTransaction) {
            Transaction.markAsFailed();
            return alreadyClosedTransaction.getMessage();
        } catch (AlreadyFailedTransaction alreadyFailedTransaction) {
            return alreadyFailedTransaction.getMessage();
        } catch (NoTransactionFound noTransactionFound) {
            if(in.isInternal()) {
                return noTransactionFound.getMessage();
            }
            else{
                if(in.isToBeRejected())
                Transaction = new CircuitBreakerTransaction(RequirementsTypes.CircuitBreaker, "",
                        in.getOrder_number(), in.getNumberOfRejections());
                Transactions.put(in.getTransaction_id(), Transaction);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Transaction.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInTransactionAndMesageRequirements differenceInTransactionAndMesageRequirements) {
            Transaction.markAsFailed();
            return differenceInTransactionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Transaction.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Transaction.markAsFailed();
            return alreadyGotTheMes.getMessage();
        } catch (stub.exceptions.differenceInrejectionMapping differenceInrejectionMapping) {
            Transaction.markAsFailed();
            return differenceInrejectionMapping.getMessage();
        }
        return tryToCloseTransaction(Transaction);
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

    public static void checkInMessageAndTransaction(AbstractInMessage in, AbstractTransaction Transaction) throws DifferenceInTransactionAndMesageRequirements {
        if(!in.getRequirement().equals(Transaction.Requirement.toString())) throw  new DifferenceInTransactionAndMesageRequirements(Transaction.transaction_id,
                Transaction.Requirement.toString(), in.getRequirement());
    }

    public static void checkWaitingTime(AbstractInMessage in, int secondsToWait, AbstractTransaction transaction) throws WaitingTimeExceeded {
     /*   System.out.println("Now " + new Date().getTime()+ " "+ new Date());
        System.out.println("Then "+ new Date(in.getTime()).getTime()+" "+ new Date(new Date(in.getTime()).getTime())+" "+ in.getTime());
        System.out.println(new Date().getTime() - new Date(in.getTime()).getTime());*/

        if(new Date().getTime() - new Date(in.getTime()).getTime()> secondsToWait)
            throw new WaitingTimeExceeded(transaction.transaction_id, transaction.Requirement.toString());
    }

    public static void checkMessageSequence(AbstractTransaction transaction,AbstractInMessage in) throws IncorrectMessageSequence {
       System.out.println(in.getOrder_number()-1+" "+(in.getOrder_number()+1));
        if ((in.getOrder_number_in_step()!=0)&&in.getOrder_number_in_step()!=in.getMsg_count()-1) {
            System.out.println("Not the first");
            if (transaction.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)
                    && !transaction.checkIfMessageAlreadyReceived(in.getOrder_number() + 1)) return;
        }
        else if(in.getMsg_count()==1) return;
        else if(in.getOrder_number_in_step()==0){
            System.out.println("first");
            if(!transaction.checkIfMessageAlreadyReceived(in.getOrder_number()+1))return;
        }
        else if(transaction.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)) return;
        throw new IncorrectMessageSequence(transaction.Requirement.toString());
    }

    public  static String tryToCloseTransaction(AbstractTransaction Transaction){
        if(Transaction.checkIfClose()){
            return "All messages received. Transaction "+ Transaction.transaction_id+" successfully closed. Requirement "+ Transaction.Requirement;
        }
        return "Message successfully processed";

    }

    public static void isRejectionRight(CircuitBreakerTransaction Transaction, CircuitBreakerInMessage in) throws differenceInrejectionMapping {
        if(Transaction.getMessageToReject()!=in.getOrder_number())
            throw new differenceInrejectionMapping(Transaction.transaction_id, Transaction.getMessageToReject(), in.getOrder_number() );

    }

}
