package stub.messages.request;

import stub.session.toReject;

public class UserCircuitBreakerRequest extends AbstractUserRequest {
    toReject[] rejection;
    int secondsAfterRejection;

    public toReject[] getRejection() {
        return rejection;
    }

    public void setRejection(toReject[] rejection) {
        this.rejection = rejection;
    }

    public int getSecondsAfterRejection() {
        return secondsAfterRejection;
    }

    public void setSecondsAfterRejection(int secondsAfterRejection) {
        this.secondsAfterRejection = secondsAfterRejection;
    }
}
