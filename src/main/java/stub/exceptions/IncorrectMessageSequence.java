package stub.exceptions;

public class IncorrectMessageSequence extends Exception {
    public IncorrectMessageSequence (String requirement){
        super("Incorrect message sequence during "+ requirement+" requirement testing. Session will be marked as failed");

    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
