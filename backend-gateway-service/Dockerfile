FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Gateway-0.0.1-SNAPSHOT.jar"]