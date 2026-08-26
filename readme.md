# Nutri-day

L'application Nutri-day a pour objectif de faire manger plus sainement et de connaître toutes les informations nutritionnelles que l'on consomme.
Des informations sur les calories, les protéines, les glucides, les lipides et les fibres pour chaque produit et chaque journée.

# 👩‍💻 Technologies

| Back - Spring Boot 3.2.5 | version (works) | 
| --- | --- |
| java | 17 |
| maven| 3.9.6 |

| Front - Angular 18 | version (works) | 
| --- | --- |
| npm | 10.5.0 |

| Base de données |  | 
| --- | --- |
| PostgreSQL | 16 |

# ⚡️ Standard Execution

### Run Docker database 

```bash
cd backend
docker-compose up
```
--------------------------
### Run API

```bash
cd backend
mvn spring-boot:run
```

URL of Swagger: http://localhost:8080/swagger-ui/index.html

--------------------------
### Run Frontend

```bash
cd frontend
npm start
```

URL of website: http://localhost:4200/home
