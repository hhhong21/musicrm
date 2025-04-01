@echo off
setlocal
set DIR=%~dp0
set MVN_JAR=%DIR%\.mvn\wrapper\maven-wrapper.jar
java -jar "%MVN_JAR%" %*
