# Spring Boot JWT Auth with Sidecar and Istio

This project demonstrates how to deploy a **Spring Boot application** with **JWT authentication** using **Istio as a sidecar** for security enforcement in a **Minikube environment**.

---

## **🚀 Usage**

### 1️⃣ Start Minikube

- To start Minikube with the **VirtualBox driver**, allocate **8GB of RAM and 4 CPUs**:
```bash
minikube delete --all
minikube start --driver=virtualbox --memory=8192mb --cpus=4
```

### 2️⃣ Create Keycloak
- Enable **Ingress Gateway** in Minikube:
```bash
minikube addons enable ingress
```

- Create `sso-dev` namespace
```bash
kubectl create namespace sso-dev
kubectl apply -f .k8s/sso.yaml -n sso-dev
```

### 3️⃣ Create Namespace and Set Context
- Create a dedicated namespace `app-dev` for the application and set it as the current namespace:
```bash
kubectl create namespace app-dev
kubectl config set-context --current --namespace=app-dev
```

### 4️⃣ Build and Deploy the Spring Boot App

- **Build the Application**
```bash
mvn clean package -DskipTests
```

- **Build the Docker Image**
```bash
docker build -t wallanaq/userinfo-api:0.0.1 .
docker images | grep userinfo-app
```

- **Load the Image**
```bash
minikube image load wallanaq/userinfo-api:0.0.1
minikube image ls | grep library
```

- **Deploy application**

```bash
cd .k8s/
kubectl apply -f deployment.yaml
```

### 5️⃣ Enable Istio in Minikube

- Enable **Istio** in Minikube:
```bash
minikube addons enable istio-provisioner
minikube addons enable istio
```

- **Label the Namespace for Sidecar Injection**
```bash
kubectl label namespace app-dev istio-injection=enabled --overwrite
kubectl get namespace -L istio-injection
```
✅ The output should show `istio-injection=enabled` for `app-dev`.

### 6️⃣ Deploy Sidecar

Change to the **Kubernetes specs directory** and apply the manifests:
```bash
cd .k8s/
kubectl apply -f request-authentication.yaml
kubectl apply -f authorization-policy.yaml
```

✅ This will:
- Deploy the application with **Istio sidecar**.
- Apply **JWT authentication** via Istio (`RequestAuthentication`).
- Enforce **access control** via Istio (`AuthorizationPolicy`).

### 7️⃣ Test

```bash
kubectl run -it --rm curlpod --image=curlimages/curl -- sh
```

```bash
curl --location 'http://keycloak-internal.sso-dev.svc.cluster.local:8080/realms/dev/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=postman' \
--data-urlencode 'client_secret=MV2CNZUi2WIuCjLWB1lMiplc3j9Ekizf'
```
```bash
curl --location 'http://keycloak-internal.sso-dev.svc.cluster.local:8080/realms/dev/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=postman' \
--data-urlencode 'client_secret=MV2CNZUi2WIuCjLWB1lMiplc3j9Ekizf' \
--data-urlencode 'username=john.doe' \
--data-urlencode 'password=S3cr3t123'
```
