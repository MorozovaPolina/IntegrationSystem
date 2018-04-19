package stub.messages;

public class DemoInObject extends AbstractInMessage {

    public String toJSon() {
        return "{ \"transaction_id\" : " + transaction_id + ", \"requirement\" : \"" + requirement + "\", \"time\" : \"" + time + "\", " +
                "\"source\" : \"" + source + "\", \"target\" : \"" + target + "\", \"order_number\" : " + order_number + ", \"msg_count\": " + msg_count + ", \"order_number_in_step\" : "+order_number_in_step+" }";
    }

}


