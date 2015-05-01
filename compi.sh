#!/bin/bash
reset
cd $(dirname "$0")
echo Compiling...
javac -d bin -cp .:lib/* src/eco/game/*.java
javac -d bin src/eco/neural/*.java
echo Compiled...
cd bin
echo Running…
java -cp .:../lib/* -Djava.library.path=../native eco/game/Main
cd eco
