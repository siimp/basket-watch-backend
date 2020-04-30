FROM openjdk:14-alpine
COPY build/libs/basket-watch-backend-*-all.jar basket-watch-backend.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "basket-watch-backend.jar"]
