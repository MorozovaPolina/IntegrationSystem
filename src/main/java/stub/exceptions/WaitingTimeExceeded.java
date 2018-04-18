package stub.exceptions;

public class WaitingTimeExceeded extends Exception {
    public WaitingTimeExceeded(int transaction){
        super("Waiting Time for transaction "+ transaction+" exceeded");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
