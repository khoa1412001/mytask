FROM adoptopenjdk/openjdk11

COPY target/mytask-0.0.1-SNAPSHOT.jar mytask.jar

ENTRYPOINT ["java","-jar","/mytask.jar"]