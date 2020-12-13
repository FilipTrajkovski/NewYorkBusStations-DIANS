import platform
import subprocess
from os import remove

from utils.utils import get_db_connection, execute_sql_file, save_osm_data, is_csv_data_already_imported, \
    truncate_table_data, dump_csv_data_to_db, insert_data_info_row


def convert_osm_data_to_csv(file_name, data_to_extract, csv_file_name):

    # Check the operating system that the script is running on before trying to execute the command
    operating_system = platform.system()

    if operating_system == "Windows":
        print("Detected windows operating system")
        print("Running osmconvert.exe with the provided data to extract and output file")
        subprocess.run(
            [
                "osmconvert.exe",
                file_name,
                "--all-to-nodes",
                "--csv=%s" % data_to_extract,
                "--csv-headline",
                "--csv-separator=,",
                "-o=%s" % csv_file_name
            ]
        )
        print("Success!!")
    else:
        print("Detected UNIX-based operating system")
        print("Making osmconvert_linux executable first")
        subprocess.run(
            [
                "chmod",
                "+x",
                "osmconvert_linux"
            ]
        )
        print("Successfully made osmconvert_linux executable")

        print("Running osmconvert_linux with the provided data to extract and output file")
        subprocess.run(
            [
                "osmconvert_linux",
                file_name,
                "--all-to-nodes",
                "--csv=%s" % data_to_extract,
                "--csv-headline",
                "--csv-separator=,",
                "-o=%s" % csv_file_name
            ]
        )
        print("Success!!")


if __name__ == "__main__":
    connection = get_db_connection()

    print("==================== RUNNING PIPE STEP ONE: CREATING THE DATABASE TABLES ====================")
    execute_sql_file(connection, "./db_scripts/create_tables.sql")

    print("==================== RUNNING PIPE STEP TWO: GETTING THE FILTERED NEW YORK BUS DATA FROM OSM ====================")
    output_format = "out:xml"
    bus_nodes_filter = "highway=bus_stop"
    new_york_coordinates = "40.44955971899028,-74.1741943359375,40.948788179193485,-73.61595153808594"
    params = "?data=[%s];node[%s](%s);out;" % (output_format, bus_nodes_filter, new_york_coordinates)

    osm_file_name = "new_york.osm"
    save_osm_data(params, osm_file_name)

    print("==================== RUNNING PIPE STEP THREE: PARSING SPECIFIC OSM DATA INTO A CSV USING OSM CONVERT ====================")
    data_to_extract = "@id @lon @lat network name"
    csv_file_name = "new_york.csv"
    convert_osm_data_to_csv(osm_file_name, data_to_extract, csv_file_name)

    # Delete the osm data file after we're done parsing the data
    remove(osm_file_name)

    print("==================== RUNNING PIPE STEP FOUR: GOING OVER THE GENERATED CSV AND INSERT IT INTO THE DATABASE ====================")

    csv_file = open(csv_file_name, "r", encoding="UTF-8")
    csv_data = csv_file.read()
    csv_file.close()

    # Remove the first row since it's just the column definitions
    #  and the last row since it's empty
    #  and split the data by the new row
    csv_data_split = csv_data.split("\n")[1:-1]

    result = is_csv_data_already_imported(connection, csv_data)

    if result:
        print("Previous data is already up to date. Skipping inserting of csv data")
    else:
        print("Data is outdated or not present. Continuing...")
        truncate_table_data(connection)
        dump_csv_data_to_db(connection, csv_data_split)

        # Update the data_info table
        insert_data_info_row(connection, csv_data)

    # Delete the csv data file after we're done inserting the data
    remove(csv_file_name)

    print("==================== FINISHED ====================")
