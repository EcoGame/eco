#!/bin/bash
reset
cd $(dirname "$0")
if ! [ -d "bin" ]; then
  mkdir bin
fi
echo Compiling...
javac -d bin -cp .:lib/* src/eco/**/*.java
echo Compiled...
cd bin
echo Running...
java -cp .:../lib/* -Djava.library.path=../native eco/game/Main
cd eco
