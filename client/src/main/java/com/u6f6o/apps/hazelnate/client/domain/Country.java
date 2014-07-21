package com.u6f6o.apps.hazelnate.client.domain;

import java.util.Set;

/**
 * Created by u6f6o on 10/10/14.
 */
public class Country {
    private String id;
    private String name;
    private World world;

    private Set<Language> languages;

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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }
}
