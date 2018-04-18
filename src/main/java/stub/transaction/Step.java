package stub.transaction;


public class Step {
    public String Source;
    public String Target;
    public int NumberOfMessagesToSend;

    public Step(String Source, String Target, int NumberOfMessageToSend){
        this.Source=Source;
        this.Target=Target;
        this.NumberOfMessagesToSend=NumberOfMessageToSend;
    }
}
