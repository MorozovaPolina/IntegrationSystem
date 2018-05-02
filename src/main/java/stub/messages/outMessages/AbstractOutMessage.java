package stub.messages.outMessages;


public abstract class AbstractOutMessage {
    int transaction_id;
    String requirement;
    String time;
    String source;
    String target;
    int order_number;
    int msg_count;
    int order_number_in_step;
    boolean isInternal;

    public AbstractOutMessage(int request_id, String transaction_type, String time, String source, String target,
                              int order_number, int order_number_in_step) {
        this.transaction_id= request_id;
        this.requirement = transaction_type;
        this.time = time;
        this.source = source;
        this.target = target;
        this.order_number = order_number;
        this.order_number_in_step=order_number_in_step;
        isInternal=true;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getTransaction_id() {
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

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setMsg_count(int msg_count) {
        this.msg_count = msg_count;
    }

    public int getMsg_count() {
        return msg_count;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean internal) {
        isInternal = internal;
    }

    public int getOrder_number_in_step() {
        return order_number_in_step;
    }

    public void setOrder_number_in_step(int order_number_in_step) {
        this.order_number_in_step = order_number_in_step;
    }


}