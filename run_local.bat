echo make sure projects are built and war files exist via mvn install
java -jar target/dependency/jetty-runner.jar --path /service target/remote-execute-engine.war 
