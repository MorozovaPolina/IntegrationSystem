package stub.messages.request;

public class UserQueueRequest extends AbstractUserRequest {
 public int seconds_to_wait;

    public int getSeconds_to_wait() {
        return seconds_to_wait;
    }

    public void setSeconds_to_wait(int seconds_to_wait) {
        this.seconds_to_wait = seconds_to_wait;
    }
}
