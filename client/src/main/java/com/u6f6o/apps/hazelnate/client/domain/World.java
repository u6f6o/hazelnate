package com.u6f6o.apps.hazelnate.client.domain;

import java.util.Set;

/**
 * Created by u6f6o on 10/10/14.
 */
public class World {
    private String id;
    private String name;
    private Set<Country> countries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
