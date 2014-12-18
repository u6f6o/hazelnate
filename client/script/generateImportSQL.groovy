import groovy.json.JsonSlurper

def inputFile;
def outputFile;

System.in.withReader {
    println "Countries file location: "
    inputFile = new File(it.readLine())
    println "Output file location: "
    outputFile = new File(it.readLine())
}

def countriesJSON = new JsonSlurper().parse(inputFile)
def langByAcronym = [:] as TreeMap
def cityIdSeq = 0;

countriesJSON.languages*.each { acronym, name ->
    langByAcronym.put(acronym, name)
}

outputFile << createWorldStmt() + "\n"

langByAcronym.eachWithIndex{ acronym, name, index ->
    outputFile << createLanguageStmt(index, acronym, name) + "\n"
}
countriesJSON.eachWithIndex { country, index ->
    outputFile << createCountryStmt(index, country.name.common) + "\n"

    country.languages.each { acronym, name ->
        outputFile << createCountryLanguageStmt(index, langByAcronym.findIndexOf { it.key ==  acronym }) + "\n"
    }
    if (country.capital?.trim()) {
        outputFile << createCityStmt(cityIdSeq++, country.capital.replace("'", "''"), index) + "\n"
    }
}

def createWorldStmt(){
    "INSERT INTO WORLD(WORLD_ID, NAME) VALUES (666, 'World');"
}

def createLanguageStmt(id, acronym, name){
    "INSERT INTO LANGUAGE(LANGUAGE_ID, ACRONYM, NAME) VALUES(${id}, '${acronym}', '${name}');"
}

def createCountryStmt(id, countryName) {
    "INSERT INTO COUNTRY(COUNTRY_ID, NAME, WORLD_ID) VALUES(${id}, '${countryName}', '666');"
}

def createCountryLanguageStmt(countryId, languageId) {
    "INSERT INTO COUNTRY_LANGUAGES(COUNTRY_ID, LANGUAGE_ID) VALUES(${countryId}, ${languageId});"
}

def createCityStmt(cityId, name, countryId) {
    "INSERT INTO CITY(CITY_ID, NAME, COUNTRY_ID) VALUES (${cityId}, '${name}', ${countryId});"
}