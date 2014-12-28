package com.u6f6o.apps.hazelnate.application.domain;

import java.util.Set;

public class Inhabitant {
    private Long id;
    private String name;
    private Integer age;
    private Set<Language> languages;
    private Country homeland;
    private City hometown;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Country getHomeland() {
        return homeland;
    }

    public void setHomeland(Country homeland) {
        this.homeland = homeland;
    }

    public City getHometown() {
        return hometown;
    }

    public void setHometown(City hometown) {
        this.hometown = hometown;
    }
}
