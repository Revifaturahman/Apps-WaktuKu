# 📅 Alarm Activity Reminder

Alarm Activity Reminder adalah aplikasi Android yang membantu mengingatkan jadwal kegiatan harian menggunakan Alarm Receiver.
Keunikan aplikasi ini adalah pengguna dapat merekam audio pengingat sendiri yang akan diputar sebagai suara alarm, serta mendapatkan notifikasi visual.

Aplikasi ini dibuat khusus dengan mempertimbangkan kebutuhan penyandang disabilitas, terutama autisme, agar mereka dapat mengetahui aktivitas harian mereka dengan jelas, yang jadwalnya dapat diatur oleh orang tua, pendamping, atau orang terdekat.

"Setiap suara yang akrab dapat menjadi pelukan hangat,
setiap pengingat adalah jembatan menuju kemandirian."
---

## ✨ Fitur Utama
- 🔔 **Pengingat Otomatis**  
  Jadwalkan aktivitas harian dengan alarm yang aktif di waktu yang ditentukan.
  
- 🎤 **Audio Pengingat Kustom**  
  Rekam suara Anda sendiri (misalnya suara orang terdekat) untuk menjadi suara pengingat.  
  Cocok untuk memberikan pengingat yang lebih personal dan mudah dipahami.

- 📢 **Notifikasi Visual + Audio**  
  Notifikasi muncul di layar disertai pemutaran audio yang telah direkam.

- 📆 **Pengaturan Jadwal Fleksibel**  
  Tambah, ubah, dan hapus jadwal sesuai kebutuhan.

- ♿ **Didesain untuk Aksesibilitas**  
  UI sederhana, teks jelas, dan audio personal membantu pengguna autisme memahami instruksi lebih mudah.

---

```
com.example.waktuku/
│
├── adapter/              # Adapter untuk RecyclerView atau komponen UI lain
├── data/
│   ├── dao/              # Data Access Object untuk mengelola database
│   ├── db/               # Konfigurasi dan instance database (Room/SQLite)
│   └── model/            # Data class (misalnya Jadwal, Alarm, dll.)
├── di/                   # Dependency Injection (misalnya Hilt/Koin)
├── repository/           # Repository untuk mengatur alur data dari sumber lokal/remote
├── utils/                # Kelas utilitas atau helper (misalnya format waktu, konversi)
├── view/                 # Activity / Fragment (UI dan interaksi pengguna)
├── viewmodel/            # ViewModel untuk mengelola data dan lifecycle-aware
└── MyApp.kt              # Class utama aplikasi (extends Application)

```


---

## 🛠️ Teknologi yang Digunakan
- **Android (Java/Kotlin)**
- **AlarmManager & BroadcastReceiver** untuk pemicu alarm
- **MediaRecorder** untuk perekaman audio
- **NotificationManager** untuk notifikasi
- **Room Database** atau SQLite untuk menyimpan jadwal (opsional)

---

## 📖 Cara Menggunakan
1. **Tambahkan Jadwal Baru**  
   Masukkan nama aktivitas dan waktu pengingat.
2. **Rekam Suara Pengingat**  
   Rekam instruksi atau pesan yang ingin diputar saat alarm aktif.
3. **Simpan dan Aktifkan Alarm**  
   Alarm akan berbunyi sesuai jadwal, lengkap dengan notifikasi.
4. **Notifikasi Muncul + Audio Diputar**  
   Pengguna akan mendapatkan pengingat visual dan audio sesuai yang telah diatur.

---

## 🎯 Tujuan Aplikasi
Aplikasi ini dikembangkan untuk:
- Membantu **penyandang autisme** atau individu dengan kebutuhan khusus agar tidak melewatkan aktivitas penting.
- Memberikan rasa aman bagi keluarga/pengasuh karena dapat mengatur jadwal dan pengingat yang lebih personal.
- Menyediakan pengalaman pengingat yang **ramah, mudah diingat, dan tidak membingungkan**.

---

## 📜 Lisensi
Proyek ini bersifat **Open Source** di bawah lisensi [MIT License](LICENSE).
