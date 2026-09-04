FROM tomcat:10.1-jdk25-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY target/helpdesk.war /usr/local/tomcat/webapps/helpdesk.war
EXPOSE 8080
