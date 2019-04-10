FROM java:latest

ADD target/log-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/log-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 8050

CMD ["java","-jar","/usr/local/jar/app.jar"]