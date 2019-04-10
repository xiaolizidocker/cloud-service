FROM java:latest

ADD target/gateway-zuul-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/gateway-zuul-2.0.jar /usr/local/jar/app.jar

EXPOSE 8080

CMD ["java","-jar","/usr/local/jar/app.jar"]