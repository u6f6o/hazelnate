## hazelnate

I initially created this project to get a better understanding of the hazelcast in-memory data grid. As we started to setup hazelcast on one of my clients main products, I also used it to clarify certain miss-understandings and to reproduce glitches we approached during the setup phase. 

### The project currently has 4 modules
* **client**: contains the main "business" logic
* **database**: wrapper to start up a h2 database 
* **server**: represents a single hazelcast server node
* **mancenter**, wrapper to start up the hazelcast mancenter    

This project mainly focuses on the distributed caching abilities, hazelcast offers in conjunction with hibernate. Other powerful features like in-memory computing etc. are (currently) not covered. 

### Quick start

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

