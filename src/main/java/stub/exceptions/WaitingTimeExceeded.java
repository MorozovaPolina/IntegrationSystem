package stub.exceptions;

public class WaitingTimeExceeded extends Exception {
    public WaitingTimeExceeded(int transaction, String requirement){
        super("Waiting Time for transaction "+ transaction+" for "+ requirement+" testing exceeded");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
