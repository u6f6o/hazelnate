package com.u6f6o.apps.hazelnate.client.rest.transformer;

import com.u6f6o.apps.hazelnate.client.domain.City;
import com.u6f6o.apps.hazelnate.client.domain.Country;
import com.u6f6o.apps.hazelnate.client.domain.Inhabitant;
import com.u6f6o.apps.hazelnate.client.domain.Language;
import com.u6f6o.apps.hazelnate.client.rest.model.CityModel;
import com.u6f6o.apps.hazelnate.client.rest.model.CountryModel;
import com.u6f6o.apps.hazelnate.client.rest.model.InhabitantModel;
import com.u6f6o.apps.hazelnate.client.rest.model.LanguageModel;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelTransformer {

    public Inhabitant toEntity(InhabitantModel inhabitantModel) {
        Inhabitant inhabitant = new Inhabitant();
        inhabitant.setId(inhabitantModel.getId());
        inhabitant.setName(inhabitantModel.getName());
        inhabitant.setAge(inhabitantModel.getAge());

        inhabitant.setHometown(toEntity(inhabitantModel.getHometown()));
        inhabitant.setHomeland(toEntity(inhabitantModel.getHomeland()));
        inhabitant.setLanguages(toLanguageEntities(inhabitantModel.getLanguages()));

        return inhabitant;
    }

    private Set<Language> toLanguageEntities(Set<LanguageModel> languageModels) {
        Set<LanguageModel> nullSafeLanguageModels = languageModels == null
                ? new HashSet<>()
                : languageModels;

        return nullSafeLanguageModels.stream().map(this::toEntity).collect(Collectors.toCollection(
                () -> new HashSet<>()));
    }

    private Country toEntity(CountryModel countryModel) {
        if (countryModel == null) {
            return null;
        }
        Country country = new Country();
        country.setId(countryModel.getId());
        country.setName(countryModel.getName());

        // TODO country.setLanguages(toLanguageEntities(countryModel.getLanguages()));
        // TODO country.setWorld();
        // TODO country.setCities();

        return country;
    }

    private City toEntity(CityModel cityModel) {
        if (cityModel == null) {
            return  null;
        }
        City city = new City();
        city.setId(cityModel.getId());
        city.setName(cityModel.getName());

        // TODO city.setCountry();
        // TODO city.setInhabitants();

        return city;
    }

    private Language toEntity(LanguageModel languageModel) {
        if (languageModel == null) {
            return null;
        }
        Language language = new Language();
        language.setId(languageModel.getId());
        language.setName(languageModel.getName());
        language.setAcronym(languageModel.getAcronym());

        return  language;
    }
}
