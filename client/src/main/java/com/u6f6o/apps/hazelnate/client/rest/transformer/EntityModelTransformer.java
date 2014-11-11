package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.u6f6o.apps.hazelnate.client.domain.*;
import com.u6f6o.apps.hazelnate.client.rest.model.*;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EntityModelTransformer {

    public WorldModel transform(World origin) {
        SortedSet<CountryModel> countries = origin.getCountries() != null
                ? origin.getCountries().stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(CountryModel::getName))))
                : null;

        return new WorldModel(origin.getId(), origin.getName(), countries);
    }

    public InhabitantModel transform(Inhabitant origin) {
        SortedSet<LanguageModel> languages = origin.getLanguages() != null
                ? origin.getLanguages().stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(LanguageModel::getAcronym))))
                : null;
        CountryModel homeland = transform(origin.getHomeland());
        CityModel hometown = transform(origin.getHometown());

        return new InhabitantModel(origin.getId(), origin.getName(), origin.getAge(),
                languages, homeland, hometown);
    }

    public CountryModel transform(Country origin) {
        SortedSet<CityModel> cities = origin.getCities() != null
                ? origin.getCities().stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(CityModel::getName))))
                : null;

        SortedSet<LanguageModel> languages = origin.getLanguages() != null
                ? origin.getLanguages().stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(LanguageModel::getAcronym))))
                : null;

        return new CountryModel(origin.getId(), origin.getName(), cities, languages);
    }

    public CityModel transform(City origin) {
        return new CityModel(origin.getId(), origin.getName());
    }

    public LanguageModel transform(Language origin) {
        return new LanguageModel(origin.getId(), origin.getAcronym(), origin.getName());
    }
}
