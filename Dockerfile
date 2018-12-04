FROM openjdk:8
ADD target/load-price-1.0.0.jar load-price.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "load-price.jar"]
