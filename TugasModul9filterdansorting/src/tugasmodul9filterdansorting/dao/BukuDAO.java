/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasmodul9filterdansorting.dao;

import java.sql.*; 
import java.util.ArrayList; 
import java.util.List;      
import tugasmodul9filterdansorting.models.Buku;
/**
 *
 * @author MyBook Hype AMD
 */

public class BukuDAO {
    public static List<Buku> getFilteredAndSorted(String kategori, String sorting) {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT buku.*, kategori_buku.nama_kategori FROM buku " +
                     "LEFT JOIN kategori_buku ON buku.kode_kategori = kategori_buku.kode_kategori ";

        if (kategori != null && !kategori.equals("Semua")) {
            sql += "WHERE kategori_buku.nama_kategori = ? ";
        }

        if (sorting != null) {
            switch (sorting) {
                case "Judul A-Z": sql += "ORDER BY buku.judul ASC "; break;
                case "Judul Z-A": sql += "ORDER BY buku.judul DESC "; break;
                case "Pengadaan Terbaru": sql += "ORDER BY buku.tanggal_pengadaan DESC "; break;
                case "Pengadaan Lama": sql += "ORDER BY buku.tanggal_pengadaan ASC "; break;
            }
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (kategori != null && !kategori.equals("Semua")) {
                stmt.setString(1, kategori);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Buku(
                    rs.getString("kode_buku"),
                    rs.getString("kode_kategori"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("penerbit"),
                    rs.getInt("tahun_terbit"),
                    rs.getInt("edisi"),
                    rs.getString("tanggal_pengadaan"),
                    rs.getString("nama_kategori")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getAllKategori() {
        List<String> list = new ArrayList<>();
        list.add("Semua");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama_kategori FROM kategori_buku")) {
            while (rs.next()) {
                list.add(rs.getString("nama_kategori"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}