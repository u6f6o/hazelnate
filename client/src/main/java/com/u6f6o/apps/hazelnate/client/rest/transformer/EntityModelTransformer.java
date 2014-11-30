package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.u6f6o.apps.hazelnate.client.domain.*;
import com.u6f6o.apps.hazelnate.client.rest.model.*;

import java.util.*;
import java.util.stream.Collectors;

// TODO generics?
public class EntityModelTransformer {
    private static final Comparator<CityModel> CITY_BY_NAME = Comparator.comparing(CityModel::getName);
    private static final Comparator<CountryModel> COUNTRY_BY_NAME = Comparator.comparing(CountryModel::getName);

    public WorldModel transform(World origin) {
        return new WorldModel(origin.getId(), origin.getName(), transformCountries(origin.getCountries()));
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
        SortedSet<LanguageModel> languages = origin.getLanguages() != null
                ? origin.getLanguages().stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(LanguageModel::getAcronym))))
                : null;

        return new CountryModel(origin.getId(), origin.getName(), transformCities(origin.getCities()), languages);
    }

    public CityModel transform(City origin) {
        return new CityModel(origin.getId(), origin.getName());
    }

    public LanguageModel transform(Language origin) {
        return new LanguageModel(origin.getId(), origin.getAcronym(), origin.getName());
    }

    public SortedSet<CountryModel> transformCountries(Set<Country> countries) {
        Set<Country> nullSafeCountries = countries == null ? new HashSet<>() : countries;
        return nullSafeCountries.stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(COUNTRY_BY_NAME)));
    }

    public SortedSet<CityModel> transformCities(Set<City> cities) {
        Set<City> nullSafeCities = cities == null ? new HashSet<>() : cities;
        return nullSafeCities.stream()
                .map(this::transform)
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(CITY_BY_NAME)));
    }
}
