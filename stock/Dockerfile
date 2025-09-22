FROM quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-21 AS build
COPY --chown=quarkus:quarkus --chmod=0755 mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn
COPY --chown=quarkus:quarkus pom.xml /code/
USER quarkus
WORKDIR /code
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.8.1:go-offline
COPY src /code/src
RUN ./mvnw package -Dmaven.test.skip

FROM registry.access.redhat.com/ubi9/ubi-minimal:9.5
WORKDIR /work/
RUN microdnf install -y java-21-openjdk-headless && microdnf clean all
COPY --from=build /code/target/quarkus-app/lib/ /work/lib/
COPY --from=build /code/target/quarkus-app/*.jar /work/
COPY --from=build /code/target/quarkus-app/app/ /work/app/
COPY --from=build /code/target/quarkus-app/quarkus/ /work/quarkus/

# Install ca-certificates to fix TLS certificate verification issues
USER root
RUN microdnf update -y && \
    microdnf install -y ca-certificates tzdata && \
    microdnf clean all && \
    ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo "America/Sao_Paulo" > /etc/timezone

# Set timezone environment variable
ENV TZ=America/Sao_Paulo

# set up permissions for user `1001`
RUN chmod 775 /work \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

EXPOSE 80
USER 1001

ENTRYPOINT ["java", "-jar", "/work/quarkus-run.jar", "-Dquarkus.http.host=0.0.0.0"]