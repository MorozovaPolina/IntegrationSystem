package stub.messages;

import stub.transaction.Step;

public class UserRequest {
    String api;
    Step[] scenario;

    public Step[] getScenario() {
        return scenario;
    }

    public void setScenario(Step[] scenario) {
        this.scenario = scenario;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
