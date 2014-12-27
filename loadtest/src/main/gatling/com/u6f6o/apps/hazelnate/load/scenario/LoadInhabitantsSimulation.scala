package com.u6f6o.apps.hazelnate.load.scenario

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

class LoadInhabitantsSimulation extends Simulation {
  val inhabitantIdsFeeder = jdbcFeeder("jdbc:h2:tcp://localhost:9501/~/world", "sa", "",
    "SELECT INHABITANT_ID FROM INHABITANT LIMIT 20000").random

  val httpConf = http
    .baseURL("http://localhost:4567")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val scn = scenario("loadInhabitants").during(5 minutes){
    exec(http("loadInhabitants")
      .get("/world/inhabitants/${INHABITANT_ID}")
    ).feed(inhabitantIdsFeeder)
  }

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)

}