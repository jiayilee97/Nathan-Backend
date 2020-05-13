FROM openjdk:8-jre-alpine
WORKDIR /nathan
COPY nathan-0.1.jar /nathan
EXPOSE 8080/tcp
ENTRYPOINT ["sh","-c"]
CMD ["java -jar nathan-0.1.jar"]
HEALTHCHECK CMD "wget --quiet --tries=1 --spider 127.0.0.1:8080/nathan/public/health || exit 1"