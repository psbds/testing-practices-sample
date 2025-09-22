package integration.lifecycle;

import java.util.HashMap;
import java.util.Map;

import io.quarkiverse.cucumber.ScenarioScope;
import lombok.Getter;
import lombok.Setter;
import io.restassured.response.Response;

@ScenarioScope
public class Context {

    @Getter
    @Setter
    private String currentUserId;

    @Getter
    @Setter
    private Response lastResponse;

    @Getter
    @Setter
    private boolean unauthenticated = false;


    private Map<String, Object> data = new HashMap<>();

    public void clear() {
        currentUserId = null;
        unauthenticated = false;
        data.clear();
    }
}
