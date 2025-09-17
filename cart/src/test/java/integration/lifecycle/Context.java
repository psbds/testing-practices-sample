package integration.lifecycle;

import java.util.HashMap;
import java.util.Map;

import io.quarkiverse.cucumber.ScenarioScope;
import lombok.Getter;
import lombok.Setter;

@ScenarioScope
public class Context {

    @Getter
    @Setter
    private String currentUserId;

    private Map<String, Object> data = new HashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key));
    }

    public void clear() {
        currentUserId = null;
        data.clear();
    }
}
