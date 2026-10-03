#!/bin/bash
# Compile and run (macOS/Linux). Put the mysql-connector-j JAR inside the lib folder first.
mkdir -p bin
javac -cp "lib/*" -d bin $(find src -name "*.java") && java -cp "bin:lib/*" Main
