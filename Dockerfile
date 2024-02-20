FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

ADD ./pom.xml pom.xml

RUN mvn verify clean --fail-never

ADD . .

RUN mvn install

ENTRYPOINT ["mvn", "spring-boot:run"]

