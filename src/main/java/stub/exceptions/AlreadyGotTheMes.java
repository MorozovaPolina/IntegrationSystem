package stub.exceptions;

public class AlreadyGotTheMes extends Exception {
    public AlreadyGotTheMes(int OrderNumber, int transaction, String requirement){
        super("The System already got the message with Order Number "+ OrderNumber+
                " in the transaction "+ transaction+"for "+requirement+" requirement testing");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
