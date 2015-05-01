#!/bin/bash
reset
cd $(dirname "$0")
echo Compiling...
javac -cp .:lib/* src/eco/game/*.java
javac src/eco/neural/*.java
echo Compiled...
cd src
echo Running…
java -cp .:../lib/* -Djava.library.path=../native eco/game/Main
cd eco
rm -f *.class
