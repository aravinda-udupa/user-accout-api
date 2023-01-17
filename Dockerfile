FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD build/libs/useraccountapi-0.0.1-SNAPSHOT.jar /user-account-api.jar
ENTRYPOINT ["java", "-jar", "/user-account-api.jar"]