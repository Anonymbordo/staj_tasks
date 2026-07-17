# Task 5 - Library Management CRUD

Spring Boot ve PostgreSQL ile hazirlanmis kutuphane yonetimi mini uygulamasi.

## Istenen Sinif Gruplari

Her madde icin Entity, Controller, Repository, Service ve ServiceImpl siniflari eklendi:

- User
- Address
- ContactInformation
- Book
- Borrowing

## Teknolojiler

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven

## Veritabani Hazirligi

PostgreSQL calisir durumda olmalidir. Veritabani:

```bash
sudo -u postgres createdb task5_db
```

PostgreSQL sifresi proje ayariyla uyumlu degilse:

```bash
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
```

## Calistirma

```bash
cd /home/ddemu/staj_tasks/task5-library-management
mvn spring-boot:run
```

Uygulama `http://localhost:8081` adresinde calisir.

## Endpointler

- `GET /api/users`
- `POST /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Ayni CRUD yapisi su endpointlerde de vardir:

- `/api/addresses`
- `/api/contact-informations`
- `/api/books`
- `/api/borrowings`

## Postman Ornekleri

### User Ekleme

```http
POST http://localhost:8081/api/users
```

```json
{
  "username": "murathan",
  "password": "123456",
  "email": "murathan@example.com"
}
```

### Address Ekleme

```http
POST http://localhost:8081/api/addresses
```

```json
{
  "street": "Ataturk Caddesi No: 10",
  "city": "Istanbul",
  "district": "Kadikoy",
  "postalCode": "34710",
  "user": {
    "id": 1
  }
}
```

### ContactInformation Ekleme

```http
POST http://localhost:8081/api/contact-informations
```

```json
{
  "phoneNumber": "+905551112233",
  "alternativeEmail": "murathan.alt@example.com",
  "user": {
    "id": 1
  }
}
```

### Book Ekleme

```http
POST http://localhost:8081/api/books
```

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publisher": "Prentice Hall",
  "available": true
}
```

### Borrowing Ekleme

```http
POST http://localhost:8081/api/borrowings
```

```json
{
  "user": {
    "id": 1
  },
  "book": {
    "id": 1
  },
  "borrowDate": "2026-07-17",
  "dueDate": "2026-07-31",
  "status": "BORROWED"
}
```

### Borrowing Iade Guncelleme

```http
PUT http://localhost:8081/api/borrowings/1
```

```json
{
  "user": {
    "id": 1
  },
  "book": {
    "id": 1
  },
  "borrowDate": "2026-07-17",
  "dueDate": "2026-07-31",
  "returnDate": "2026-07-20",
  "status": "RETURNED"
}
```