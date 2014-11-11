package com.u6f6o.apps.hazelnate.client.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LanguageModel {
    private final Long id;
    private final String acronym;
    private final String name;

    @JsonCreator
    public LanguageModel(@JsonProperty("id") Long id, @JsonProperty("acronym") String acronym,
                         @JsonProperty("name") String name) {
        this.id = id;
        this.acronym = acronym;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    //    private static LanguageModel valueOf(Language origin) {
//        LanguageModel result = new LanguageModel();
//        result.id = origin.getId();
//        result.acronym = origin.getAcronym();
//        result.name = origin.getName();
//        return result;
//    }
}
