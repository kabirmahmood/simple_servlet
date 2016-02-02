FROM tomcat
MAINTAINER xyz

ADD target/my-web-app.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
