#! /bin/bash
# compi.sh -- build script for eco
#
# usage - compi.sh [operation]
# 
# operations:
# 
# none (default) - build, run, then clean when finished
# build - just build
# run - run existing binaries
# clean - remove binaries and other generated files

# version number
vn=0.1

# location of binaries
BIN_DIR=bin

# location of library files
LIB_DIR=lib

# location of native files
NATIVE_DIR=native

# main java class
MAIN=eco.game.Main

build()
{
    echo building...
    if [ ! -d "$BIN_DIR" ]; then
        mkdir "$BIN_DIR"
    fi
    
    javac -d "$BIN_DIR" -cp .:"$LIB_DIR"/* src/**/**/*.java
}

run()
{
    echo running...
    if [ ! -s "$BIN_DIR" ]; then
        echo error -- bin directory is empty!
        echo have you built the project?
    else
        #cd "$BIN_DIR"

        java -cp "$BIN_DIR":"$LIB_DIR"/* -Djava.library.path="$NATIVE_DIR" "$MAIN"
    fi
}

clean()
{
    rm -rf "$BIN_DIR"
    rm -rf saves
    echo cleaned.
}

echo compi v$vn

if [[ -z "$1" ]]; then
    build
    run
    clean
elif [[ "$1" = "build" ]]; then
    build
elif [[ "$1" = "run" ]]; then
    run
elif [[ "$1" = "clean" ]]; then
    clean
else
    echo unknown operation \"$1\"
fi
