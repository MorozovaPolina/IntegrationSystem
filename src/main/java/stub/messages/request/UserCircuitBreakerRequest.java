package stub.messages.request;

public class UserCircuitBreakerRequest extends AbstractUserRequest {
    int on_of_message_to_be_rejected;
    int rejection_quantity;


    public int getOn_of_message_to_be_rejected() {
        return on_of_message_to_be_rejected;
    }

    public void setOn_of_message_to_be_rejected(int on_of_message_to_be_rejected) {
        this.on_of_message_to_be_rejected = on_of_message_to_be_rejected;
    }

    public int getRejection_quantity() {
        return rejection_quantity;
    }

    public void setRejection_quantity(int rejection_quantity) {
        this.rejection_quantity = rejection_quantity;
    }
}
