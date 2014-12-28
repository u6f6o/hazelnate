package com.u6f6o.apps.hazelnate.application.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InhabitantModel {
    private final Long id;
    private final String name;
    private final Integer age;
    private final Set<LanguageModel> languages;
    private final CountryModel homeland;
    private final CityModel hometown;

    public InhabitantModel(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("age") Integer age,
            @JsonProperty("languages") Set<LanguageModel> languages,
            @JsonProperty("homeland") CountryModel homeland,
            @JsonProperty("hometown") CityModel hometown) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.languages = languages;
        this.homeland = homeland;
        this.hometown = hometown;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Set<LanguageModel> getLanguages() {
        return languages;
    }

    public CountryModel getHomeland() {
        return homeland;
    }

    public CityModel getHometown() {
        return hometown;
    }
}
