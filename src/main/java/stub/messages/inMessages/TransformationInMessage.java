package stub.messages.inMessages;

import stub.session.Step;

public class TransformationInMessage extends AbstractInMessage {
    Step step;
    String ESB;

    public String getESB() {
        return ESB;
    }

    public void setESB(String ESB) {
        this.ESB = ESB;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
