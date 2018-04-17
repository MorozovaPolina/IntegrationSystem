package stub.exceptions;

public class AlreadyFailedTransaction extends Exception {
    public AlreadyFailedTransaction(int TransactionID){
        super("Transaction with ID "+ TransactionID+" already marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
