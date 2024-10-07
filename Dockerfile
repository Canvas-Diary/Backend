FROM amazoncorretto:17-alpine-jdk
ARG JAR_PATH=/Bootstrap-Module/build/libs/*.jar
COPY ${JAR_PATH} app.jar
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","app.jar"]