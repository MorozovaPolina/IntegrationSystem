package stub.exceptions;

public class WrongMessageReceiver extends Exception{
    public WrongMessageReceiver (String Waited, String real, String requirement){
        super("Wrong message receiver while "+requirement+" testing. Waiting for "+Waited+", actual "+ real);
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
