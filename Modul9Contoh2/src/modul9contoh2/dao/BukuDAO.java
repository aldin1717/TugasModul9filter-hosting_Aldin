/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul9contoh2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modul9contoh2.models.Buku;
/**
 *
 * @author MyBook Hype AMD
 */

public class BukuDAO {
    // Variabel static untuk opsi sorting
    public static String sortingOption = "";

    // Method untuk mengambil semua data buku dari database dengan opsi sorting
    public static List<Buku> getAllBuku() {
        List<Buku> bukuList = new ArrayList<>();

        // Query dasar dengan JOIN ke tabel kategori_buku
        String sql = "SELECT buku.*, kategori_buku.nama_kategori FROM buku " +
                     "LEFT JOIN kategori_buku ON buku.kode_kategori = kategori_buku.kode_kategori";

        // Menambahkan klausa ORDER BY jika sortingOption tidak kosong
        if (!sortingOption.isEmpty()) {
            switch (sortingOption) {
                case "Judul A-Z":
                    sql += " ORDER BY buku.judul ASC";
                    break;
                case "Judul Z-A":
                    sql += " ORDER BY buku.judul DESC";
                    break;
                case "Pengadaan Terbaru":
                    sql += " ORDER BY buku.tanggal_pengadaan DESC";
                    break;
                case "Pengadaan Lama":
                    sql += " ORDER BY buku.tanggal_pengadaan ASC";
                    break;
            }
        }

        // Try-with-resources untuk auto-close connection, statement, dan resultset
        try (
            Connection koneksi = DBConnection.getConnection();
            Statement stmt = koneksi.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                String kodeBuku = rs.getString("kode_buku");
                String kodeKategori = rs.getString("kode_kategori");
                String judul = rs.getString("judul");
                String pengarang = rs.getString("pengarang");
                String penerbit = rs.getString("penerbit");
                int tahun = rs.getInt("tahun_terbit");
                int edisi = rs.getInt("edisi");
                String tanggalPengadaan = rs.getString("tanggal_pengadaan");
                String namaKategori = rs.getString("nama_kategori");

                // Menambahkan objek Buku ke dalam list
                bukuList.add(new Buku(
                    kodeBuku, kodeKategori, judul, pengarang, penerbit, tahun, edisi, tanggalPengadaan, namaKategori
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bukuList;
    }
}
