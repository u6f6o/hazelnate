package com.u6f6o.apps.hazelnate.load.scenario

import io.gatling.core.Predef._
import io.gatling.core.feeder.Record
import io.gatling.http.Predef._

import scala.concurrent.forkjoin.ThreadLocalRandom

class Insert100kInhabitantsSimulation extends Simulation {
  val random = ThreadLocalRandom.current
  val footprints = csv("data/footprints.csv").records
  val forenames = csv("data/forenames.csv").records
  val surnames = csv("data/surnames.csv").records

  val httpConf = http
    .baseURL("http://localhost:4567")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val scn = scenario("Insert100kInhabitants").repeat(10000){
    exec{ session =>
      val footprintRecords = chooseRandomly(footprints, 5)
      session.setAll(
        "forename" -> chooseRandomly(forenames).getOrElse("forename", ""),
        "surname" -> chooseRandomly(surnames).getOrElse("surname", ""),
        "age" -> random.nextInt(1, 110),
        "hometown" -> footprintRecords.head.getOrElse("city", ""),
        "homeland" -> footprintRecords.head.getOrElse("country", ""),
        "languages" -> footprintRecords.map{ x => x.getOrElse("language", "")}
      )
    }
    .exec(http("insert100kInhabitants")
      .post("/world/inhabitants")
      .body(StringBody( session => generateJson(session))).asJSON
    )
  }

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)

  def generateJson(session:Session) : String = {
    s"""{
      |   "name": "${session("forename").as[String]} ${session("surname").as[String]}",
      |   "age": "${session("age").as[String]}",
      |   "hometown": { "id": "${session("hometown").as[String]}" },
      |   "homeland": { "id": "${session("homeland").as[String]}" },
      |   "languages": [
      |     ${session("languages").as[Seq[String]].map{ lang => s"""{ "id": "${lang}" }"""}.mkString(", ")}
      |   ]
      |}""".stripMargin
  }

  def chooseRandomly(pool:IndexedSeq[Record[String]]) : Record[String] = {
    pool(random.nextInt(pool.length))
  }

  def chooseRandomly(pool:IndexedSeq[Record[String]], maxLength:Int) : IndexedSeq[Record[String]] = {
    for (i <- 1 to random.nextInt(1, maxLength)) yield pool(random.nextInt(pool.length))
  }
}