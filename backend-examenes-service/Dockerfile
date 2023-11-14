FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-exanenes-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/backend-examenes-service-0.0.1-SNAPSHOT.jar"]