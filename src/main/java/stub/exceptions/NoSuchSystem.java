package stub.exceptions;

public class NoSuchSystem extends Exception {
    public NoSuchSystem(String Name){
        super("No System with name "+ Name+" found");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
