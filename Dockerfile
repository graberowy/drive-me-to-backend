FROM openjdk:16-alpine
ADD target/DriveMeTo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar DriveMeTo-0.0.1-SNAPSHOT.jar