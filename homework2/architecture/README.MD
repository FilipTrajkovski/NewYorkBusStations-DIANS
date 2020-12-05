## Architecture of the application and implementation flow

![alt text](https://github.com/FilipTrajkovski/NewYorkBusStations-DIANS/blob/main/homework2/architecture/application_flow.png?raw=true)

The picture above represents the flow of the application. It's split into two main groups:
- Backend structure
- Frontend structure

## Backend structure
This group represents the core of the application that contains the business logic for getting/filtering/sorting the 
results from the database. It's layers contain the following:
- Model layer (Representing the database structure and associations)
- Repository layer (Containing the communication between Hibernate and the database for retrieving results)
- Service layer (Containing the business logic of the application)
- Web layer (Containing the endpoints for the application)

The application will also use the following modules:
- Lombook (Encapsulation of object properties such as getters and setters and automatic generation of builders and constructors)
- Liquibase (Database migration tool)

## Frontend structure
This group represents the look of the application and it's navigation through it. It's sub-groups consist of:
- View component (React component that contains the view of a certain view group)
- Typescript services (Service files containing the communication logic between the react components and the backend endpoints)