FROM openjdk:11-jre-slim

COPY target/smart-dustbin-management-platform-*.jar smart-dustbin-management-platform.jar
ENTRYPOINT ["java","-jar","/smart-dustbin-management-platform.jar","--server.port=80"]