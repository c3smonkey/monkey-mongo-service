# Openshift

## Create Project
```bash
oc new-project dev \
         --description="Development Stage" \
         --display-name="Development"
```
## Deploy Spring Boot App with S2i
```bash
oc new-app \
    codecentric/springboot-maven3-centos~https://github.com/c3smonkey/monkey-mongo-service.git \
    --name=monkey-app \
    -l app=monkey-app  \
    -n dev
```
