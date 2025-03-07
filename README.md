# 🛒 Mini-Market Java Application

This is a **Java-based mini-market management system**. The application uses **MySQL** as the database and can be initialized using **Docker**.

## 🚀 Getting Started

### **1️⃣ Prerequisites**
Before running the project, ensure you have:
- **Java 17+**
- **Docker**
- **MySQL Shell (`mysqlsh`)**
- **Maven** or **Gradle** (depending on the build tool used)

---

## 🐳 **Initializing Docker Container**
To start the MySQL database using **Docker**, run the following command:

```sh
docker run --detach \
  --env MYSQL_ROOT_PASSWORD=dummypassword \    # Root admin password
  --env MYSQL_USER=mini-market-admin \         # Database credentials
  --env MYSQL_PASSWORD=dummypassword \         # Database credentials
  --env MYSQL_DATABASE=mini-market-database \  # Creating a database named mini-market-database
  --name mini-market \                         # Docker container name
  --publish 3306:3306 \                        # Running port
  mysql:8-oracle                               # MySQL image type/version
```

## 🐳 **Connecting Into The Docker Container**
Prompt this in **MySQL Shell**:
```sh
       connect mini-market-admin@localhost:3306
       'then insert <your_password>'
       use mini-market-database
       'then we can run mysql commands'
```
