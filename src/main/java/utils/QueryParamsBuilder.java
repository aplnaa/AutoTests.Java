package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class QueryParamsBuilder {
    private Map<String, List<Object>> params;
    public QueryParamsBuilder() {
        this.params = new HashMap<>();
    }

    public QueryParamsBuilder param(String key, Object value) {
        if (!params.containsKey(key)) {
            params.put(key, new ArrayList<>());
        }
        params.get(key).add(value);
        return this;
    }

    public Map<String, List<Object>> build() {
        return params;
    }
}
