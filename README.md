# Openshift

## Create Project
```bash
oc new-project dev \
         --description="Development Stage" \
         --display-name="Development"
```

## Add View Role for default service account
This is used for reading the Secrets via Spring Boot App 
```bash
oc policy add-role-to-user view system:serviceaccount:dev:default
```

## Deploy Feature1
```bash
oc apply -f k8s/deployment-feature1.yaml
```

## Import Imagage
```bash
oc import-image feature1 --from=c3smonkey/monkey-mongo-service:feature1
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
(see : https://docs.openshift.com/container-platform/3.5/dev_guide/builds/triggering_builds.html#github-webhooks)

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
    -e SELECTOR=feature1
```
Or 
```bash
oc import-image feature1 --from=c3smonkey/monkey-mongo-service:feature1
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











## A/B Testing


## Round Robin

We also need to override the default least connection balance setting of HAProxy 
using an annotation so that we use round-robin and the weightings specified in our route-backends command instead:

### 
```bash
oc expose service feature1 --name='ab-route' -l name='ab-route'
```
```bash
oc annotate route/ab-route haproxy.router.openshift.io/balance=roundrobin
```

### Set 50% to Service A and Service B
```bash
oc set route-backends ab-route feature1=50 feature2=50
```

#### Test Routing Round Robin Service A and Service B
```bash
for x in (seq 11); http http://ab-route-dev.apps.c3smonkey.ch/actuator/info` | jq .build.version ; end
```


### Set Backends Route 100% to Service A
```bash
oc set route-backends ab-route feature1=100 feature2=0
```
#### Test the Routing
```bash
for x in (seq 11); http http://ab-route-dev.apps.c3smonkey.ch/actuator/info` | jq .build.version ; end
```


### Set Backends Route +10% for Service B 
```bash
oc set route-backends ab-route --adjust feature2=+10%
``` 

#### Test Routing 10% to Service B 
```bash
for x in (seq 11); http http://ab-route-dev.apps.c3smonkey.ch/actuator/info` | jq .build.version ; end
```

## Get Routings
```bash
oc get routes
```



## Debug
```bash
oc debug dc/feature1
```

## CleanUp
```bash
oc delete all --selector app=feature1 
```
```bash
oc secrets unlink default  mongodb
```



        
        
        
        
        