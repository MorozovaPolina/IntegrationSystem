package stub.messages.inMessages;

import com.google.gson.Gson;

public abstract class AbstractInMessage {
    int session_id;
    String requirement;
    String time;
    int order_number;
    int msg_count;
    int order_number_in_step;
    boolean isInternal;


    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getSession_id() {
        return session_id;
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

    public boolean getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(boolean internal) {
        isInternal = internal;
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

    public int getOrder_number_in_step() {
        return order_number_in_step;
    }

    public void setOrder_number_in_step(int order_number_in_step) {
        this.order_number_in_step = order_number_in_step;
    }
}
