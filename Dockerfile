FROM openjdk:17-alpine
EXPOSE 8080
ADD target/testfile.jar testfile.jar
ENTRYPOINT ["java","-jar","/testfile.jar"]