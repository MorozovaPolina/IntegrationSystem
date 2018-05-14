package stub.exceptions;

public class AlreadyClosedSession extends Exception {
    public AlreadyClosedSession(int session_id, String requirement){
        super("Session with the ID "+session_id+" ("+requirement+ " testing) was already closed. Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
