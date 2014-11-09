## hazelnate

I initially created this project to get a better understanding of the hazelcast in-memory data grid. As we started to setup hazelcast on one of my clients main products, I also used it to clarify certain miss-understandings and to reproduce glitches we approached during the setup phase. 

### The project currently has 4 modules
* **client**: contains the main "business" logic
* **database**: wrapper to start up a h2 database 
* **server**: reprents a single hazelcast server node 
* **mancenter**, wrapper to start up the hazelcast mancenter    

This project mainly focuses on the distributed caching abilities, hazelcast offers in conjunction with hibernate. Other powerful features like in-memory computing etc. are (currently) not covered. 

### Quick start

```bash
git clone https://github.com/u6f6o/hazelnate.git 
```


