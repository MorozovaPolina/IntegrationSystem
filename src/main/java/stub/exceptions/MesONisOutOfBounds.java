package stub.exceptions;

public class MesONisOutOfBounds extends Exception{
    public MesONisOutOfBounds(int OrderNumber, int Transaction, String requirement)
    {
        super("Message Order Number " + OrderNumber+" is out of Bounds. Transaction "+ Transaction+" ("+requirement+ " testing)");
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
