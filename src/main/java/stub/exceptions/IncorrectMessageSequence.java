package stub.exceptions;

public class IncorrectMessageSequence extends Exception {
    public IncorrectMessageSequence (String requirement){
        super("Incorrect message sequence during "+ requirement+" requirement testing");

    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
