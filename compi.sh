#!/bin/bash
reset
cd $(dirname "$0")
echo Compiling...
javac -cp .:lib/* src/eco/*.java
javac src/neural/*.java
echo Compiled...
cd src
echo Running…
java -cp .:../lib/* -Djava.library.path=../native eco/Main
cd eco
rm -f *.class
