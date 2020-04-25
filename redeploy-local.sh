#!/bin/bash
docker stop device-manage-system || true
docker rmi  device-manage-system:dev || true
cd  Device-Manage-System-Server
mvn clean package -DskipTests
docker build -t device-manage-system:dev .
kubectl delete -f deployment.yaml | true
kubectl apply -f deployment.yaml
