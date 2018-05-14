package stub.exceptions;

public class NoSessionFound extends Exception {
    public NoSessionFound(int session_id){
        super("No session with ID "+ session_id+" found");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
