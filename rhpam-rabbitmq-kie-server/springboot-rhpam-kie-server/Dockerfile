# IN OCP -Jenkisfile FROM image-registry.openshift-image-registry.svc:5000/openshift/redhat-openjdk18-openshift
# OUT of OCP
FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift

ENV AB_ENABLED off
ENV AB_JOLOKIA_AUTH_OPENSHIFT true

EXPOSE 8090

RUN mkdir -p /deployments/maven/repository
#RUN mkdir -p /deployments/maven/repository/za/co/amazon/telkom/amazon-buyjourney-bpm/1.0.1
#RUN mkdir -p /deployments/maven/repository/com/redhat/new-hire/1.0.0

#RUN curl http://nexus-cicd-pam.apps.cluster-hapoalim-8074.hapoalim-8074.example.opentlc.com/repository/maven-public/com/redhat/new-hire/1.0.0/new-hire-1.0.0.pom --output new-hire-1.0.0.pom --silent
#RUN curl http://nexus-cicd-pam.apps.cluster-hapoalim-8074.hapoalim-8074.example.opentlc.com/repository/maven-public/com/redhat/new-hire/1.0.0/new-hire-1.0.0.JAR --output new-hire-1.0.0.jar --silent

#RUN mv new-hire-1.0.0.pom /deployments/maven/repository/com/redhat/new-hire/1.0.0
#RUN mv new-hire-1.0.0.jar /deployments/maven/repository/com/redhat/new-hire/1.0.0

# Copy artifact
COPY target/pam-rabbitmq-service-1.6.1.jar /deployments/

USER root

RUN chmod -R 777 /deployments/maven/repository

USER jboss
