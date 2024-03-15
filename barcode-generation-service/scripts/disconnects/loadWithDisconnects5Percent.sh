#!/bin/bash

msg_length=100

for (( i = 0; i < 500000; i++ )); do

    random_number=$(( RANDOM % 20 ))

    msg=$(cat /dev/urandom | env LC_ALL=C tr -dc 'A-Z0-9' | fold -w $msg_length | head -n 1)

    echo "Generating barcode for: $msg"

    if [ $random_number == 0 ]; then
      curl --request GET -sL \
               --url "http://localhost:8080/barcode/generate?message=$msg" &
    else
      curl --request GET -sL \
               --url "http://localhost:8080/barcode/generate?message=$msg" \
               --output /dev/null &
    fi

done