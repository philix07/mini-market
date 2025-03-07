# 🛒 Mini-Market API

🚀 **Mini-Market API** is a robust backend system designed to manage a mini-market’s operations, including **transactions, sales reports, user management, and stock tracking**. Built using **Spring** and **MySQL**.

---

## ✨ Features

✅ **Transaction Management** – Save transactions with detailed records.  
✅ **Sales Reports** – Generate insightful sales reports for business analysis.  
✅ **User & Customer Management** – Secure authentication and role-based access.  
✅ **Activity Log** – Track system activities for better transparency.  
✅ **Stock Entry & Product Integration** – Manage inventory in sync with product data.  

---

## 🖥️ Tech Stack

🔹 **Backend**           : Java (Spring Boot)  
🔹 **Database**          : MySQL  
🔹 **Containerization**  : Docker  
🔹 **Build Tools**       : Maven

---

## 🐳 **Setting Up the Database with Docker**

To run the MySQL database inside a **Docker container**, execute the following command:

```sh
docker run --detach \
  --env MYSQL_ROOT_PASSWORD=dummypassword \    # Root admin password
  --env MYSQL_USER=mini-market-admin \         # Database credentials
  --env MYSQL_PASSWORD=dummypassword \         # Database credentials
  --env MYSQL_DATABASE=mini-market-database \  # Creating a database named mini-market-database
  --name mini-market \                         # Docker container name
  --publish 3306:3306 \                        # Running port
  mysql:8-oracle                               # MySQL image type/version

## 🐳 **Connecting Into The Docker Container**
Prompt this in **MySQL Shell**:
```sh
       connect mini-market-admin@localhost:3306
       'then insert <your_password>'
       use mini-market-database
       'then we can run mysql commands'
```

## 👨‍💻 Author

Developed by **Felix Liando**  
📧 Email: felix.liando07@gmail.com  
🔗 [GitHub](https://github.com/philix07) | [LinkedIn](https://www.linkedin.com/in/felix-liando-324306250/)  
