package com.u6f6o.apps.hazelnate.client.domain;

public class Language {

    private Long id;
    private String acronym;
    private String name;

    public Language() {
    }

    public Language(Long id, String acronym, String name) {
        this.id = id;
        this.acronym = acronym;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
