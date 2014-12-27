## hazelnate

I initially created this project to get a better understanding of the hazelcast in-memory data grid. As we started to setup hazelcast on one of my clients main products, I also used it to clarify certain miss-understandings and to reproduce glitches we approached during the setup phase. 

### The project currently has 5 modules
* **client**: contains the main "business" logic
* **database**: wrapper to start up a h2 database 
* **server**: represents a single hazelcast server node
* **mancenter**: wrapper to start up the hazelcast mancenter    
* **loadtest**: some basic loadtests using gatling framework

This project mainly focuses on the distributed caching abilities hazelcast offers in conjunction with hibernate. Other powerful features like in-memory computing etc. are (currently) not covered. 

### Quick start
To start the application, simply clone the repo and execute the following commands one after another. The whole application is build using gradle wrapper. 

```zsh
git clone https://github.com/u6f6o/hazelnate.git 
cd hazelnate
# start the database on a new console 
database/gradlew -b database/build.gradle startDatabase
# start the mancenter on a new console
mancenter/gradlew -b mancenter/build.gradle startMancenter
# start the hazelcast server on a new console
server/gradlew -b server/build.gradle startServer
# start the client on a new console
client/gradlew -b client/build.gradle startClient

```

#### Access REST api 
The client basically offers a REST api which represents the world. You can fetch different things (countries, cities, languages etc.) or add new inhabitants to the world. For more information about the REST api, have a look at the WorldsEnd class. 

```zsh
# load whole world 
curl http://localhost:4567/world
# load specific country 
curl http://localhost:4567/world/countries/66
# add inhabitant
curl -H "Content-Type: application/json" -d '{ "name": "Paolo Pinkel", "age": "34", "hometown": { "id": "13" }, "homeland": { "id": "14" }, "languages": [ { "id": "20" }]}' http://localhost:4567/world/inhabitants
```

#### Gatling simulations
```zsh
# insert 100k inhabitants
loadtest/gradlew -b loadtest/build.gradle insert100kInhabitants
# randomly load countries for 5 minutes
loadtest/gradlew -b loadtest/build.gradle loadCountries
# randomly load inhabitants for 5 minutes
loadtest/gradlew -b loadtest/build.gradle loadInhabitants
```
