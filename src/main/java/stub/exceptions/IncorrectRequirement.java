package stub.exceptions;

public class IncorrectRequirement extends Exception{
    public IncorrectRequirement(String Requirement){

        super("No Requirement "+ Requirement+" found");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
