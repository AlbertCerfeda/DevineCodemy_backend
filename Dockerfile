FROM ${CI_DEPENDENCY_PROXY_GROUP_IMAGE_PREFIX_SLASH}openjdk:11-slim

WORKDIR /codeland

COPY build/libs/devinecodemy-0.0.1-SNAPSHOT.jar /codeland/app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]