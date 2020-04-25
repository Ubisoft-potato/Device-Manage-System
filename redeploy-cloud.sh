#!/bin/bash
docker stop device-manage-system || true
docker rmi  device-manage-system:dev || true
cd  Device-Manage-System-Server
mvn clean package -DskipTests
docker build -t device-manage-system:dev .
docker run --rm  -d \
--link mysql5.7:mysql  \
-e ACTIVE_ENV=dev \
--name device-manage-system \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 8081:8081 device-manage-system:dev