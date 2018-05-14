package stub.exceptions;

public class AlreadyGotTheMes extends Exception {
    public AlreadyGotTheMes(int OrderNumber, int session_id, String requirement){
        super("The System already got the message with Order Number "+ OrderNumber+
                " in the session "+ session_id+" for "+requirement+" requirement testing. Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
