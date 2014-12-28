package com.u6f6o.apps.hazelnate.application.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HibernateUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            logHibernateProperties(configuration);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            LOG.error("Cannot initialize SessionFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void logHibernateProperties(Configuration configuration) {
        LOG.info(">>>>>>>>>>>> hibernate properties <<<<<<<<<<<<");

        for (Map.Entry<Object, Object> hibernateProperties : configuration.getProperties().entrySet()) {
            String propertyKey = (String) hibernateProperties.getKey();
            if (propertyKey.startsWith("hibernate")) {
                LOG.info(propertyKey + ": " + hibernateProperties.getValue());
            }
        }
        LOG.info(">>>>>>>>>>>> hibernate properties <<<<<<<<<<<<");
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }


    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
    }
}
