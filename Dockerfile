FROM maven:3.8.6-openjdk-11

WORKDIR /app

ADD ./pom.xml pom.xml

RUN mvn verify clean --fail-never

ADD . .

RUN mvn install

ENTRYPOINT ["mvn", "spring-boot:run"]
