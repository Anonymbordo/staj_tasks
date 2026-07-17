# Task 2 - Sayi Tahmin Etme Oyunu

Java ile yazilmis komut satiri sayi tahmin oyunu ve iki modlu modern web oyun arayuzu.

## Komut Satiri Ozellikleri

- Rastgele sayi uretimi
- 1-100 veya 1-1000 araligi secimi
- Sinirli deneme hakki
- Sinirli sure
- Tahmin gercek sayidan buyukse "Cok Yuksek" uyarisi
- Tahmin gercek sayidan kucukse "Cok Dusuk" uyarisi
- Oyun kaybedilirse gercek sayiyi gosterme
- Hatali giris kontrolu

## Web Arayuzu Modlari

### 1. Sayi Tahmini

- Gorsel tahmin ekrani
- 1-100 ve 1-1000 araligi
- Sure ve hak takibi
- Cok Yuksek / Cok Dusuk geri bildirimi
- Skor hesaplama

### 2. Matematik Parkuru

- Dogru cevaplarla ilerleyen parkur oyunu
- Toplama, cikarma, carpma ve bolme sorulari
- Her dogru cevapta karakter ilerler
- Yanlis cevapta sure cezasi
- Parkur bitirme skoru

## Komut Satiri Calistirma

```bash
javac -d out src/main/java/com/murathan/task2/Main.java
java -cp out com.murathan.task2.Main
```

## Web Arayuzu Calistirma

`index.html` dosyasini tarayicida acabilirsiniz.

PowerShell uzerinden dosya konumu:

```powershell
\\wsl.localhost\Ubuntu-22.04\home\ddemu\staj_tasks\task2-number-guessing-game\index.html
```
