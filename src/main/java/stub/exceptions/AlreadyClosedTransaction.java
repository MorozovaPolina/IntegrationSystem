package stub.exceptions;

public class AlreadyClosedTransaction extends Exception {
    public AlreadyClosedTransaction(int transaction_id, String requirement){
        super("Transaction with the ID "+transaction_id+" ("+requirement+ " testing) was already closed. Transaction will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
