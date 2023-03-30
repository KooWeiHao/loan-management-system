FROM ibm-semeru-runtimes:open-11-jre-focal
MAINTAINER erickoo
ARG JAR_FILE
ADD ./target/*.jar ./loan-management-system.jar
ENV PORT 8080
EXPOSE 8080
CMD java -Dspring.profiles.active=live -jar loan-management-system.jar
