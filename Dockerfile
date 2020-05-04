FROM openjdk:8-jre-alpine
WORKDIR /nathan
COPY nathan-0.1.jar /nathan
ENTRYPOINT ["sh","-c"]
CMD ["java -jar nathan-0.1.jar"]