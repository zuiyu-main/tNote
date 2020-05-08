FROM openjdk:8-jdk-alpine
ADD *.jar app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
