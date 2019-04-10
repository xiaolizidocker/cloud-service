FROM java:latest

ADD target/file-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/file-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 9006

CMD ["java","-jar","/usr/local/jar/app.jar"]