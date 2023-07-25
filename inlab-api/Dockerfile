FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir /inlab-audio-file
RUN apt-get update
RUN apt-get install -y ffmpeg
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod","/app.jar"]
