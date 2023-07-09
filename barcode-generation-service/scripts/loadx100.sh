#!/bin/bash

for (( i = 0; i < 10000; i++ )); do

    random_number=$(( RANDOM % 100 + 1 ))

    # Sleep for the random number of milliseconds
    sleep_time=$(echo "scale=3; $random_number / 1000" | bc)

    echo "Slept for $sleep_time seconds"
    sleep "$sleep_time"
    curl --request GET -sL \
         --url 'http://localhost:8080/barcode/generate?message=ABCDFGNSDGSNFGSEIOGJNPRIGVJVGSDPFNEDPIFNEPFIENWFPIWENFPWEJFWEPFJWEPFJWEPFNWEFPIWEHFPIWEJFWPEIFNWEPFWEPIFJWEPFHJWEP' &
done