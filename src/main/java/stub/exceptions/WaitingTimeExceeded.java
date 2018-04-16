package stub.exceptions;

public class WaitingTimeExceeded extends Exception {
    public WaitingTimeExceeded(){
        super("Время ожидания сообщения превышено");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
