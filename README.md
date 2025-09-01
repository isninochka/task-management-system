📘 Task Management System

🇩🇪 Deutsch | 🇬🇧 English | 🇷🇺 Русский

🇩🇪 Deutsch
📖 Projektbeschreibung

Das Task Management System ist eine Microservice-Anwendung zum Verwalten von Benutzern, Aufgaben und Benachrichtigungen.
Die Architektur basiert auf Spring Boot und Docker mit folgenden Kernkomponenten:

API Gateway – Einstiegspunkt, JWT-Authentifizierung & Routing

User Service – Verwaltung von Benutzern & Authentifizierung

Task Service – Verwaltung von Aufgaben und deren Historie

Notification Service – Senden von Benachrichtigungen

PostgreSQL – separate Datenbanken für User und Task

⚙️ Technologien

Java 21

Spring Boot 3 (Web, Security, Data JPA)

PostgreSQL 15

Docker + Docker Compose

Spring Cloud Gateway (JWT-Auth)

MapStruct (DTO-Mapper)

JUnit 5, Mockito

🚀 Projekt starten
docker-compose up --build


Dienste nach dem Start verfügbar:

Dienst	Port	Beschreibung
API Gateway	8080	Einstiegspunkt für alle Anfragen
User Service	8081	Benutzerverwaltung
Task Service	8082	Aufgabenverwaltung
Notification Service	8083	Benachrichtigungen (ohne DB)
PostgreSQL	5432	Datenbanken: user_db, task_db

📌 Beispielanfragen

👤 Benutzer registrieren
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "anna",
  "email": "anna@example.com",
  "password": "secret"
}

✅ Anmeldung
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "anna@example.com",
  "password": "secret"
}

📋 Aufgabe erstellen
POST http://localhost:8080/api/tasks
Content-Type: application/json
X-User-Id: 1

{
  "title": "Bericht schreiben",
  "description": "Bericht bis Montag vorbereiten"
}

📖 Aufgaben abrufen
GET http://localhost:8080/api/tasks
X-User-Id: 1

🇬🇧 English
📖 Project Description

The Task Management System is a microservice-based application for managing users, tasks, and notifications.
The system is built with Spring Boot and Docker, and includes the following services:

API Gateway – entry point, JWT authentication & routing

User Service – user management & authentication

Task Service – task management with history tracking

Notification Service – notification sending

PostgreSQL – separate databases for user and task services

⚙️ Technologies

Java 21

Spring Boot 3 (Web, Security, Data JPA)

PostgreSQL 15

Docker + Docker Compose

Spring Cloud Gateway (JWT-Auth)

MapStruct (DTO Mapper)

JUnit 5, Mockito

🚀 Run project
docker-compose up --build


Services available after startup:

Service	Port	Description
API Gateway	8080	Entry point for all requests
User Service	8081	User management
Task Service	8082	Task management
Notification Service	8083	Notifications (no DB)
PostgreSQL	5432	Databases: user_db, task_db
📌 Example requests
👤 Register User
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "anna",
  "email": "anna@example.com",
  "password": "secret"
}

✅ Login
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "anna@example.com",
  "password": "secret"
}

📋 Create Task
POST http://localhost:8080/api/tasks
Content-Type: application/json
X-User-Id: 1

{
  "title": "Write report",
  "description": "Prepare report for Monday"
}

📖 Get Tasks
GET http://localhost:8080/api/tasks
X-User-Id: 1

🇷🇺 Русский
📖 Описание проекта

Task Management System — это микросервисное приложение для управления пользователями, задачами и уведомлениями.
Проект реализован на базе Spring Boot и Docker. Основные сервисы:

API Gateway – точка входа, JWT-аутентификация и маршрутизация

User Service – управление пользователями и авторизация

Task Service – управление задачами и историей изменений

Notification Service – отправка уведомлений

PostgreSQL – отдельные базы данных для пользователей и задач

⚙️ Технологии

Java 21

Spring Boot 3 (Web, Security, Data JPA)

PostgreSQL 15

Docker + Docker Compose

Spring Cloud Gateway (JWT-аутентификация)

MapStruct (маппинг DTO)

JUnit 5, Mockito

🚀 Запуск проекта
docker-compose up --build


Сервисы будут доступны:

Сервис	Порт	Описание
API Gateway	8080	Входная точка, JWT
User Service	8081	Пользователи, аутентификация
Task Service	8082	Задачи и история
Notification Service	8083	Уведомления
PostgreSQL	5432	user_db, task_db

✅ Примеры запросов

👤 Пользователь
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "username": "anna",
  "email": "anna@example.com",
  "password": "secret"
}

✅ Авторизация
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "anna@example.com",
  "password": "secret"
}

📋 Создание задачи
POST http://localhost:8080/api/tasks
Content-Type: application/json
X-User-Id: 1

{
  "title": "Сделать отчёт",
  "description": "Подготовить отчёт к понедельнику"
}

📖 Получение задач
GET http://localhost:8080/api/tasks
X-User-Id: 1


