/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul9contoh1.dao;

import java.sql.*; 
import java.util.ArrayList; 
import java.util.List;      
import modul9contoh1.models.Buku;
/**
 *
 * @author MyBook Hype AMD
 */

public class BukuDAO {
    // Variabel static untuk filter pencarian
    public static String filterTanggalDari;     // Filter tanggal mulai pengadaan
    public static String filterTanggalSampai;   // Filter tanggal akhir pengadaan
    public static String filterJudul;           // Filter judul buku (partial match)
    public static String filterKategori;        // Filter kategori buku

    public static List<Buku> getAllBuku() {
        // Membuat list kosong untuk menampung hasil
        List<Buku> bukuList = new ArrayList<>();

        // Query dasar dengan JOIN ke tabel kategori
        String sql = "SELECT buku.*, kategori_buku.nama_kategori FROM buku " +
                     "LEFT JOIN kategori_buku ON buku.kode_kategori = kategori_buku.kode_kategori " +
                     "WHERE 1=1 ";

        // Menambahkan filter tanggal jika ada
        if (filterTanggalDari != null && !filterTanggalDari.isEmpty() &&
            filterTanggalSampai != null && !filterTanggalSampai.isEmpty()) {
            sql += "AND buku.tanggal_pengadaan BETWEEN '" + filterTanggalDari + "' AND '" + filterTanggalSampai + "' ";
        }

        // Menambahkan filter judul jika ada
        if (filterJudul != null && !filterJudul.isEmpty()) {
            sql += "AND buku.judul LIKE '%" + filterJudul + "%' ";
        }

        // Menambahkan filter kategori jika ada
        if (filterKategori != null && !filterKategori.isEmpty()) {
            sql += "AND buku.kode_kategori = '" + filterKategori + "' ";
        }

        try (
            // Mendapatkan koneksi database
            Connection koneksi = DBConnection.getConnection();
            // Membuat statement
            Statement stmt = koneksi.createStatement();
            // Mengeksekusi query
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Mengolah hasil query
            while (rs.next()) {
                // Mengambil nilai dari setiap kolom
                String kodeBuku = rs.getString("kode_buku");
                String kodeKategori = rs.getString("kode_kategori");
                String judul = rs.getString("judul");
                String pengarang = rs.getString("pengarang");
                String penerbit = rs.getString("penerbit");
                int tahun = rs.getInt("tahun_terbit");
                int edisi = rs.getInt("edisi");
                String tanggalPengadaan = rs.getString("tanggal_pengadaan");
                String namaKategori = rs.getString("nama_kategori");

                // Membuat objek Buku dan menambahkannya ke list
                bukuList.add(new Buku(
                    kodeBuku, kodeKategori, judul, pengarang, penerbit, tahun, edisi, tanggalPengadaan, namaKategori
                ));
            }
        } catch (Exception e) {
            // Menangani error dengan mencetak stack trace
            e.printStackTrace();
        }

        return bukuList;
    }
}

