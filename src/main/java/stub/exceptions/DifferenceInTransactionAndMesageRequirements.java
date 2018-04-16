package stub.exceptions;

public class DifferenceInTransactionAndMesageRequirements extends Exception {
    public DifferenceInTransactionAndMesageRequirements (int  Transaction, String TrReq, String MesReq){
        super("Found difference during transaction" +Transaction+" as "+ TrReq+" does not match "+ MesReq );
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
