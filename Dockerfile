FROM maven:3.6-ibmjava
COPY / /app
WORKDIR /app
RUN cd /app && mvn clean package
ENTRYPOINT mvn spring-boot:run
