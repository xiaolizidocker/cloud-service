FROM java:latest

ADD target/manage-backend-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/manage-backend-2.0.jar /usr/local/jar/app.jar

EXPOSE 8081

CMD ["java","-jar","/usr/local/jar/app.jar"]