package stub.exceptions;

public class IncorrectMessageSequence extends Exception {
    public IncorrectMessageSequence (String rquirement){
        super("Incorrect message sequence during "+ rquirement+" requirement testing");

    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
