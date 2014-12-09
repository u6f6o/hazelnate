import groovy.json.JsonSlurper

def countriesFile;

System.in.withReader {
    println "Countries file location: "
    countriesFile = new File(it.readLine())
}

def countriesJSON = new JsonSlurper().parse(countriesFile)
def langByAcronym = [:] as TreeMap

countriesJSON.languages*.each { acronym, name ->
    langByAcronym.put(acronym, name)
}

println "INSERT INTO WORLD(WORLD_ID, NAME) VALUES (666, 'World');"

countriesJSON.eachWithIndex { country, index ->
    println "INSERT INTO COUNTRY(COUNTRY_ID, NAME, WORLD_ID) VALUES(${index}, '${country.name.common}', '666');"
}

langByAcronym.eachWithIndex{ acronym, name, index ->
    println "INSERT INTO LANGUAGE(LANGUAGE_ID, ACRONYM, NAME) VALUES(${index}, '${acronym}', '${name}');"
}

countriesJSON.eachWithIndex { country, index ->
    country.languages.each { acronym, name ->
        def langId = langByAcronym.findIndexOf { it.key ==  acronym }
        println "INSERT INTO COUNTRY_LANGUAGES(COUNTRY_ID, LANGUAGE_ID) VALUES(${index}, ${langId});"
    }
}

countriesJSON.eachWithIndex { country, index ->
    if (!"".equals(country.capital)) {
        println "INSERT INTO CITY(NAME, COUNTRY_ID) VALUES ('${country.capital.replace("'", "''")}', ${index});"
    }
}