FROM eclipse-temurin:21-jre-alpine AS builder

WORKDIR /tmp

COPY target/*.jar app.jar

RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM eclipse-temurin:21-jre-alpine

WORKDIR /opt/app-root

COPY --from=builder /tmp/extracted/dependencies/ ./
COPY --from=builder /tmp/extracted/spring-boot-loader/ ./
COPY --from=builder /tmp/extracted/snapshot-dependencies/ ./
COPY --from=builder /tmp/extracted/application/ ./

ENTRYPOINT ["java", "-jar","app.jar"]
