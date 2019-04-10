FROM java:latest

ADD target/redis-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/redis-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 8063

CMD ["java","-jar","/usr/local/jar/app.jar"]