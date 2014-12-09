package com.u6f6o.apps.hazelnate.client.service;

import com.u6f6o.apps.hazelnate.client.domain.*;
import com.u6f6o.apps.hazelnate.client.hibernate.HibernateUtil;
import com.u6f6o.apps.hazelnate.client.rest.model.*;
import com.u6f6o.apps.hazelnate.client.rest.transformer.EntityTransformer;
import com.u6f6o.apps.hazelnate.client.rest.transformer.ModelTransformer;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: find a name that doesn't remind you on Spring ;-)
public class WorldService {
    private static final EntityTransformer ENTITY_TRANSFORMER = new EntityTransformer();
    private static final ModelTransformer MODEL_TRANSFORMER = new ModelTransformer();

    public WorldModel fetchWorld() {
        return new TxContext<World, WorldModel>().withTx(
                session -> get(session, World.class, 666l), ENTITY_TRANSFORMER::toModel);
    }

    public CountryModel fetchCountry(Long id) {
        return new TxContext<Country, CountryModel>().withTx(
                session -> get(session, Country.class, id), ENTITY_TRANSFORMER::toModel);
    }

    public CityModel fetchCity(Long id) {
        return new TxContext<City, CityModel>().withTx(
                session -> get(session, City.class, id), ENTITY_TRANSFORMER::toModel);
    }

    public SortedSet<CountryModel> fetchAllCountries() {
        return new TxContext<Set<Country>, SortedSet<CountryModel>>().withTx(
                session -> {
                    World world = get(session, World.class, 666l);
                    return world.getCountries();
                }, ENTITY_TRANSFORMER::toCountryModels);
    }

    public SortedSet<CityModel> fetchAllCities() {
        return new TxContext<Set<City>, SortedSet<CityModel>>().withTx(
                session -> {
                    Set<City> cities = new HashSet<>();
                    World world = get(session, World.class, 666l);
                    for (Country country : world.getCountries()) {
                        cities.addAll(country.getCities());
                    }
                    return cities;
                }, ENTITY_TRANSFORMER::toCityModels);
    }

    public IdentityModel createInhabitant(InhabitantModel inhabitantModel) {
        Inhabitant inhabitant = MODEL_TRANSFORMER.toEntity(inhabitantModel);
        return new TxContext<Serializable, IdentityModel>().withTx(
                session -> {
                    City hometown = get(session, City.class, inhabitant.getHometown().getId());
                    Country homeland = get(session, Country.class, inhabitant.getHomeland().getId());
                    Set<Language> languages = inhabitant.getLanguages().stream().map(
                            language -> get(session, Language.class, language.getId()))
                            .collect(Collectors.toSet());

                    inhabitant.setLanguages(languages);
                    inhabitant.setHometown(hometown);
                    inhabitant.setHomeland(homeland);

                    return session.save(inhabitant);
                },
                ENTITY_TRANSFORMER::toIdentityModel);
    }

    private static <T> T get(Session session, Class<T> clazz, Serializable id) {
        return (T) session.get(clazz, id);
    }

    private static class TxContext<T, R> {
        R withTx(Function<Session, T> dbOperation, Function<T, R> processOperation) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            R result = null;
            try {
                transaction = session.beginTransaction();
                result = processOperation.apply(dbOperation.apply(session));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                session.close();
            }
            return result;
        }
    }
}

