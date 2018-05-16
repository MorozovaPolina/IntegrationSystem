package stub.exceptions;

public class differenceInRejectionMapping extends Exception {
    public differenceInRejectionMapping(int session_id, int real_on){
        super("Difference in information in session "+session_id+" and message. According to session no message " +
                "with order number "+ real_on+"" + "  shall be rejected. Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
