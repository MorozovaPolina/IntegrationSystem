package stub.exceptions;

public class WaitingTimeLack extends Exception {
    public WaitingTimeLack(int session_id, String requirement){
        super("Not enough waiting Time for session "+ session_id+" for "+ requirement+" testing. Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
