package stub.exceptions;

public class WrongMessageReceiver extends Exception{
    public WrongMessageReceiver (String Waited, String real){
        super("Wrong message receiver. Waiting for "+Waited+", actual "+ real);
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
