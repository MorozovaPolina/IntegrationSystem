package stub.exceptions;

public class NoTransactionFound extends Exception {
    public NoTransactionFound(int TransactionID){
        super("No transaction with ID "+ TransactionID+" found");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
