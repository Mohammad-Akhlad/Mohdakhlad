#!/bin/bash

# Function to display an error message and exit
function error_exit {
    echo "$1" >&2
    exit 1
}

# Check if the profile argument is provided
if [ -z "$1" ]; then
  echo "Please provide a profile argument: 'dev' or 'prod'"
  exit 1
fi

# Get the script's directory
script_directory="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Change to the project directory
project_directory="$script_directory"

# Run Maven clean and install
mvn clean install -f "$project_directory" || error_exit "Error: Failed to execute 'mvn clean install'."

# Set the environment variables based on the profile
if [ "$1" == "dev" ]; then
    export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/Akhlad123
    export SPRING_DATASOURCE_USERNAME=mohdakhlad
    export SPRING_DATASOURCE_PASSWORD=Akhlad123
elif [ "$1" == "prod" ]; then
    export SPRING_DATASOURCE_URL=jdbc:postgresql://akhlad-rds.cozepxcgspyj.eu-north-1.rds.amazonaws.com:5432/Akhlad123
    export SPRING_DATASOURCE_USERNAME=mohdakhlad
    export SPRING_DATASOURCE_PASSWORD=Akhlad123
fi

# Run the Spring Boot application with the specified profile
mvn spring-boot:run -Dspring.profiles.active="$1" -f "$project_directory" || error_exit "Error: Failed to execute 'mvn spring-boot:run' with the '$1' profile."
