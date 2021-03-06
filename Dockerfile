FROM adoptopenjdk:11-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/ads-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "ads-api-1.0.0-SNAPSHOT.jar"]