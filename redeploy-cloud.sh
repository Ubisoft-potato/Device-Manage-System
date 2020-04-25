#!/bin/bash
kubectl delete -f deployment.yaml | true
docker rmi  device-manage-system:dev || true
cd  Device-Manage-System-Server
mvn clean package -DskipTests
docker build -t device-manage-system:dev .
kubectl apply -f deployment.yaml
