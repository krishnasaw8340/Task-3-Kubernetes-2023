# Task-3-Kubernetes-2023

Use the application that you created in task #1 or task #2. Create dockerfiles and build docker
images. Create kubernetes yaml manifests for the application (at least a deployment and a
service). It’s ok to expose the application with a LoadBalancer or NodePort service or with an
ingress. Spin up a single-node local Kubernetes cluster (Docker Desktop, Kind or Minikube) or
use a managed cluster like EKS, AKS, GKE etc. Deploy MongoDB to the cluster (it’s ok to use a
community helm chart for this, any other approach is fine as well). Then deploy the application
to the cluster by applying your manifests. The following requirements should be fulfilled:
● you can bring your application up by applying your yaml manifests
● mongodb is running in a separate pod
● the application should take mongo connection details from the environment variables
● the app endpoints should be available from your host machine
● a persistent volume should be used to store the MongoDB data. I.e., when you delete
the MongoDB pod the records in the db should not disappear.

# Table of Contents:

Table of Contents:
1. Project Setup :
   1.1 Dockerfile
   1.2 Docker Image
   1.3 Yaml Files
   - app-deployment.yml:
   - db-deplouyment.yml
   - mongo-secret.yml
   - mongo-config.yml
2. Installing minikube on ec2
   commands Used


# 1. Project Setup :

### 1.1 Dockerfile
    FROM openjdk:11
    COPY target/kaiburrr-0.0.1-SNAPSHOT.jar kaiburr.jar
    ENTRYPOINT ["java", "-jar", "/kaiburr.jar"]


### 1.2 Docker Image
##### Command used :
    docker build -t kaiburr-app .

    docker images

    docker run -t kaiburr-app 


## 1.3 Yaml Files
### app-deployment.yml:

    apiVersion: apps/v1 kind: Deployment metadata:
    name: kaiburrr spec:
    selector:
    matchLabels:
    app: kaiburrr replicas: 3 template:
    metadata:
    labels:
    app: kaiburrr spec:
    containers:

    - name: recursing_dijkstra image: kaiburrr ports:
    - containerPort: 8080 env:   # Setting Enviornmental Variables
    - name: DB_HOST # Setting Database host address from configMap valueFrom :
        configMapKeyRef :
            name : ${NAME} key :  ${PASS}

            - name: DB_NAME  # Setting Database name from configMap
              valueFrom :
                configMapKeyRef :
                  name : ${DB_NAME}
                  key :  ${PASS}

            - name: DB_USERNAME  # Setting Database username from Secret
              valueFrom :
                secretKeyRef :
                  name : mysql-secrets
                  key :  username

            - name: DB_PASSWORD # Setting Database password from Secret
              valueFrom :
                secretKeyRef :
                  name : mysql-secrets
                  key :  password

---

    apiVersion: v1 # Kubernetes API version
    kind: Service # Kubernetes resource kind we are creating
     metadata: # Metadata of the resource kind we are creating
    name: springboot-crud-svc
    spec:
    selector:
    app: springboot-k8s-mysql
    ports:
    - protocol: "TCP"
    port: 8080 # The port that the service is running on in the cluster
    targetPort: 8080 # The port exposed by the service
    type: NodePort # type of the service. 


## db-deplouyment.yml

    apiVersion: v1
    kind: PersistentVolumeClaim
    metadata:
    name: mongodb # name of PVC essential for identifying the storage data
    labels:
    app: mongodb
     tier: database
     spec:
     accessModes:
      - ReadWriteOnce   #This specifies the mode of the claim that we are trying to create.
      resources:
    requests:
    storage: 1Gi    #This will tell kubernetes about the amount of space we are trying to claim.
     ---
     apiVersion: apps/v1
     kind: Deployment
     metadata:
     name: mongodb-deployment
    namespace: mongo-namespace
    labels:
    app: mongodb
    spec:
    replicas: 1
      selector:
     matchLabels:
     app: mongodb
     template:
     metadata:
     labels: 
    app: mongodb
    spec:
    containers:
    - name: mongodb
    image: mongo 
    ports:
     - containerPort: 27017
    env:
    - name: MONGO_INITDB_ROOT_USERNAME
    valueFrom:
    secretKeyRef:
    name: mongodb-secret
    key: password
    - name: MONGO_DB
    valueFrom:
    secretKeyRef:
     name: mongodb-secret
     key: mongodbName
    ---
     apiVersion: v1
    kind: Service
      metadata:
    name: mongodb-service 
     namespace: mongo-namespace
     spec:
      selector:
    app: mongodb   
    ports:
      - protocol: TCP
     port: 27017
      targetPort: 27017 
    ---



### mongo-secret.yml
    apiVersion: v1
    kind: Secret
    metadata:
    name: mongodb-secret
    namespace: mongo-namespace
    type: Opaque
    data:
    mongo-root-username: dXNlcm5hbWU==
    mongo-root-password: cGFzc3dvcmQ==


### mongo-config.yml
    apiVersion: v1
    kind: ConfigMap
    metadata:
    name: db-config
    data:
    host: krihhh 
    dbName: kaiburr

## 2. 2. Setup and load minikube on ec2
      Command used :
      1. minikube version 
      2. minikube start --network-plugin=cni --cni=calico
      3. kubectl cluster-info
      4. kubectl get nodes
      5. kubectl get pods
      6. minikube stop
      7. minikube ip.
      
![aws-setup1](https://github.com/krishnasaw8340/Task-3-Kubernetes-2023/assets/63328010/4b256f5b-961c-461c-90d3-30e870ba4898)


 Minikube Installed:
   
![minikube-installed-2](https://github.com/krishnasaw8340/Task-3-Kubernetes-2023/assets/63328010/0ee16187-32fd-4930-a351-3c5d1d359f3c)

   

