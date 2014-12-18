import groovy.sql.Sql

def outputFile

System.in.withReader {
    println "Database should run! "
    println "Output file location: "
    outputFile = new File(it.readLine())
}

outputFile << "COUNTRY_ID;CITY_ID;LANGUAGE_ID\n"

sql = Sql.newInstance( 'jdbc:h2:tcp://localhost:9501/~/world', 'sa', '', 'org.h2.Driver' )
sql.eachRow(
        'select\n' +
            'country.country_id,\n' +
            'city.city_id,\n' +
            'language.language_id\n' +
        'from\n' +
            'country,\n' +
            'city,\n' +
            'language,\n' +
            'country_languages\n' +
        'where\n' +
            'country.country_id=country_languages.country_id and\n' +
            'language.language_id=country_languages.language_id and\n' +
            'country.country_id=city.country_id;'
) {
    outputFile << "${it.country_id};${it.city_id};${it.language_id}\n/va"
}
