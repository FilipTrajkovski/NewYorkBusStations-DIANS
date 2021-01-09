FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/api-1.1.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]