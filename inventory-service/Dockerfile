FROM openjdk:11
EXPOSE 8183
ADD /target/*.jar inventory-service.jar
ENTRYPOINT ["java","-jar","inventory-service.jar"]