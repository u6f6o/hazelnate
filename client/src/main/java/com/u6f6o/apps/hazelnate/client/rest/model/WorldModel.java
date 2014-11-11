package com.u6f6o.apps.hazelnate.client.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WorldModel implements Serializable {
    private final Long id;
    private final String name;
    private final SortedSet<CountryModel> countries;

    @JsonCreator
    public WorldModel(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                      @JsonProperty("countries") SortedSet<CountryModel> countries) {
        this.id = id;
        this.name = name;
        this.countries = countries;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<CountryModel> getCountries() {
        return countries;
    }
}
