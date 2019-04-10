FROM java:latest

ADD target/oauth-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/oauth-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 8062

CMD ["java","-jar","/usr/local/jar/app.jar"]