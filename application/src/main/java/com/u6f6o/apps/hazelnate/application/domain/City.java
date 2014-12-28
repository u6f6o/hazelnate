package com.u6f6o.apps.hazelnate.application.domain;

import java.util.Set;

public class City {
    private Long id;
    private String name;
    private Country country;
    private Set<Inhabitant> inhabitants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Set<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }
}
