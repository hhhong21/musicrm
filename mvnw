#!/bin/sh
BASEDIR=$(dirname "$0")
exec java -jar "$BASEDIR/.mvn/wrapper/maven-wrapper.jar" "$@"
