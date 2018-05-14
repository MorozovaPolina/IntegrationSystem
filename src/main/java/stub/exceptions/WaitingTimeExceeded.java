package stub.exceptions;

public class WaitingTimeExceeded extends Exception {
    public WaitingTimeExceeded(int session_id, String requirement){
        super("Waiting Time for session "+ session_id+" for "+ requirement+" testing exceeded");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
