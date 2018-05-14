package stub.exceptions;

public class AlreadyFailedSession extends Exception {
    public AlreadyFailedSession(int session_id, String requirement){
        super("Session with ID "+ session_id+" ("+requirement+ " testing) already marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
