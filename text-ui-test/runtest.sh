#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# count the number of testcases
testNo=$( ls testcases/input* 2>/dev/null -Ubad1 -- log* | wc -l )

for (( i = 1; i <= testNo; i++ ))
do
    java -classpath ../bin Duke < testcases/input-$i > temp-$i
    diff temp-$i testcases/output-$i

    if [ $? -eq 0 ]
    then
        echo "Test result: PASSED"
    else
        echo "Test result: FAILED"
    fi

    rm temp-$i
done
