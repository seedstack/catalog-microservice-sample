#
# Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.
#

FROM maven:3-openjdk-14 as maven
MAINTAINER 'adrien.lauer@mpsa.com'

# copy the project files
COPY ./pom.xml ./pom.xml
COPY ./src ./src
# build package
RUN mvn -q package
# our final base image
FROM openjdk:14-alpine
# set deployment directory
WORKDIR /app
# copy over the built artifact from the maven image
COPY --from=maven target/catalog-microservice-sample-capsule.jar .
# command to run binary
CMD ["java", "-jar", "catalog-microservice-sample-capsule.jar"]
# Expose the 8080 port
EXPOSE 8080
