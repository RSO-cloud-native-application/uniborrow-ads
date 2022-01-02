# RSO: ads microservice

## Run application's configuration server

```bash
consul agent -dev
```

## Run application's database in docker

```bash
docker run -d --name ads-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ads -p 5432:5432 postgres:13
```

## Build and run application

```bash
mvn clean package
cd api/target
java -jar ads-api-1.0.0-SNAPSHOT.jar
```

Available at: localhost:8080/v1/ads

## Build app's docker image and push it to repo

```bash
docker build -t app-img .
docker tag app-img efodx/uniborrow-ads
docker push efodx/uniborrow-ads 
```

## Create a docker network and run the app's database and app through it

```bash
docker build -t app-img
docker network create ads
docker run -d --name ads-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ads -p 5432:5432 --network ads postgres:13
docker run -p 8080:8080 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://ads-db:5432/ads app-img
```
