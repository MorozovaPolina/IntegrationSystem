package stub.exceptions;

public class MesONisOutOfBounds extends Exception{
    public MesONisOutOfBounds(int OrderNumber, int Transaction)
    {
        super("Message Order Number " + OrderNumber+" is out of Bounds. Transaction "+ Transaction);
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
