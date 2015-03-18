#!/bin/bash
reset
echo "Returning from the ashes to live another day…"
#cd $(dirname "$0")
if [ "$1" == "name" ];
then echo "Compiling..."
javac src/eco/NameGen.java
echo "Compiled..."
sleep 1
echo "Running..."
java src/eco/NameGen.java
else
# Actual stuff below
echo "Compiling..."
javac -cp .:lib/* src/**/*.java
echo "Compiled..."
sleep 1
echo "Running..."
java -cp .:../lib/* -Djava.library.path=../native eco/Main
cd eco
rm -f *.class
fi
