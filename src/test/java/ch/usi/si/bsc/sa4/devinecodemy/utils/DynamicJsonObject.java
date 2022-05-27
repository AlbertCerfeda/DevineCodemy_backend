package ch.usi.si.bsc.sa4.devinecodemy.utils;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicJsonObject {
    Map<String, Object> fields = new LinkedHashMap<>();

    @JsonAnySetter
    void set(String key, Object value) {
        fields.put(key, value);
    }

    public Object get(String key) {
        return fields.get(key);
    }
}
