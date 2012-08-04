#!/bin/bash
echo make sure projects are built and war files exist via mvn package
java -jar target/dependency/jetty-runner.jar --path /service target/remote-execute-engine.war 
