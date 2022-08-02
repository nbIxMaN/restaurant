FROM amazoncorretto:11-alpine-jdk
MAINTAINER baeldung.com
COPY target/restaurant-1.0.0-SNAPSHOT.jar restaurant-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/restaurant-1.0.0-SNAPSHOT.jar"]