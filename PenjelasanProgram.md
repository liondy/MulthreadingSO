# MulthreadingSO
Aplikasi ini merupakan sebuah aplikasi untuk menangani aplikasi (GUI), yang memanfaatkan multithreading dan menangani persoalan critical region dengan salah satu teknik mutex

Spesifikasi program (JAVA) : 

1. menerima input dari keyboard

2. input yang diketik dapat menampilkan jumlah karakter, jumlah word, jumlah kalimat berbarengan saat diinput. 

3. jumlahnya dapat berubah (bertambah atau berkurang) ketika mengetik atau menghapus.

# Class Editor (Main) : 

start() --> untuk menjalankan aplikasi

setTitle() --> menambahkan judul aplikasi

addImage() --> menampilkan logo Informatika Unpar

# Class FXMLDocumentController (Kode Program Aplikasi) : 

typed() --> untuk menghitung jumlah karakter, jumlah word, jumlah kalimat dengan menggunakan Thread.
Method ini menggunakan synchronized() untuk menangani terjadinya race condition

close() --> keluar aplikasi saat meng-klik Close pada Menu File

delete() --> mereset aplikasi saat meng-klik Delete pada Menu Edit

autosave() --> menjalankan autosave setiap 5 detik dan menginputnya ke dalam file autotext.txt

initialize() --> setting default saat aplikasi dijalankan.

save() --> menyimpan file saat meng-klik Save pada Menu File

saveFile() --> mengalokasikan file yang sudah tersimpan pada komputer

open() --> membuka file yang dipilih user

loadFile() --> mencari lokasi file yang dipilih user

readFile() --> mengganti textArea dengan file yang dibuka oleh user.
