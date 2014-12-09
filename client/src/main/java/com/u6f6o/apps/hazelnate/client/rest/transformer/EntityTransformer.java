package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.u6f6o.apps.hazelnate.client.domain.*;
import com.u6f6o.apps.hazelnate.client.rest.model.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class EntityTransformer {
    private static final Comparator<LanguageModel> LANGUAGE_BY_NAME = Comparator.comparing(LanguageModel::getName);
    private static final Comparator<CountryModel> COUNTRY_BY_NAME = Comparator.comparing(CountryModel::getName);
    private static final Comparator<CityModel> CITY_BY_NAME = Comparator.comparing(CityModel::getName);

    public WorldModel toModel(World origin) {
        return new WorldModel(origin.getId(), origin.getName(), toCountryModels(origin.getCountries()));
    }

    public InhabitantModel toModel(Inhabitant inhabitant) {
        return new InhabitantModel(inhabitant.getId(), inhabitant.getName(), inhabitant.getAge(),
                toLanguageModels(inhabitant.getLanguages()), toModel(inhabitant.getHomeland()),
                toModel(inhabitant.getHometown()));
    }

    public CountryModel toModel(Country country) {
        return new CountryModel(country.getId(), country.getName(), toCityModels(country.getCities()),
                toLanguageModels(country.getLanguages()));
    }

    public CityModel toModel(City city) {
        return new CityModel(city.getId(), city.getName());
    }

    public LanguageModel toModel(Language language) {
        return new LanguageModel(language.getId(), language.getAcronym(), language.getName());
    }

    public IdentityModel toIdentityModel(Serializable serializable) {
        return new IdentityModel((Long) serializable);
    }

    // TODO generify?

    public SortedSet<CountryModel> toCountryModels(Set<Country> countries) {
        Set<Country> nullSafeCountries = countries == null
                ? new HashSet<>()
                : countries;

        return nullSafeCountries.stream().map(this::toModel).collect(Collectors.toCollection(
                () -> new TreeSet<>(COUNTRY_BY_NAME)));
    }

    public SortedSet<CityModel> toCityModels(Set<City> cities) {
        Set<City> nullSafeCities = cities == null
                ? new HashSet<>()
                : cities;

        return nullSafeCities.stream().map(this::toModel).collect(Collectors.toCollection(
                () -> new TreeSet<>(CITY_BY_NAME)));
    }

    public SortedSet<LanguageModel> toLanguageModels(Set<Language> languages) {
        Set<Language> nullSafeLanguages = languages == null
                ? new HashSet<>()
                : languages;

        return nullSafeLanguages.stream().map(this::toModel).collect(Collectors.toCollection(
                () -> new TreeSet<>(LANGUAGE_BY_NAME)));
    }
}
