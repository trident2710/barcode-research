#!/bin/bash

for (( i = 0; i < 1000; i++ )); do
    echo "Running request"
    curl --request GET -sL \
         --url 'http://localhost:8080/barcode/generate?message=ABCDFGNSDGSNFGSEIOGJNPRIGVJVGSDPFNEDPIFNEPFIENWFPIWENFPWEJFWEPFJWEPFJWEPFNWEFPIWEHFPIWEJFWPEIFNWEPFWEPIFJWEPFHJWEP' &
done