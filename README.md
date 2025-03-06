# HelpDesk Ticket Management App 📞💻  

## Overview  
HelpDesk App is a modern ticketing system for customer support, built with a **React** frontend and a **Spring Boot** backend. It create, track, and manage support requests efficiently.  

## Tech Stack  
### 🔹 Frontend  
- **Vite** (for fast build & development)  
- **React** (UI framework)  
- **Tailwind CSS** (for styling)  

### 🔹 Backend  
- **Spring Boot** (REST API)   
- **JPA/Hibernate** (database access)  

### 🔹 API Documentation  
- **Swagger** (for API documentation)  

---

## 🐳 Containerization (Docker)  
This project supports **Docker** for easy deployment.  

### 1️⃣ Build & Run with Docker  
#### Backend (Spring Boot)  
```sh  
docker build -t helpdesk-ticket ./ticket-service  
docker run -p 8080:8080 helpdesk-backend  
```

#### Frontend (React)  
```sh  
docker build -t helpdesk-frontend ./helpdesk-management-web  
docker run -p 5173:5173 helpdesk-frontend  
```

### 2️⃣ Using Docker Compose  
To run both frontend and backend together:
```sh  
docker-compose up --build  
```

---

## 📂 Project Structure  
### Frontend (`/helpdesk-management-web`)  
```
/src
 ├── components/        # Reusable UI components
 ├── pages/             # Page components
 ├── services/          # API calls
 ├── App.tsx            # Main App component
 ├── main.tsx           # Entry point
```

### Backend (`/ticket-service`)  
```
/src/main/java/com/phu/ticket-service
 ├── ticket             # Ticket -> Controller, Service, Repository, Model
 ├── handler/           # Exception Handler
 ├── HelpdeskApplication.java # Main Spring Boot class
```

---

## 🚀 Getting Started  

### 1️⃣ Clone the Repository  
```sh  
git clone https://github.com/phu-phurithat/helpdesk-ticket-management-system.git  
cd helpdesk-ticket-management-system  
```

### 2️⃣ Start the Backend (Spring Boot)  
> **Prerequisite:** Java 17+ & Maven installed  

```sh  
cd ticket-service  
mvn spring-boot:run  
```
- The backend will start on **`http://localhost:8080`**  
- Swagger API Docs: **`http://localhost:8080/swagger-ui/index.html`**  

### 3️⃣ Start the Frontend (React)  
> **Prerequisite:** Node.js 18+ & npm/yarn installed  

```sh  
cd helpdesk-management-web  
npm install   # or yarn install  
npm run dev   # or yarn dev  
```
- The frontend will run on **`http://localhost:5173`**  

---

## 🔗 API Documentation  
The API is fully documented using **Swagger**.  

- View API Docs: [Swagger UI](http://localhost:8080/swagger-ui/index.html)  
- API Spec (JSON): [OpenAPI Spec](http://localhost:8080/v3/api-docs)  

---

## 📌 Features  
✅ Drag and Drop + Table view  

✅ Ticket Creation & Management  

✅ RESTful API with Swagger Docs  


