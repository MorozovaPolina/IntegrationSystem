package stub.exceptions;

public class differenceInrejectionMapping extends Exception {
    public differenceInrejectionMapping(int transaction, int trans_on, int real_on){
        super("Difference in information in transaction "+transaction+" and message. According to transaction "+ trans_on+ " " +
                "shall be rejected. But according to message "+ real_on+"" + " message shall be rejected");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
