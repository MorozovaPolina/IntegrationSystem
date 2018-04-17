package stub.transaction;

import stub.systems.ExistingSystem;


public class Step {
    public ExistingSystem Source;
    public ExistingSystem Target;
    public int NumberOfMessagesToSend;

    public Step(ExistingSystem Source, ExistingSystem Target, int NumberOfMessageToSend){
        this.Source=Source;
        this.Target=Target;
        this.NumberOfMessagesToSend=NumberOfMessageToSend;
    }
}
