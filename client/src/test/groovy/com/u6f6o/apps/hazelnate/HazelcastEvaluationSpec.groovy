package com.u6f6o.apps.hazelnate

import com.fasterxml.jackson.databind.ObjectMapper
import com.u6f6o.apps.hazelnate.client.domain.Country
import com.u6f6o.apps.hazelnate.client.domain.Language
import com.u6f6o.apps.hazelnate.client.domain.World
import com.u6f6o.apps.hazelnate.client.hibernate.HibernateUtil
import groovy.json.JsonSlurper
import org.hibernate.SessionFactory
import spock.lang.Shared
import spock.lang.Specification
/**
 * Created by u6f6o on 10/10/14.
 */
class HazelcastEvaluationSpec extends Specification {

    @Shared
    def JsonSlurper slurper;

    def setupSpec(){
        slurper = createCountriesSlurper();
    }


    def "check hibernate statistics on simple operations"(){
        setup:
            def sessionFactory = HibernateUtil.getSessionFactory()
            def statistics = sessionFactory.statistics
            def world = parseTestData()
        when: "create world with children"
            def worldId = withinSession(sessionFactory) { session ->
                session.save(world)
            }
        then: "expect db inserts and cache puts to be equal"
            statistics.secondLevelCachePutCount == statistics.entityInsertCount
            17 == statistics.secondLevelCachePutCount
            statistics.clear()
        when: "load world only (collections are lazilly mapped)"
            withinSession(sessionFactory) { session ->
                session.get(World.class, worldId)
            }
        then: "expect single 2nd level cache get operation"
            1 == statistics.secondLevelCacheHitCount
            0 == statistics.secondLevelCacheMissCount
            0 == statistics.entityFetchCount
            0 == statistics.entityLoadCount
            statistics.clear()
        when: "load world and countries"
            withinSession(sessionFactory) { session ->
                def worldFromDB = session.get(World.class, worldId)
                worldFromDB.getCountries().size()
            }
        then: "expect to load collection from DB first time it is accessed"
            1 == statistics.secondLevelCacheHitCount   // world entity
            1 == statistics.collectionLoadCount        // first time we load countries collection
            1 == statistics.secondLevelCacheMissCount  // no countries collection in cache
            3 == statistics.secondLevelCachePutCount   // 1 x countries collection, 2 x country entities in collection
            statistics.clear()
        when: "load world and access countries collection again"
            withinSession(sessionFactory) { session ->
                def worldFromDB = session.get(World.class, worldId)
                worldFromDB.getCountries().size()
            }
        then: "expect to load collection from 2nd level without DB hit"
            4 == statistics.secondLevelCacheHitCount   // world, countries collection and countries
            0 == statistics.collectionLoadCount
            0 == statistics.secondLevelCacheMissCount
            0 == statistics.secondLevelCachePutCount
            statistics.clear()
    }

    private createCountriesSlurper() {
        def resource = this.getClass().getResource("/test-samples/countries.json")
        new JsonSlurper().parse(resource);
    }

    private parseLanguages(){
        slurper.
        slurper.each {

        }
    }

    private parseTestData() {
        def slurper = createCountriesSlurper()

        def world = new World()
        world.countries = slurper.each {
            def country = new Country()
            country.name = it.name.common
            country.languages = it.languages.collect {
                new Language(acronym: it.key, name: it.value)
            }
        }
        return new ObjectMapper().readValue(resource, World.class)
    }

    private withinSession(SessionFactory sessionFactory, Closure closure) {
        def session = sessionFactory.openSession()
        session.beginTransaction()
        def result = closure(session)
        session.getTransaction().commit()
        session.close()
        result
    }
}

