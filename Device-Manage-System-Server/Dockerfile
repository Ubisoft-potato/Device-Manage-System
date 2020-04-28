FROM openjdk:8-jdk-alpine
ADD target/device-manage-system-1.0.0-SNAPSHOT.jar /app/device-manage-system.jar
ENV JAVA_OPTS "-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/java_pid%p.hprof -Xmx210m -Xms210m  -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["sh" , "-c", "java  $JAVA_OPTS  -Dspring.profiles.active=$ACTIVE_ENV -jar /app/device-manage-system.jar" ]
EXPOSE 8081
