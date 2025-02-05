# Spring Boot JWT Auth with Sidecar and Istio

This project demonstrates how to deploy a **Spring Boot application** with **JWT authentication** using **Istio as a sidecar** for security enforcement in a **Minikube environment**.

---

## **🚀 Usage**

### **1️⃣ Start Minikube**

To start Minikube with the **VirtualBox driver**, allocate **8GB of RAM and 4 CPUs**:
```bash
minikube delete --all
minikube start --driver=virtualbox --memory=8192mb --cpus=4
```

### **2️⃣ Create Namespace and Set Context**
Create a dedicated namespace (`app-dev`) for the application and set it as the current namespace:

```bash
kubectl create namespace app-dev
kubectl config set-context --current --namespace=app-dev
```
### **3️⃣ Enable Istio in Minikube**

Enable **Istio** and **Ingress Gateway** in Minikube:
```bash
minikube addons enable istio-provisioner
minikube addons enable istio
minikube addons enable ingress
```

- **Label the Namespace for Sidecar Injection**

To enable automatic **sidecar injection**, label the namespace:
```bash
kubectl label namespace app-dev istio-injection=enabled --overwrite
kubectl get namespace -L istio-injection
```
✅ The output should show `istio-injection=enabled` for `app-dev`.

### 4️⃣ Build and Deploy the Spring Boot App

- **Build the Application**
```bash
mvn clean package -DskipTests
```

- **Build the Docker Image**
```bash
docker build -t userinfo-api:0.0.1 .
docker images | grep userinfo-app
```

- **Push the Image to Minikube**

```bash
minikube image load userinfo-api:0.0.1
minikube image ls | grep library
```

### 5️⃣ Deploy Application with Sidecar

Change to the **Kubernetes specs directory** and apply the manifests:

```bash
cd specs/kubernetes
kubectl apply -f deployment.yaml
kubectl apply -f request-authentication.yaml
kubectl apply -f authorization-policy.yaml
```

✅ This will:
- Deploy the application with **Istio sidecar**.
- Apply **JWT authentication** via Istio (`RequestAuthentication`).
- Enforce **access control** via Istio (`AuthorizationPolicy`).

### 6️⃣ Verify Deployment

- **Check if the Pods are Running**

```bash
kubectl get pods -n app-dev
```

✅ The **READY column** should show **2/2**, meaning the app and sidecar (Envoy proxy) are running.

- **Test API with a Valid JWT**

```bash
ACCESS_TOKEN="your_valid_token_here"
curl -H "Authorization: Bearer $ACCESS_TOKEN" http://userinfo-api.app-dev.svc.cluster.local/userinfo
```

