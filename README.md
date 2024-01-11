#  Spring Boot GraphQL Application

This application's primary goal is to call an external API (here it is mock api) and persist it into Database. And using GraphQL queries to search the data from database. 


## Mockoon Setup 

- Installed Mockoon desktop version and imported the Json mock (Simple Vehicle API) into Mockoon via File -> Open Environment. 
   [Note] The root elements in the mock are removed to import the json file as it was throwing error to upload. But no changes made in the data.

   ![mockoon.png](src%2Fdocs%2Fmockoon.png)

- We can see the Routes with different endpoints
- Run the Api by clicking on the play button. As given in the mock, the mock api runs in the base URL "http:localhost:1337/vehicle"

## Spring Boot Setup

- Created a Spring boot application with required dependencies like Web, JPA, H2 Database GraphQL via Spring Initializer.
- Added the schema.graphqls in the resources/graphql folder. The file has the type Query and different schema types.
- Created different packages like component, config, controller, model, repository for the corresponding java files.

## Build

Start the application
    ```
       mvn spring-boot:run
    ```

- The application runs in the default port : 8080
- On the start of the application, the DataLoader component will be triggered as it implements the CommandLineRunner to make API calls, fetch data and save into the Database.
- Connect to the in-memory H2 Database, to see if the tables are created and data are pushed into those tables.
![H2DB-Vehicle.png](src%2Fdocs%2FH2DB-Vehicle.png)
![H2DB-Service.png](src%2Fdocs%2FH2DB-Service.png)

- Once the data is inserted into the DB, I have added a controller to make the graphQl search
- GraphQLController class has the different QueryMapping for fetching all Vehicles, vehicleById, vehicleByName ( Full name/ partial name) and VehicleByService(name, status).

## application.properties
```
spring.graphql.graphiql.enabled=true
```
- This property enables the graphiQL ( UI to execute the GraphQL query to see the results)
![vehicleById.png](src%2Fdocs%2FvehicleById.png)
![vehicleByName.png](src%2Fdocs%2FvehicleByName.png)
![vehcileByService.png](src%2Fdocs%2FvehcileByService.png)
![vehicleByService2.png](src%2Fdocs%2FvehicleByService2.png)

Improvements

- Connecting to MongoDB
- Refactoring the code
