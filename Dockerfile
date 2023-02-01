FROM maven:3.8.3-openjdk-17 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./build/target/*.jar ./e-commerce-api.jar
ENTRYPOINT java -jar e-commerce-api.jar