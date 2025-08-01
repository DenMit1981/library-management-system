1.Name of project: library-management-system

2.Project launch:
2.1 Using Docker
2.1.1 Build and run docker-compose with logs:
docker compose up --build
Build and run docker-compose without logs in detached mode:
docker compose up -d --build
Run docker-compose with logs:
docker-compose up
2.1.2 Stop and remove services:
docker-compose down

2.2 Using Kubernetes
Requirements:
 - Docker Desktop (with Kubernetes enabled)
 - kubectl installed and configured
 - YAML configuration files inside the k8s/ folder
2.2.1 Build Docker Image:
docker build -t library-management-system:latest .
2.2.3 Deploy Database and application:
kubectl apply -f k8s/librarydb.yaml 
kubectl apply -f k8s/libraryapp.yaml
2.2.4 Check Pods and Services:
kubectl get pods
2.2.5 Check service ports and get NodePort (for example, 31934)
kubectl get svc
2.2.6 Access your app in the browser:
http://localhost:{NodePort}/library-management-system/swagger-ui/index.html
or Postman:
http://localhost:{NodePort}/api/v1/books
2.2.7 Stop apps in k8s/ folder:
kubectl delete -f k8s/

2.2 Build maven project:
mvn clean install

3.Port of the project:
http://localhost:8081

4.Names/emails of users in database:
1. Ivan Petrov / petrov@mail.com 
2. Olga Sidorova / sidorova@mail.com 
3. Wei Zhang / zhang@mail.com 
4. Liang Tan / tan@mail.com 
5. Xiu Ying / ying@mail.com 
6. Ahmad Faizal / faizal@mail.com
7. Nur Aisyah / aisyah@mail.com

5.Configuration: resources/application.yaml
6.Documentation: resources/static/openapi.yaml
7.Swagger: http://localhost:8081/library-management-system/swagger-ui/index.html#

8.Database PostgreSQL connection:
Name: librarydb
User: test
Password: test
Port: 5432

9.Rest controllers:
UserController:
register(POST): http://localhost:8081/api/v1/users/register + body;

BookController:
register(POST): http://localhost:8081/api/v1/books/register + body
getAll(GET): http://localhost:8081/api/v1/books?page=0&size=10&sortBy=title&sortDir=asc
borrowBook(POST): http://localhost:8081/api/v1/books/{bookId}/borrow?borrowerId={borrowerId}
returnBook(POST): http://localhost:8081/api/v1/books/{bookId}/return?borrowerId={borrowerId}

10.Tests:
BookControllerTest
CardServiceImplTest