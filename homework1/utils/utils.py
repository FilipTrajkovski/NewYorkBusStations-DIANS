import traceback
from configparser import ConfigParser
from os.path import abspath

from hashlib import sha1
from re import escape

import requests
from mysql.connector import connect

from utils import Constants


# Loads the database configuration
def load_db_config():

    config = ConfigParser()

    try:
        # Try to open the configuration file and parse it's contents
        config.read(abspath("db.ini"))

        # Checks if all the required data in the configuration is not missing
        assert config["DB_CONFIG"]
        assert config["DB_CONFIG"]["DB_HOST"]
        assert config["DB_CONFIG"]["DB_USER"]
        assert config["DB_CONFIG"]["DB_PASSWORD"]
        assert config["DB_CONFIG"]["DB_PORT"]

        return config["DB_CONFIG"]
    except FileNotFoundError:
        print(Constants.CONFIGURATION_FILE_NOT_FOUNT_ERROR_TEXT)
        exit(-1)
    except KeyError as ke:
        print(Constants.CONFIGURATION_KEY_MISSING, ke)
        exit(-1)
    except Exception:
        print(Constants.ERROR_GENERAL_TEXT)
        print(traceback.format_exc())
        exit(-1)


# Creates the database connection
def get_db_connection():

    config = load_db_config()

    try:
        mysql_conn = connect(
            user=config["DB_USER"],
            password=config["DB_PASSWORD"],
            host=config["DB_HOST"],
            port=config["DB_PORT"]
        )

        return mysql_conn
    except Exception:
        print(Constants.ERROR_CONNECTION)
        print(traceback.format_exc())
        exit(-1)


# Run the sql commands in the provided `sql_script_name` using the provided `connection`
def execute_sql_file(connection, sql_script_name):
    cursor = connection.cursor()
    sql_script = open(sql_script_name, 'r', encoding="UTF-8")

    # Extract the data
    sql_script_data = sql_script.read()
    sql_script.close()

    # Split all sql commands present in the file
    sql_commands = sql_script_data.split(";")

    # Remove empty last element
    sql_commands = sql_commands[:-1]

    for sql_command in sql_commands:
        try:
            print("Running: \n", sql_command.strip())
            cursor.execute(sql_command)
            print("Success!! \n")
        except Exception as exception:
            print("Command issue: ", exception)
            exit(-1)

    # Use the newly generated schema
    cursor.execute("USE buses")


# Saves the open street map data filtered by the provided `params` into the provided `file_name`
def save_osm_data(params, file_name):
    osm_url = "https://overpass-api.de/api/interpreter" + params

    print("Requesting the data from the url: ", osm_url)
    # Request the data and extract it
    response = requests.get(osm_url)
    data = response.text

    print("Saving the data to the file: ", file_name)
    # Create the file with the provided file name (if it doesn't exist) and save the data from the request
    file = open(file_name, "w+", encoding="UTF-8")
    file.write(data)
    file.close()

    print("Success!!")


# Checks the hashed current csv_data with the latest updated one if it's the same
def is_csv_data_already_imported(connection, csv_data):
    cursor = connection.cursor()

    print("Checking if previous data exists and if it's outdated")
    get_latest_hash_query = "SELECT di.data_hash FROM buses.data_info AS di ORDER BY di.updated_on DESC LIMIT 1"

    cursor.execute(get_latest_hash_query)

    result = cursor.fetchone()

    if result is not None:
        last_data_hash = result[0]
        current_data_hash = sha1(csv_data.encode()).hexdigest()

        return last_data_hash == current_data_hash

    return False


# Truncates all the data from the bus, bus_station and bus_network tables
def truncate_table_data(connection):
    cursor = connection.cursor()

    set_key_checks_query = "SET FOREIGN_KEY_CHECKS = %s"
    truncate_query = "TRUNCATE TABLE %s"

    try:
        cursor.execute(set_key_checks_query % 0)

        print("Truncating table bus_network")
        cursor.execute(truncate_query % "bus_network")
        print("Success!!")

        print("Truncating table bus")
        cursor.execute(truncate_query % "bus")
        print("Success!!")

        print("Truncating table bus_station")
        cursor.execute(truncate_query % "bus_station")
        print("Success!!")

        cursor.execute(set_key_checks_query % 1)
    except Exception:
        print(Constants.ERROR_GENERAL_TEXT)
        print(traceback.format_exc())
        exit(-1)


# Escapes the provided string
def __escape_string(string_to_escape):
    return escape(string_to_escape)\
        .replace("'", "\\'")\
        .replace('"', '\\"')


# Inserts a row to the data_info table with the hash of the provided csv_data
def insert_data_info_row(connection, csv_data):
    cursor = connection.cursor()

    insert_data_info_row_query = "INSERT INTO buses.data_info(data_hash) VALUES ('%s')"

    data_hash = sha1(csv_data.encode()).hexdigest()

    print("Saving data hash: '%s'" % data_hash)
    cursor.execute(insert_data_info_row_query % data_hash)
    connection.commit()
    print("Success!!")


# Dumps the provided csv_data into the database using the provided connection
def dump_csv_data_to_db(connection, csv_data):
    cursor = connection.cursor()

    print("Dumping csv data into database")
    insert_bus_station_query = "INSERT INTO buses.bus_station(name, longitude, latitude) VALUES ('%s', '%s', '%s')"
    insert_bus_query = "INSERT INTO buses.bus(name) VALUES ('%s')"
    insert_bus_network_query = "INSERT INTO buses.bus_network(bus_id, bus_station_id) VALUES ('%s', '%s')"
    get_bus_query = "SELECT bus.id FROM buses.bus WHERE bus.name = '%s' LIMIT 1"

    longitude_column_index = 1
    latitude_column_index = 2
    network_column_index = 3
    bus_stop_column_index = 4

    for index, csv_row in enumerate(csv_data):
        csv_row_data = csv_row.split(",")

        try:
            longitude = __escape_string(str(csv_row_data[longitude_column_index]))
            latitude = __escape_string(str(csv_row_data[latitude_column_index]))
            network = __escape_string(str(csv_row_data[network_column_index]))
            bus_stop = __escape_string(str(csv_row_data[bus_stop_column_index]))
        except IndexError:
            print("Invalid or corrupt row data with index: %s with data: '%s'. Skipping..." % (index, csv_row))
            continue

        # Insert the bus station into the database
        cursor.execute(insert_bus_station_query % (bus_stop, longitude, latitude))

        bus_station_id = cursor.lastrowid

        # If the buses column isn't empty insert all of the found buses passing through the bus station
        #  and link them to the bus station
        if network != "":
            for bus_name in network.split(";"):

                # Search for the bus if it has been previously added
                cursor.execute(get_bus_query % bus_name)

                result = cursor.fetchone()

                # If we don't find the bus insert it otherwise get it's id
                if result is None:
                    # Save the bus
                    cursor.execute(insert_bus_query % bus_name)

                    bus_id = cursor.lastrowid
                else:
                    bus_id = result[0]

                # Link the bus to the bus station
                cursor.execute(insert_bus_network_query % (bus_id, bus_station_id))

    connection.commit()
    print("Success!!")
