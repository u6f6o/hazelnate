package com.u6f6o.apps.hazelnate.load.scenario

import io.gatling.core.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadCountriesSimulation extends Simulation {
  val countryIdsFeeder = jdbcFeeder("jdbc:h2:tcp://localhost:9501/~/world", "sa", "",
    "SELECT COUNTRY_ID FROM COUNTRY").random

  val httpConf = http
    .baseURL("http://localhost:4567")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val scn = scenario("loadCountries").during(5 minutes){
    exec(http("loadCountries")
      .get("/world/countries/${COUNTRY_ID}")
    ).feed(countryIdsFeeder)
  }

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)

}
