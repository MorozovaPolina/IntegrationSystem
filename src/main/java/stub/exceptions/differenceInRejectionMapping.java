package stub.exceptions;

public class differenceInRejectionMapping extends Exception {
    public differenceInRejectionMapping(int session_id, int trans_on, int real_on){
        super("Difference in information in session "+session_id+" and message. According to session "+ trans_on+ " " +
                "shall be rejected. But according to message "+ real_on+"" + " message shall be rejected. Session will be marked as failed");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
