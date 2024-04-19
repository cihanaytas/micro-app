FROM openjdk:11
EXPOSE 8180
ADD /target/*.jar api-gateway-service.jar
ENTRYPOINT ["java","-jar","api-gateway-service.jar"]