# 🛂 QR Code Enabled Visitor Management System

A full-stack web application that enables visitors to register and generate QR codes for easy check-in and admin verification. Admins can log in, verify QR codes via webcam or image, and track visitor logs.


## 🚀 Features

- Visitor registration with QR code generation  
- Display and download QR code  
- Admin login authentication  
- Admin dashboard with:  
  - Visitor logs  
  - QR scanner (webcam)  
  - QR image upload and verification  
  - Logout  

## 🧰 Tech Stack

- **Frontend**: React, HTML, CSS  
- **Backend**: Java Spring Boot  
- **Database**: MySQL  
- **QR Code**: html5-qrcode (frontend), ZXing (backend)  

## 🛠️ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/qr-visitor-management.git
```

### 2. Start frontend

```bash
cd frontend
npm install
npm start
```

### 3. Start backend

```bash
cd backend
./mvnw spring-boot:run
```

### 4. Access endpoint

```bash
frontend in http://localhost:8080
backend in http://localhost:3000
```
