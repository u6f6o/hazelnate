package com.u6f6o.apps.hazelnate.load.scenario

import io.gatling.core.Predef._
import io.gatling.core.feeder.{RecordSeqFeederBuilder, Record}
import io.gatling.http.Predef._

import scala.concurrent.forkjoin.ThreadLocalRandom
import scala.io.{BufferedSource, Source}

class Insert100kInhabitants extends Simulation {
  val random = ThreadLocalRandom.current
  val footprints = loadCsvFromClassPath("/data/footprints.csv")
  val forenames = loadCsvFromClassPath("/data/forenames.csv")
  val surnames = loadCsvFromClassPath("/data/surnames.csv")

  val httpConf = http
    .baseURL("http://localhost:4567")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")

  val scn = scenario("Insert100kInhabitants").repeat(10000){
    exec{ session =>
      val footprintRecords = chooseRandomly(footprints, 5)
      val forenameRecord = chooseRandomly(forenames)
      val surnameRecord = chooseRandomly(surnames)

      session.setAll(
        "name" -> chooseFullName(forenameRecord, surnameRecord),
        "age" -> chooseAge(),
        "homeland" -> chooseHomeland(footprintRecords),
        "hometown" -> chooseHometown(footprintRecords),
        "languages" -> chooseLanguages(footprintRecords)
      )
    }
    .exec(http("insert100kInhabitants")
      .post("/world/inhabitants")
      .body(StringBody(
        """{
          "name": ${name},
          "age": ${age},
          "homeland": ${homeland},
          "hometown": ${hometown},
          "languages": ${languages}
          }"""
      )).asJSON
    )
  }

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)

  def loadCsvFromClassPath(path:String) : IndexedSeq[Record[String]] = {
    csv(getClass.getResource(path).getFile).records
  }

  def chooseRandomly(pool:IndexedSeq[Record[String]]) : Record[String] = {
    pool(random.nextInt(pool.length))
  }

  def chooseRandomly(pool:IndexedSeq[Record[String]], maxLength:Int) : IndexedSeq[Record[String]] = {
    for (i <- 1 to random.nextInt(1, maxLength)) yield pool(random.nextInt(pool.length))
  }

  def chooseFullName(forenameRec:Record[String], surnameRec:Record[String]) : String = {
    "\"" + forenameRec.getOrElse("forename", "") + " " + surnameRec.getOrElse("surname", "") + "\""
  }

  def chooseAge() : String = {
    "\"" + random.nextInt(1, 110) + "\""
  }

  def chooseHomeland(footprints:IndexedSeq[Record[String]]) : String = {
    """{ "id": """" + { footprints.head.getOrElse("country", "")} + """"}"""
  }

  def chooseHometown(footprints:IndexedSeq[Record[String]]) : String = {
    """{ "id": """" + { footprints.head.getOrElse("city", "")} + """"}"""
  }

  def chooseLanguages(footprints:IndexedSeq[Record[String]]) : String = {
    "[" + footprints.map(x => "{\"id\": \"" + x.getOrElse("language", "") + "\"}").mkString(",") + "]"
  }
}