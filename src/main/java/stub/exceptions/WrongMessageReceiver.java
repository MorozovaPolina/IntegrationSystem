package stub.exceptions;

public class WrongMessageReceiver extends Exception{
    public WrongMessageReceiver (){
        super("Неправильный получатель сообщения ");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
