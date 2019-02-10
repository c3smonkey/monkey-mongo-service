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


