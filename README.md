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
        -e mongo.user='<USER>' \
        -e mongo.password='<PASSWORD>' \
        -e mongo.host='<HOST>' \
        -e mongo.port='<PORT>' \
        -e mongo.database='<DATABASE>' \
        --name=monkey-app \
        -l app=monkey-app  \
        -n dev
```

## Get all 
```bash
oc get all --selector app=monkey-app
```

## Delete all 
```bash
oc delete all --selector app=monkey-app
```

