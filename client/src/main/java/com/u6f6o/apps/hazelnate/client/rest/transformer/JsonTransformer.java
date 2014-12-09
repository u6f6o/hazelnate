package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

import java.io.IOException;

public class JsonTransformer implements ResponseTransformer {
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();

    @Override
    public String render(Object model) throws Exception {
        return JACKSON_MAPPER.writeValueAsString(model);
    }

    public <T> T parse(String content, Class<T> modelClazz) {
        try {
            return JACKSON_MAPPER.readValue(content, modelClazz);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse json content", e);
        }
    }
}
