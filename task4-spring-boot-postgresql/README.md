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

## PostgreSQL Kurulumu ve Hazirlik

WSL Ubuntu uzerinde PostgreSQL kurulu degilse:

```bash
sudo apt-get update
sudo apt-get install -y postgresql postgresql-contrib
```

PostgreSQL servisini baslatin:

```bash
sudo service postgresql start
```

`postgres` kullanicisinin sifresini proje ayarina uygun olacak sekilde `postgres` yapin:

```bash
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
```

Veritabanini olusturun:

```bash
sudo -u postgres createdb task4_db
```

PowerShell uzerinden calistirmak isterseniz:

```powershell
wsl -e sudo service postgresql start
wsl -e sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
wsl -e sudo -u postgres createdb task4_db
```

Veritabani listesini kontrol etmek icin:

```bash
sudo -u postgres psql -l
```

## Veritabani Ayari

Varsayilan baglanti bilgileri `src/main/resources/application.properties` dosyasindadir:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task4_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Kendi PostgreSQL kullanici adi/sifrenize gore degistirebilirsiniz.

## Calistirma

WSL terminalinde:

```bash
cd /home/ddemu/staj_tasks/task4-spring-boot-postgresql
mvn spring-boot:run
```

PowerShell uzerinden:

```powershell
wsl bash -lc "cd /home/ddemu/staj_tasks/task4-spring-boot-postgresql && mvn spring-boot:run"
```

Uygulama varsayilan olarak `http://localhost:8080` adresinde calisir. Terminalde `Started Task4Application` gorunurse uygulama basariyla calismistir.

## Postman ile Test

### Kullanici Ekleme

Method: `POST`

URL:

```http
http://localhost:8080/api/users
```

Body > raw > JSON:

```json
{
  "username": "murathan",
  "password": "123456",
  "email": "murathan@example.com"
}
```

### Tum Kullanicilari Listeleme

Method: `GET`

```http
http://localhost:8080/api/users
```

### ID ile Kullanici Getirme

Method: `GET`

```http
http://localhost:8080/api/users/1
```

### Kullanici Guncelleme

Method: `PUT`

```http
http://localhost:8080/api/users/1
```

Body > raw > JSON:

```json
{
  "username": "murathan_updated",
  "password": "newpass123",
  "email": "murathan.updated@example.com"
}
```

### Kullanici Silme

Method: `DELETE`

```http
http://localhost:8080/api/users/1
```

Basarili silme isleminde `204 No Content` doner.

## PowerShell ile Hizli Test

Postman kurulu degilse kullanici eklemek icin:

```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8080/api/users" `
  -ContentType "application/json" `
  -Body '{"username":"murathan","password":"123456","email":"murathan@example.com"}'
```

Listelemek icin:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/users"
```
