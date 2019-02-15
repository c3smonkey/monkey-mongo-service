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

## Add GitHub WebHook
(see : https://docs.openshift.com/container-platform/3.5/dev_guide/builds/triggering_builds.html#github-webhooks
)

Get the Secret from the _BuildConfig_ 
```bash
oc get bc monkey-app -o yaml
```


Get the URL from the _BuildConfig_ configuration
Search for the GitHub URL

```bash
oc describe bc monkey-app
```
```bash
Webhook GitHub:
	URL:	https://okd.cluster.ch/apis/build.openshift.io/v1/namespaces/dev/buildconfigs/monkey-app/webhooks/<secret>/github

```

Go to your GitHub repository  (https://github.com/c3smonkey/monkey-mongo-service/settings/hooks/new)
```bash
Settings
    |
    -> Webhooks 
        |
        -> Add webhook     
```
- Change Content-Type to _application/json_
- Paste the GitHub WebHook URL with the replaced _<secret>_ from your _BuildConfig_ secret.
```bash
https://okd.cluster.ch/apis/build.openshift.io/v1/namespaces/dev/buildconfigs/monkey-app/webhooks/MYSECRETFROM_BUILD_CONFIG/github
``` 
- If your Cluster don`t have a valid certificate you have to disable the SSL verification
_Disable (not recommended)_

## Get all 
```bash
oc get all --selector app=monkey-app
```

## Delete all 
```bash
oc delete all --selector app=monkey-app
```


# i18n API Calls

## Validation Messages

```bash
http POST :8080/api/customer/ age=23 "Accept-Language: de"
```

Swiss german validation message
```bash
http POST :8080/api/customer/ age=23 "Accept-Language: de-CH"
```






# Blue Green
## Deploy Spring Boot App as Docker Image 
### Feature 1
```bash
oc new-app --docker-image=c3smonkey/monkey-mongo-service:feature1 \
    --name='feature1' \
    -l name='feature1' \
    -e mongo.user='<USER>' \
    -e mongo.password='<PASSWORD>' \
    -e mongo.host='<HOST>' \
    -e mongo.port='<PORT>' \
    -e mongo.database='<DATABASE>' \
    -e SELECTOR=feature1
```
```bash
oc expose service feature1 \
    --name=feature1 \
    -l name='feature1'
```


### Feature 2
```bash
oc new-app --docker-image=c3smonkey/monkey-mongo-service:feature2 \
    --name='feature2' \
    -l name='feature2' \
    -e mongo.user='<USER>' \
    -e mongo.password='<PASSWORD>' \
    -e mongo.host='<HOST>' \
    -e mongo.port='<PORT>' \
    -e mongo.database='<DATABASE>' \
    -e SELECTOR=feature2
```
```bash
oc expose service feature2 \
    --name=feature2 \
    -l name='feature2'
```



## Expose Feature1 Service to BlueGreen Service
```bash
oc expose service feature1 --name=bluegreen
```

### Test Routing
```bash
for x in (seq 11); http http://bluegreen-dev.apps.c3smonkey.ch/actuator/info | jq .git.branch ; end
```


## Switch to Feature2 
```bash
oc patch route/bluegreen -p '{"spec":{"to":{"name":"feature2"}}}' 
```
## Switch to Feature1
```bash
oc patch route/bluegreen -p '{"spec":{"to":{"name":"feature1"}}}'
```











oc get secrets
oc secrets link --for=mount,pull default mongodb-ephemeral-dksxt-credentials-21m5m
oc describe serviceaccount default
oc policy add-role-to-user view system:serviceaccount:dev:default





$ kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default

oc policy add-role-to-user cluster-reader system:serviceaccount:dev:default






```bash
oc new-app --docker-image=c3smonkey/monkey-mongo-service:k8s \
    --name='k8s' \
    -l name='k8s' \
    -e SELECTOR=k8s
    
```

```bash
oc get all --selector app=k8s 
```
        
        