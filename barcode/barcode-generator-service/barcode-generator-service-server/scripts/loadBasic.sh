#!/bin/bash

for (( i = 0; i < 500000; i++ )); do

    msg_length=100

    msg=$(cat /dev/urandom | env LC_ALL=C tr -dc 'A-Z0-9' | fold -w $msg_length | head -n 1)

    random_number=$(( RANDOM % 1000 + 1 ))

    sleep_time=$(echo "scale=3; $random_number / 10000" | bc)

    echo "Generating barcode for: $msg"
    sleep "$sleep_time"
    curl --request GET -sL \
         --url "http://localhost:8080/barcode/generate?message=$msg" \
         --output /dev/null &
done