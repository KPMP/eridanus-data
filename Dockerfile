FROM alpine:3.20.0

RUN apk update && \
    apk upgrade
RUN apk add openjdk21

VOLUME /tmp
ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

CMD java -cp /app:app/lib/* org.kpmp.eridanus.Application