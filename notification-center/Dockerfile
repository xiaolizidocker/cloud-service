FROM java:latest

ADD target/notification-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/notification-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 9002

CMD ["java","-jar","/usr/local/jar/app.jar"]