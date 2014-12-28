package com.u6f6o.apps.hazelnate.application.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.SortedSet;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CountryModel {
    private final Long id;
    private final String name;
    private final SortedSet<CityModel> cities;
    private final SortedSet<LanguageModel> languages;

    @JsonCreator
    public CountryModel(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                        @JsonProperty("cities") SortedSet<CityModel> cities,
                        @JsonProperty("languages") SortedSet<LanguageModel> languages) {
        this.id = id;
        this.name = name;
        this.cities = cities;
        this.languages = languages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SortedSet<LanguageModel> getLanguages() {
        return languages;
    }

    public SortedSet<CityModel> getCities() {
        return cities;
    }
}