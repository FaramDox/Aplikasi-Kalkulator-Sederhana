# Aplikasi Kalkulator Sederhana (JavaFX)

Aplikasi desktop kalkulator berbasis Java dan JavaFX dengan arsitektur pemisahan logika bisnis (*business logic*) dan antarmuka pengguna (*user interface*). Proyek ini juga memuat implementasi alternatif menggunakan FXML serta modul form biodata.

---

## 1. Struktur Direktori

```text
Aplikasi-Kalkulator-Sederhana/
├── pom.xml                               # Konfigurasi Maven dependencies & build plugin
├── README.md                             # Dokumentasi proyek
└── src/
    └── main/
        ├── java/
        │   ├── module-info.java          # Deklarasi Java Platform Module System (JPMS)
        │   └── com/
        │       └── trpl/
        │           ├── Kalkulator.java          # Core logic kalkulasi numerik
        │           ├── KalkulatorApp.java       # Main class & GUI murni JavaFX (GridPane)
        │           ├── App.java                 # Varian launcher GUI kalkulator
        │           ├── PrimaryController.java   # Controller untuk tampilan FXML
        │           ├── SecondaryController.java # Controller dummy/cadangan
        │           └── BiodataApp.java          # Aplikasi mandiri form input biodata mahasiswa
        └── resources/
            └── com/
                └── trpl/
                    ├── primary.fxml             # Layout deklaratif UI kalkulator (FXML)
                    └── secondary.fxml           # Layout deklaratif cadangan
```

---

## 2. Penjelasan Detail Setiap File

### A. `src/main/java/com/trpl/Kalkulator.java`
Komponen logika murni (POJO - *Plain Old Java Object*). Beroperasi tanpa dependensi terhadap UI/JavaFX, memudahkan pengujian unit (*unit testing*).

* **State Variables**:
  * `double operand1`: Menyimpan nilai bilangan pertama atau akumulasi hasil perhitungan.
  * `String operator`: Menyimpan operator aritmatika aktif (`+`, `-`, `×`, `÷`).
  * `boolean startNew`: Penanda (*flag*) apakah digit berikutnya harus menimpa display (angka baru) atau ditambahkan di belakang angka saat ini.
* **Metode Utama**:
  * `clear()`: Mengembalikan kalkulator ke kondisi awal (`operand1 = 0`, `operator = ""`, `startNew = true`).
  * `processNumber(String current, String digit)`: Mengelola *input* angka. Mengganti angka `"0"` awal atau memulai bilangan baru bila `startNew == true`; selain itu melakukan konkatenasi string.
  * `processOperator(String current, String op)`: Menangani kalkulasi berantai (*chained operations*). Bila operator sudah ada sebelumnya, hitung hasil kalkulasi sementara terlebih dahulu, simpan ke `operand1`, set operator baru, dan aktifkan `startNew`.
  * `calculate(String current)`: Mengambil operand kedua dari string display, menjalankan *switch-case* sesuai operator:
    * `+` : Penjumlahan
    * `-` : Pengurangan
    * `×` : Perkalian
    * `÷` : Pembagian (termasuk proteksi pembagian dengan nol; mengembalikan `0` bila `operand2 == 0`)
  * **Format Output**: Memeriksa apakah `result == (long) result`. Jika bilangan bulat, desimal `.0` dipotong dengan konversi ke `long`.

---

### B. `src/main/java/com/trpl/KalkulatorApp.java`
Kelas utama (*main application*) berbasis JavaFX murni (UI dibuat programatik tanpa FXML).

* **Komponen Tampilan**:
  * `Label display`: Area penampil angka/hasil dengan latar gelap (`#1e293b`), teks putih, font ukuran 28px rata kanan.
  * `GridPane grid`: Tata letak matriks 4 baris × 4 kolom untuk tombol:
    ```text
    [ 7 ] [ 8 ] [ 9 ] [ ÷ ]
    [ 4 ] [ 5 ] [ 6 ] [ × ]
    [ 1 ] [ 2 ] [ 3 ] [ - ]
    [ C ] [ 0 ] [ = ] [ + ]
    ```
  * `VBox root`: Kontainer vertikal yang menyatukan `display` dan `grid`.
* **Event Handling**:
  * Menggunakan *lambda expression* pada tiap tombol:
    * Regex `[0-9]` → memanggil `kalkulator.processNumber()`.
    * `"C"` → memanggil `kalkulator.clear()`, display kembali `"0"`.
    * `"="` → memanggil `kalkulator.calculate()`.
    * Operator lainnya (`+`, `-`, `×`, `÷`) → memanggil `kalkulator.processOperator()`.
* **Entry Point**: Memanggil `Application.launch(args)` dari `main()`.

---

### C. `src/main/java/com/trpl/App.java`
Varian implementasi kalkulator prosedural murni JavaFX, identik dengan `KalkulatorApp` namun dengan judul window standar `"Kalkulator"`.

---

### D. `src/main/resources/com/trpl/primary.fxml` & `src/main/java/com/trpl/PrimaryController.java`
Implementasi antarmuka deklaratif berbasis XML (FXML) dan Controller terpisah.

* **Fitur Tambahan pada FXML Controller**:
  * Dukungan koma / desimal (`.`): Mencegah pengetikan ganda tanda titik desimal menggunakan validasi `display.getText().contains(".")`.
  * Penanganan pembagian dengan nol: Menampilkan teks `"Error"` jika penyebut adalah `0`.
  * Tombol `+` dan `=` memanfaatkan `rowSpan="2"` pada `GridPane`.
  * Tombol `0` memanfaatkan `columnSpan="2"`.

---

### E. `src/main/java/com/trpl/BiodataApp.java`
Aplikasi mandiri form input data mahasiswa menggunakan komponen JavaFX Form:
* Komponen: `TextField` (Nama, NIM), `ComboBox` (Pilihan Program Studi), `Button` (Simpan), `Label` (Notifikasi status).
* Validasi input: Mengecek apakah nama, nim, atau prodi belum diisi sebelum menampilkan status penyimpanan.

---

### F. `src/main/java/module-info.java`
Konfigurasi modularitas Java 11+:
* `requires javafx.controls`: Dependensi komponen UI dasar (Button, Label, TextField).
* `requires javafx.fxml`: Dependensi parser dan loader file FXML.
* `opens com.trpl to javafx.fxml`: Memberikan izin refleksi bagi FXML loader ke kelas controller.
* `exports com.trpl`: Mengekspos paket `com.trpl` agar dapat diakses runtime JavaFX.

---

### G. `pom.xml`
Konfigurasi Maven untuk build dan eksekusi:
* **Java Version**: Java 11 (`<maven.compiler.source>11</maven.compiler.source>`).
* **Dependencies**:
  * `org.openjfx:javafx-controls:13`
  * `org.openjfx:javafx-fxml:13`
* **Plugins**:
  * `maven-compiler-plugin` (versi 3.8.0, target release 11).
  * `javafx-maven-plugin` (versi 0.0.6) dengan konfigurasi `mainClass`: `com.trpl.KalkulatorApp`.

---

## 3. Alur Kerja Logika (*Execution Flow*)

```text
[Input Pengguna]
       │
       ├─ Angka (0-9) ───► processNumber() ───► Display diperbarui
       │
       ├─ Operator (+,-,×,÷) ──► processOperator()
       │                                │
       │                                ├─ Jika ada operasi pending ──► calculate()
       │                                └─ Simpan operand1 & operator baru
       │
       ├─ Samadengan (=) ──────► calculate() ───────► Hitung hasil ──► Format integer/desimal
       │
       └─ Clear (C) ───────────► clear() ───────────► Reset memori ──► Display = "0"
```

---

## 4. Cara Menjalankan Proyek

### Prasyarat
* JDK 11 atau lebih baru terpasang di sistem.
* Apache Maven terpasang.

### Perintah Eksekusi

Jalankan kalkulator utama (`com.trpl.KalkulatorApp`):
```bash
mvn clean javafx:run
```

Jalankan class lain (contoh: `BiodataApp`):
```bash
mvn clean javafx:run -Djavafx.mainClass=com.trpl.BiodataApp
```
