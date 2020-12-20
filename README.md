# NewYorkBusStations-DIANS
Simple application for searching bus stations or bus routes. The scope of the application concerns selecting from a list of bus stations or bus names and get all the bus stations that a bus passes through or busses that pass through certain bus stations. It's only limited to the central part of New York so we cannot search for bus stations that are in the outskirts of the city. The base application is maven-based, with a python module that initializes the database. The backend will be a REST-ful Spring Boot service that will contain the business logic of the application and the frontend will be made with the Typescript/React frameworks. Another possible feature would be to add a map that will mark down the found bus stations or bus stations that the selected bus goes through. It uses data from OpenStreetMap and tools such as osmconverter to parse the data. 

### Homework Part 1: Pipe for initializing the database and filling it with data

Instructions on how to run the python pipe script and requrements:
- Requirements:
  - Python 3.7 or higher
  - Pip package manager
  - Mysql database
- Nagivate to the `homework1` directory
- Install the required packages for the python script to work (<code>pip install -r requirements.txt</code>)
- Fill db connection params in the <code>db.ini</code> file
- Run the script <code>database_init.py</code> (<code>python database_init.py)

### Homework Part 2: Achitecture documentation, mockups and proof of concept application

Navigate to the `homework2` folder for further instructions.

### Homework Part 3: Implementation and deployment

Navigate to the `homework3` folder for the structure of the implemented app.

The app is deployed using heroku and it is currently running on: https://boiling-fortress-42885.herokuapp.com/
Examples for bus: B61, B57, B41
Examples for bus stations: Castle Hill Avenue & Bruckner Boulevard, Farmers Boulevard & 133rd Road
