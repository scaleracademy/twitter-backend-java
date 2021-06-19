#
# Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
# Copyright © 2021 Subhrodip Mohanta (hello@subho.xyz)
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

# Use latest openjdk:11-jre-slim image as the base
FROM openjdk:11-jre-slim

LABEL maintainer="Subhrodip Mohanta <hello@subho.xyz>"
LABEL artifact="twitter-backend"
LABEL platform="java"
LABEL name="Twitter Backend"
LABEL org.opencontainers.image.source="https://github.com/scaleracademy/twitter-backend-java"

# If the container is launched with re-mapped ports, these 
# ENV vars should be set to the remapped values.
ENV MYSQL_DB_HOST db
ENV MYSQL_DB_PORT 3306
ENV MYSQL_DB_UNAME root
ENV MYSQL_DB_PASSWD root

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

# If you are changing server port, be sure to change this as well
EXPOSE 8080

#Running the application with `prod` profile
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod". "/app.jar" ]
