package stub.messages;

import stub.transaction.Step;

public class UserRequest {
    String api;
    RequestStep[] scenario;

    public RequestStep[] getScenario() {
        return scenario;
    }

    public void setScenario(RequestStep[] scenario) {
        this.scenario = scenario;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
