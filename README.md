## hazelnate

I initially created this project to get a better understanding of the hazelcast in-memory data grid. As we started to setup hazelcast on one of my clients main products, I also used it to clarify certain miss-understandings and to reproduce glitches we approached during the setup phase. 

### The project currently has 5 modules
* **application**: contains the main "business" logic
* **database**: wrapper to start up a h2 database 
* **node**: represents a single hazelcast node node
* **mancenter**: wrapper to start up the hazelcast mancenter    
* **loadtest**: some basic loadtests using gatling framework

This project mainly focuses on the distributed caching abilities hazelcast offers in conjunction with hibernate. Other powerful features like in-memory computing etc. are (currently) not covered. 

### Quick start
To start the application, simply clone the repo and execute the following commands one after another. The whole application is build using gradle wrapper. 

```zsh
git clone https://github.com/u6f6o/hazelnate.git 
cd hazelnate
# start the database on a new console 
./gradlew -p database startDatabase
# start the mancenter on a new console
./gradlew -p mancenter startMancenter
# start the hazelcast node on a new console
./gradlew -p node startNode
# start the application on a new console
./gradlew -p application startApplication

```

#### Access REST api 
The application basically offers a REST api which represents the world. You can fetch different things (countries, cities, languages etc.) or add new inhabitants to the world. For more information about the REST api, have a look at the WorldsEnd class.

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
./gradlew -p loadtest insert100kInhabitants
# randomly load countries for 5 minutes
./gradlew -p loadtest loadCountries
# randomly load inhabitants for 5 minutes
./gradlew -p loadtest loadInhabitants
```

## License

Copyright © 2015 Ulf Gitschthaler

Distributed under the MIT License.
