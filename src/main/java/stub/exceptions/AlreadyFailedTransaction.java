package stub.exceptions;

public class AlreadyFailedTransaction extends Exception {
    public AlreadyFailedTransaction(int TransactionID, String requirement){
        super("Transaction with ID "+ TransactionID+" ("+requirement+ " testing) already marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
