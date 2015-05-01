#!/bin/bash
reset
echo Returning from the ashes to live another day…
cd $(dirname "$0")
echo Compiling...
echo Compiled...
rm -r -f bin
cp -r src/ bin/
cp -r lib/ bin/lib
javac -cp .:lib/* bin/**/*.java
cd bin
echo Running…
rm -f eco/*.java
java -cp .:../lib/* -Djava.library.path=../native eco/Main
cd eco
rm -f *.class
