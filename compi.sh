#!/bin/bash
reset
cd $(dirname "$0")
if ! [ -d "bin" ]; then
  mkdir bin
fi
if ! [ -d "jars" ]; then
  mkdir jars
fi
echo "Compiling..."
javac -Xlint:unchecked -d bin -cp .:lib/* src/eco/**/*.java
echo "Compiled"
echo "Creating jar..."
jar cfm jars/eco.jar src/Manifest.txt -C bin .
echo "Running..."
cd jars
java -Djava.library.path=../native -jar eco.jar
if [ "$1" = "clean" ]; then
  cd ..
  rm -rf bin
  rm -rf saves
  rm -rf savetxt
  rm -rf screenshots
  echo "Cleaned"
fi
