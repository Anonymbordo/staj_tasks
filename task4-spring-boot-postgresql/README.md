# Task 4 - Spring Boot PostgreSQL Kullanici CRUD

Spring Boot ve PostgreSQL kullanarak hazirlanmis mini kullanici CRUD uygulamasi.

## Teknolojiler

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Jakarta Validation
- Maven

## Veritabani Ayari

PostgreSQL tarafinda once veritabani olusturun:

```sql
CREATE DATABASE task4_db;
```

Varsayilan baglanti bilgileri `src/main/resources/application.properties` dosyasindadir:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task4_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Kendi PostgreSQL kullanici adi/sifrenize gore degistirebilirsiniz.

## Calistirma

```bash
mvn spring-boot:run
```

Uygulama varsayilan olarak `http://localhost:8080` adresinde calisir.

## API Endpointleri

### Kullanici Ekleme

```http
POST http://localhost:8080/api/users
Content-Type: application/json
```

```json
{
  "username": "murathan",
  "password": "123456",
  "email": "murathan@example.com"
}
```

### Tum Kullanicilari Listeleme

```http
GET http://localhost:8080/api/users
```

### ID ile Kullanici Getirme

```http
GET http://localhost:8080/api/users/1
```

### Kullanici Guncelleme

```http
PUT http://localhost:8080/api/users/1
Content-Type: application/json
```

```json
{
  "username": "murathan_updated",
  "password": "newpass123",
  "email": "murathan.updated@example.com"
}
```

### Kullanici Silme

```http
DELETE http://localhost:8080/api/users/1
```

Basarili silme isleminde `204 No Content` doner.
