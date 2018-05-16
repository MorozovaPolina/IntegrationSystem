package stub.rest.systems;

import stub.exceptions.*;
import stub.messages.inMessages.*;
import stub.session.*;

import java.util.Date;

import static stub.helpers.GlobalValues.Sessions;

public class MessageHandler {

    public static String routingProcessMessage(RoutingInMessage in, String receiverName){
        RoutingSession session=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            session = (RoutingSession) getSession(in.getSession_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndSession(in, session);
            session.addMessage(in);

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedSession alreadyClosedSession) {
            session.markAsFailed();
            return alreadyClosedSession.getMessage();
        } catch (AlreadyFailedSession alreadyFailedSession) {
            return alreadyFailedSession.getMessage();
        } catch (NoSessionFound noSessionFound) {
            if(in.getIsInternal()) {
                return noSessionFound.getMessage();
            }
            else{
                    session = new RoutingSession(RequirementsTypes.Routing, "");
                    Sessions.put(in.getSession_id(), session);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            session.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInSessionAndMesageRequirements differenceInSessionAndMesageRequirements) {
            session.markAsFailed();
            return differenceInSessionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            session.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            session.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }
       return tryToCloseSession(session);
    }

    public static String queueProcessMessage(QueueInMessage in, String receiverName){
        QueueSession session=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            session = (QueueSession) getSession(in.getSession_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndSession(in, session);
            session.addMessage(in);
            checkMessageSequence(session, in);
            checkWaitingTimeLack(session.getLastMessageReceived(), session.getMinSuitableMessageProcessingTime(), session);
            checkWaitingTimeExceeded(in, session.getMaxSuitableMessageProcessingTime(), session);
            System.out.println("all way");

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedSession alreadyClosedSession) {
            session.markAsFailed();
            return alreadyClosedSession.getMessage();
        } catch (AlreadyFailedSession alreadyFailedSession) {
            return alreadyFailedSession.getMessage();
        } catch (NoSessionFound noSessionFound) {
            if(in.getIsInternal()) {
                return noSessionFound.getMessage();
            }
            else{
                session = new QueueSession(RequirementsTypes.Queues, "", in.getMaxSuitableMessageProcessingTime(),
                        in.getMinSuitableMessageProcessingTime());
                Sessions.put(in.getSession_id(), session);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            session.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInSessionAndMesageRequirements differenceInSessionAndMesageRequirements) {
            session.markAsFailed();
            return differenceInSessionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            session.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            session.markAsFailed();
            return alreadyGotTheMes.getMessage();
        } catch (IncorrectMessageSequence incorrectMessageSequence) {
            session.markAsFailed();
            return incorrectMessageSequence.getMessage();
        } catch (WaitingTimeExceeded waitingTimeExceeded) {
            session.markAsFailed();
            return waitingTimeExceeded.getMessage();
        } catch (WaitingTimeLack waitingTimeLack) {
            session.markAsFailed();
            return waitingTimeLack.getMessage();
        }
        return tryToCloseSession(session);
    }

    public static String transformationProcessMessage(TransformationInMessage in, String receiverName){
        TransformationSession Session=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Session = (TransformationSession) getSession(in.getSession_id());
            checkTarget(Requirement, in.getStep().getTarget(), receiverName);
            checkInMessageAndSession(in, Session);
            Session.addMessage(in);

        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedSession alreadyClosedSession) {
            Session.markAsFailed();
            return alreadyClosedSession.getMessage();
        } catch (AlreadyFailedSession alreadyFailedSession) {
            return alreadyFailedSession.getMessage();
        } catch (NoSessionFound noSessionFound) {
            if(in.getIsInternal()) {
                return noSessionFound.getMessage();
            }
            else{
                Session = new TransformationSession(RequirementsTypes.Transformation, "");
                Sessions.put(in.getSession_id(), Session);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Session.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInSessionAndMesageRequirements differenceInSessionAndMesageRequirements) {
            Session.markAsFailed();
            return differenceInSessionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Session.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Session.markAsFailed();
            return alreadyGotTheMes.getMessage();
        }
        return tryToCloseSession(Session);
    }

    public static String circuitBreakerProcessMessage(CircuitBreakerInMessage in, String receiverName){
        CircuitBreakerSession Session=null;
        RequirementsTypes Requirement;
        try {
            Requirement = getRequirement(in.getRequirement());
            Session = (CircuitBreakerSession) getSession(in.getSession_id());
            checkTarget(Requirement, in.getTarget(), receiverName);
            checkInMessageAndSession(in, Session);
            if(Session.isReject()) checkWaitingTimeLack(Session.getLastMessageReceived(), Session.getSecondsAfterRejection(), Session);
            if(!in.isToBeRejected())
            Session.addMessage(in);
            else {
                isRejectionRight(Session, in);
                System.out.println("in mes "+ in.isToBeRejected() );
                if(Session.reject(in.getOrder_number())) {
                    return null;
                }
                else
                    Session.addMessage(in);
            }
        } catch (IncorrectRequirement incorrectRequirement) {
            return incorrectRequirement.getMessage();
        } catch (AlreadyClosedSession alreadyClosedSession) {
            Session.markAsFailed();
            return alreadyClosedSession.getMessage();
        } catch (AlreadyFailedSession alreadyFailedSession) {
            return alreadyFailedSession.getMessage();
        } catch (NoSessionFound noSessionFound) {
            if(in.getIsInternal()) {
                return noSessionFound.getMessage();
            }
            else{
                if(in.isToBeRejected())
                Session = new CircuitBreakerSession(RequirementsTypes.CircuitBreaker, "", in.getSecondsAfterRejection());
                Session.addRejection(in.getOrder_number(), in.getNumberOfRejections());
                Sessions.put(in.getSession_id(), Session);
            }
        } catch (WrongMessageReceiver wrongMessageReceiver) {
            Session.markAsFailed();
            return wrongMessageReceiver.getMessage();
        } catch (DifferenceInSessionAndMesageRequirements differenceInSessionAndMesageRequirements) {
            Session.markAsFailed();
            return differenceInSessionAndMesageRequirements.getMessage();
        }catch (MesONisOutOfBounds mesONisOutOfBounds) {
            Session.markAsFailed();
            return mesONisOutOfBounds.getMessage();
        } catch (AlreadyGotTheMes alreadyGotTheMes) {
            Session.markAsFailed();
            return alreadyGotTheMes.getMessage();
        } catch (stub.exceptions.differenceInRejectionMapping differenceInrejectionMapping) {
            if(in.getIsInternal()) {
                Session.markAsFailed();
                return differenceInrejectionMapping.getMessage();
            }
            else {
                Session.addRejection(in.getOrder_number(), in.getNumberOfRejections());
                Session.reject(in.getOrder_number());
                return null;
            }
        } catch (WaitingTimeLack waitingTimeLack) {
            Session.markAsFailed();
            return waitingTimeLack.getMessage();
        }
        return tryToCloseSession(Session);
    }

    public static AbstractSession getSession(int SessionID) throws NoSessionFound, AlreadyFailedSession, AlreadyClosedSession {
        if(!Sessions.containsKey(SessionID)){
            throw new NoSessionFound(SessionID);
        }
        else {

            AbstractSession Session = Sessions.get(SessionID);
            switch (Session.getStatus()){
                case Failed: throw new AlreadyFailedSession(SessionID, Session.getRequirement().toString());
                case SuccessfullyEnded: throw new AlreadyClosedSession(SessionID, Session.getRequirement().toString());
                case Opened: return Session;
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

    public static void checkInMessageAndSession(AbstractInMessage in, AbstractSession session) throws DifferenceInSessionAndMesageRequirements {
        if(!in.getRequirement().equals(session.getRequirement().toString()))
            throw  new DifferenceInSessionAndMesageRequirements(session.getSession_id(),
                session.getRequirement().toString(), in.getRequirement());
    }

    public static void checkWaitingTimeExceeded(AbstractInMessage in, int secondsToWait, AbstractSession session) throws WaitingTimeExceeded {
        if(new Date().getTime() - new Date(in.getTime()).getTime()> secondsToWait)
            throw new WaitingTimeExceeded(session.getSession_id(), session.getRequirement().toString());
    }

    public static void checkWaitingTimeLack(Date lastMessageReceiver, int secondsToWait, AbstractSession session) throws WaitingTimeLack {
        if((new Date().getTime() - lastMessageReceiver.getTime())< secondsToWait)
            throw new WaitingTimeLack(session.getSession_id(), session.getRequirement().toString());
    }

    public static void checkMessageSequence(AbstractSession session,AbstractInMessage in) throws IncorrectMessageSequence {
       System.out.println(in.getOrder_number()-1+" "+(in.getOrder_number()+1));
        if ((in.getOrder_number_in_step()!=0)&&in.getOrder_number_in_step()!=in.getMsg_count()-1) {
            System.out.println("Not the first");
            if (session.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)
                    && !session.checkIfMessageAlreadyReceived(in.getOrder_number() + 1)) return;
        }
        else if(in.getMsg_count()==1) return;
        else if(in.getOrder_number_in_step()==0){
            System.out.println("first");
            if(!session.checkIfMessageAlreadyReceived(in.getOrder_number()+1))return;
        }
        else if(session.checkIfMessageAlreadyReceived(in.getOrder_number() - 1)) return;
        throw new IncorrectMessageSequence(session.getRequirement().toString());
    }

    public  static String tryToCloseSession(AbstractSession session){
        if(session.checkIfClose()){
            return "All messages received. Session "+ session.getSession_id()+" successfully closed. Requirement "+ session.getRequirement();
        }
        return "Message successfully processed";

    }

    public static void isRejectionRight(CircuitBreakerSession session, CircuitBreakerInMessage in) throws differenceInRejectionMapping {
        if(!session.checkRejection(in.getOrder_number()))
            throw new differenceInRejectionMapping(session.getSession_id(), in.getOrder_number() );

    }

}
