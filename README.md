# 🔐 JWT Token Dependency

A lightweight Java library for creating, signing, and validating JSON Web Tokens (JWT).  
Designed as a reusable dependency for backend services that require authentication and secure token handling.

<br>

## 🧰 Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## 📌 Overview

This module provides:

- 🔑 JWT token generation  
- 🔍 Token validation and signature verification  
- 🕒 Support for expiration times  
- 🧪 Small and dependency-friendly design  
- 🔄 Easy integration in Javalin, Spring, Ktor, or any backend service  

Perfect for microservices, standalone Java apps, or students learning authentication mechanics.

---

## 🚀 Installation

### Maven

```xml
<dependency>
    <groupId>com.yourname</groupId>
    <artifactId>jwt-token-dependency</artifactId>
    <version>1.0.0</version>
</dependency>
````

*(Replace groupId/artifactId/version with your actual values.)*

### Gradle

```gradle
implementation 'com.yourname:jwt-token-dependency:1.0.0'
```

---

## ✨ Usage

### ▶️ Create a Token

```java
JwtService jwt = new JwtService("super-secret-key", 30 * 60 * 1000);

String token = jwt.createToken("user123");
System.out.println(token);
```

### ▶️ Validate a Token

```java
JwtService jwt = new JwtService("super-secret-key", 30 * 60 * 1000);

boolean isValid = jwt.validateToken(token);

if (isValid) {
    String userId = jwt.getSubject(token);
    System.out.println("Authenticated user: " + userId);
}
```

---

## 🎛️ Configuration Options

| Setting               | Description                         |
| --------------------- | ----------------------------------- |
| **SECRET_KEY**        | The signing key used to sign tokens |
| **TOKEN_EXPIRE_TIME** | Expiration time in milliseconds     |
| **ISSUER**            | (Optional) token issuer identity    |

---

## 🧱 Architecture Diagram (ASCII)

```
               +------------------------+
               |     Your Backend       |
               |  (Javalin / Spring)    |
               +-----------+------------+
                           |
                           | uses
                           v
               +------------------------+
               |  JWT Token Dependency  |
               |  - createToken()       |
               |  - validateToken()     |
               |  - getSubject()        |
               +-----------+------------+
                           |
                           | generates / validates
                           v
                  +-----------------+
                  |     JWT Token   |
                  |   (signed JWT)  |
                  +-----------------+
```

---

## 🧪 Testing

If the project includes tests:

```bash
mvn test
```

---

## 📦 Packaging

To publish locally:

```bash
mvn clean install
```

To publish to Maven Central or a private repository, add your `distributionManagement` settings.

---

## 📜 License

MIT License (or whatever license you choose).

---

## 🙌 Contributing

Pull requests are welcome.
Feel free to open issues for bug reports or feature requests.
