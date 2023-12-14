FROM openjdk:17
WORKDIR /app
EXPOSE 9090
COPY target/e-wallet.jar /app/e-wallet.jar
ENTRYPOINT ["java", "-jar", "e-wallet.jar"]