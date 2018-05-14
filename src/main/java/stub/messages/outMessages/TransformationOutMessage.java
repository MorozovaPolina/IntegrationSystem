package stub.messages.outMessages;

public class TransformationOutMessage extends AbstractOutMessage {

    public TransformationOutMessage(int session_id, String requirement, String time, String source, String target, int order_number, int order_number_in_step) {
        super(session_id, requirement, time, source, target, order_number, order_number_in_step);
    }
}
