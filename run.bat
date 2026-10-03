@echo off
REM Compile and run (Windows). Put the mysql-connector-j JAR inside the lib folder first.
if not exist bin mkdir bin
dir /s /b src\*.java > sources.txt
javac -cp "lib/*" -d bin @sources.txt
if errorlevel 1 pause & exit /b 1
java -cp "bin;lib/*" Main
