package stub.messages.request;

import stub.session.Step;

public abstract class AbstractUserRequest {
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
