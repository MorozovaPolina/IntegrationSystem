package stub.session;

public class toReject {
    int rejectionMessage;
    int numberOfRejections;
    int alreadyrejected=0;

    public void reject(){
        alreadyrejected++;
    }

    public boolean rejectionRequired(){
        return !(numberOfRejections==alreadyrejected);
    }

    public int getAlreadyrejected() {
        return alreadyrejected;
    }

    public void setAlreadyrejected(int alreadyrejected) {
        this.alreadyrejected = alreadyrejected;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public int getRejectionMessage() {
        return rejectionMessage;
    }

    public void setRejectionMessage(int rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }
}
