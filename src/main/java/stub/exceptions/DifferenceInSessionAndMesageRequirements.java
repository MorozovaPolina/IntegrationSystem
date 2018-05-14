package stub.exceptions;

public class DifferenceInSessionAndMesageRequirements extends Exception {
    public DifferenceInSessionAndMesageRequirements (int  session_id, String TrReq, String MesReq){
        super("Found difference during session" +session_id+" as "+ TrReq+" does not match "+ MesReq );
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
