package stub.messages;

import stub.transaction.Transaction;

public class DemoInObject {
    String transaction_id;
    String requirement;
    String time;
    String source;
    String target;
    String order_number;
    String msg_count;

    public String toJSon() {
        return "{ \"transaction_id\" : \"" + transaction_id + "\", \"requirement\" : \"" + requirement + "\", \"time\" : \"" + time + "\", " +
                "\"source\" : \"" + source + "\", \"target\" : \"" + target + "\", \"order_number\" : \"" + order_number + "\", \"msg_count\": \"" + msg_count + "\" }";
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setMsg_count(String msg_count) {
        this.msg_count = msg_count;
    }

    public String getMsg_count() {
        return msg_count;
    }
}


