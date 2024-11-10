FROM amazoncorretto:8-alpine-jdk
WORKDIR /app
COPY ./app /app
ENTRYPOINT ["/bin/sh", "-c", "pwd && ls -al && \
  java -Xmx1024m -classpath ./scouter-server-boot.jar \
       scouter.boot.Boot ./lib"]