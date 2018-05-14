package stub.messages.outMessages;

import com.google.gson.Gson;

public class MessageProcResult {
    String result;

    public MessageProcResult(String result){
        this.result=result;
    }

    public String toJSON() {
        return new Gson().toJson(this);
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
