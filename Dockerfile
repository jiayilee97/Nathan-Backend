FROM openjdk:8-jre-alpine
WORKDIR /nathan
COPY nathan-0.1.jar /nathan
EXPOSE 8080/tcp
ENTRYPOINT ["sh","-c"]
CMD ["java -jar nathan-0.1.jar"]
