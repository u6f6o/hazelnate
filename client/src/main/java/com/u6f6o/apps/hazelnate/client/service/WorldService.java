package com.u6f6o.apps.hazelnate.client.service;

import com.u6f6o.apps.hazelnate.client.domain.City;
import com.u6f6o.apps.hazelnate.client.domain.Country;
import com.u6f6o.apps.hazelnate.client.domain.World;
import com.u6f6o.apps.hazelnate.client.hibernate.HibernateUtil;
import com.u6f6o.apps.hazelnate.client.rest.model.CityModel;
import com.u6f6o.apps.hazelnate.client.rest.model.CountryModel;
import com.u6f6o.apps.hazelnate.client.rest.model.WorldModel;
import com.u6f6o.apps.hazelnate.client.rest.transformer.EntityModelTransformer;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.util.function.Function;

// TODO: find a name that doesn't remind you on Spring ;-)
public class WorldService {
    private static final EntityModelTransformer MODEL_TRANSFORMER = new EntityModelTransformer();

    public WorldModel fetchWorld() {
        return new TxContext<World, WorldModel>()
                .withTx(session -> (World) session.get(World.class, 666l),
                        MODEL_TRANSFORMER::transform);
    }

    public CountryModel fetchCountry(Long id) {
        return new TxContext<Country, CountryModel>()
                .withTx(session -> (Country) session.get(Country.class, id),
                        MODEL_TRANSFORMER::transform);
    }

    public CityModel fetchCity(Long id) {
        return new TxContext<City, CityModel>()
                .withTx(session -> (City) session.get(City.class, id),
                        MODEL_TRANSFORMER::transform);
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

