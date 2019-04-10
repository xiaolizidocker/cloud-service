FROM java:latest

ADD target/user-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/user-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 8061

CMD ["java","-jar","/usr/local/jar/app.jar"]