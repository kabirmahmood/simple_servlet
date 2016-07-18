FROM tomcat:8.0

ADD target/my-web-app.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
