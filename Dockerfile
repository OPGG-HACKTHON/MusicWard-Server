FROM openjdk:11-jre-slim
EXPOSE 8080
ENV TZ=Asia/Seoul
COPY ./build/libs/music_ward_server.jar ./app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
