FROM maven:3.3.3-jdk-8-onbuild
MAINTAINER 'pierre.thirouin@ext.mpsa.com'
EXPOSE 8080
CMD ["java" "jar" "target/catalog-capsule.jar"]
