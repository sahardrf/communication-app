#!/bin/bash

# Define the source directory and the output directory for compiled classes
SRC_DIR="src/main/java"
OUT_DIR="out"

MAIN_CLASS="com.example.communicationapp.CommunicationApp"

# Create the output directory if it doesn't exist
mkdir -p $OUT_DIR

# Compile all Java source files
echo "Compiling Java files..."
find $SRC_DIR -name "*.java" > sources.txt
javac -d $OUT_DIR @sources.txt
rm sources.txt

# Check if compilation succeeded
if [ $? -ne 0 ]; then
    echo "Compilation failed. Exiting."
    exit 1
fi

# Run the program
echo "Running the program..."
java -cp $OUT_DIR $MAIN_CLASS
