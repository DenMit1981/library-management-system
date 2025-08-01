1.Name of project: library-management-system

2.Port of the project:
http://localhost:8081

3.Project launch:
3.1 Using Docker
3.1.1 Build and run docker-compose with logs:
docker compose up --build
Build and run docker-compose without logs in detached mode:
docker compose up -d --build
Run docker-compose with logs:
docker-compose up
3.1.2 Stop and remove services:
docker-compose down

3.2 Using Kubernetes
Requirements:
 - Docker Desktop (with Kubernetes enabled)
 - kubectl installed and configured
 - YAML configuration files inside the k8s/ folder
3.2.1 Build Docker Image:
docker build -t library-management-system:latest .
3.2.3 Deploy Database and application:
kubectl apply -f k8s/librarydb.yaml 
kubectl apply -f k8s/libraryapp.yaml
3.2.4 Check Pods and Services:
kubectl get pods
3.2.5 Check service ports and get NodePort (for example, 31934)
kubectl get svc
3.2.6 Access your app in the browser:
http://localhost:{NodePort}/library-management-system/swagger-ui/index.html
or Postman:
http://localhost:{NodePort}/api/v1/books
3.2.7 Stop apps in k8s/ folder:
kubectl delete -f k8s/

3.3 Build maven project:
mvn clean install

4. CI/CD Pipeline with GitHub Actions & Kubernetes (using self-hosted runner)
4.1. Prerequisites:
 - A self-hosted runner installed and active on your local machine or server
 - Docker installed and running
 - A local Kubernetes cluster (e.g., Minikube or Docker Desktop)
 - A kubeconfig file available locally
 - A Docker Hub account
 - GitHub repository secrets configured
 
4.2 Required GitHub Secrets:
- In your GitHub repository, go to Settings → Secrets → Actions, and add the following:
 Secret Name	Description
DOCKER_HUB_USERNAME	Your Docker Hub username
DOCKER_HUB_TOKEN	Your Docker Hub access token (or password)
KUBE_CONFIG_DATA	Base64-encoded contents of your local kubeconfig file (~/.kube/config)

4.3 How to Generate KUBE_CONFIG_DATA (Base64-encoded kubeconfig):
- Run this command in PowerShell:
[Convert]::ToBase64String([IO.File]::ReadAllBytes("$env:USERPROFILE\.kube\config"))
- Copy the output and save it as the value for the secret KUBE_CONFIG_DATA.

4.4. Self-Hosted Runner Setup on GitHub
- In your GitHub repository, go to Settings → Actions → Runners → Add runner.
- Choose your OS (e.g., Windows).
- Follow the commands provided by GitHub:
# Create a directory for the runner
mkdir actions-runner
cd actions-runner
# Download the latest runner package (example version)
Invoke-WebRequest -Uri https://github.com/actions/runner/releases/download/v2.327.1/actions-runner-win-x64-2.327.1.zip -OutFile actions-runner-win-x64-2.327.1.zip
# Extract the package
Add-Type -AssemblyName System.IO.Compression.FileSystem
[System.IO.Compression.ZipFile]::ExtractToDirectory("$PWD/actions-runner-win-x64-2.327.1.zip", "$PWD")
# Configure the runner (replace URL and TOKEN with your repo's URL and token)
.\config.cmd --url https://github.com/your-username/library-management-system --token YOUR_RUNNER_TOKEN

4.5 Running the Self-Hosted Runner
- Before pushing code to trigger GitHub Actions, start the runner manually (keep this terminal open):
cd path\to\actions-runner
.\run.cmd

4.6. How to Trigger the GitHub Action
- Make a code change (e.g., update Java code or Kubernetes YAML config)
- Commit and push changes to the main branch:
git add .
git commit -m "Update app logic"
git push origin main
- GitHub Actions will automatically start the deployment workflow.

4.7. Monitoring the Deployment
- Go to your GitHub repository → Actions tab
- Find the running or completed workflow for your push
- Check logs and status for build, docker push, and Kubernetes deployment

4.8. Access the Application
- After the deployment finishes:
- Check if your pods are running:
kubectl get pods
- Check the services and get the NodePort:
kubectl get svc
- Open your browser or Postman and go to:
http://localhost:<NodePort>/library-management-system/api/books
Replace <NodePort> with the actual port listed under your service (e.g., 32079).

4.9 Useful PowerShell Commands Summary
# Clone repo (if not done)
git clone https://github.com/DenMit1981/library-management-system.git
cd library-management-system

# Start the self-hosted runner (in separate terminal)
cd 'C:\*\*\actions-runner'
.\run.cmd

# Commit and push code to trigger pipeline
git add .
git commit -m "Your commit message"
git push origin main

# Check Kubernetes pods and services
kubectl get pods
kubectl get svc

5.How to Stop the App
5.1. Delete Deployments
- This will stop and remove the Pods for your Spring Boot app and PostgreSQL database:
kubectl delete deployment libraryapp
kubectl delete deployment librarydb
5.2. Delete Services
- This will remove the exposed service endpoints (NodePort и ClusterIP):
kubectl delete service libraryapp-service
kubectl delete service librarydb
5.3. Verify Everything is Deleted
- After a few seconds, check that everything has been cleaned up:
kubectl get all

6.Names/emails of users in database:
1. Ivan Petrov / petrov@mail.com 
2. Olga Sidorova / sidorova@mail.com 
3. Wei Zhang / zhang@mail.com 
4. Liang Tan / tan@mail.com 
5. Xiu Ying / ying@mail.com 
6. Ahmad Faizal / faizal@mail.com
7. Nur Aisyah / aisyah@mail.com

7.Configuration: resources/application.yaml
8.Documentation: resources/static/openapi.yaml
9.Swagger: http://localhost:8081/library-management-system/swagger-ui/index.html#

10.Database PostgreSQL connection:
Name: librarydb
User: test
Password: test
Port: 5432

11.Rest controllers:
UserController:
register(POST): http://localhost:8081/api/v1/users/register + body;

BookController:
register(POST): http://localhost:8081/api/v1/books/register + body
getAll(GET): http://localhost:8081/api/v1/books?page=0&size=10&sortBy=title&sortDir=asc
borrowBook(POST): http://localhost:8081/api/v1/books/{bookId}/borrow?borrowerId={borrowerId}
returnBook(POST): http://localhost:8081/api/v1/books/{bookId}/return?borrowerId={borrowerId}

12.Tests:
BookControllerTest
CardServiceImplTest