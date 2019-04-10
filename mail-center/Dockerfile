FROM java:latest

ADD target/mail-center-2.0.jar /usr/local/jar/

RUN mv /usr/local/jar/mail-center-2.0.jar /usr/local/jar/app.jar

EXPOSE 8055

CMD ["java","-jar","/usr/local/jar/app.jar"]