package stub.exceptions;

public class MesONisOutOfBounds extends Exception{
    public MesONisOutOfBounds(int OrderNumber, int session_id, String requirement)
    {
        super("Message Order Number " + OrderNumber+" is out of Bounds. Session "+ session_id+" ("+requirement+ " testing). Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
