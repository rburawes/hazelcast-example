# Cluster and nodes using Hazelcast
An example of using Hazelcast with Spring Boot.

Running the application
--------------

Build the application and create the runnable jar file.
1. Go to the project root and execute `./mvnw clean install`
2. A jar file will be created under `target` directory. e.g `target/cluster-0.0.1-SNAPSHOT.jar`
3. From the root directory run `java -jar target/cluster-0.0.1-SNAPSHOT.jar --server.port=8080`
4. To create more nodes repeat step number three but use another value for the port. e.g. `java -jar target/cluster-0.0.1-SNAPSHOT.jar --server.port=8081`
5. All the running nodes will be automatically detected and will part of the cluster.

Using the API
--------------
This application has three endpoints:
* `http://{host:port}/api/write` for sending data to Hazelcast distributed map.
* `http://{host:port}/api/read?key={value}` for retrieving data from map.
* `http://{host:port}/api/all` for reading all the data.

Data can be sent on any available nodes

    `curl -s -H 'Content-Type: application/json' -d '{"dataKey":"Test1","dataValue":"8080"}' http://localhost:8080/api/write`

    `curl -s -H 'Content-Type: application/json' -d '{"dataKey":"Test2","dataValue":"8081"}' http://localhost:8081/api/write`

    `curl -s -H 'Content-Type: application/json' -d '{"dataKey":"Test3","dataValue":"8082"}' http://localhost:8082/api/write`
    
To read or get a value from the map using a key as parameter

    `curl -s -H 'Content-Type: application/json' http://localhost:8080/api/read?key=Test1`
    
    `curl -s -H 'Content-Type: application/json' http://localhost:8081/api/read?key=Test1`
    
    `curl -s -H 'Content-Type: application/json' http://localhost:8082/api/read?key=Test1`
    
Read all the values of the map

    curl -s -H 'Content-Type: application/json' http://localhost:8080/api/all
    
As an alternative to test the application, a Postman file `HZL-API.postman_collection.json` can be found under the root folder of this project.  Import the file to the Postman app and run the examples.  


