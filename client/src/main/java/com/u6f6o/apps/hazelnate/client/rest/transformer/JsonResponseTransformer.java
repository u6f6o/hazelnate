package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();

    @Override
    public String render(Object model) throws Exception {
        return JACKSON_MAPPER.writeValueAsString(model);
    }
}
