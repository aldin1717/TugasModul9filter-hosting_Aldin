/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasmodul9filterdansorting.dao;


import tugasmodul9filterdansorting.models.Buku;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author MyBook Hype AMD
 */


public class BukuDAO {
    public static List<Buku> getFilteredSortedByDateRange(String kategori, String sorting, LocalDate fromDate, LocalDate toDate) {
        List<Buku> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT buku.*, kategori_buku.nama_kategori FROM buku " +
            "LEFT JOIN kategori_buku ON buku.kode_kategori = kategori_buku.kode_kategori "
        );

        List<Object> params = new ArrayList<>();
        boolean whereAdded = false;

        if (kategori != null && !kategori.equals("Semua")) {
            sql.append("WHERE kategori_buku.nama_kategori = ? ");
            params.add(kategori);
            whereAdded = true;
        }

        if (fromDate != null && toDate != null) {
            sql.append(whereAdded ? "AND " : "WHERE ");
            sql.append("tanggal_pengadaan BETWEEN ? AND ? ");
            params.add(Date.valueOf(fromDate));
            params.add(Date.valueOf(toDate));
        } else if (fromDate != null) {
            sql.append(whereAdded ? "AND " : "WHERE ");
            sql.append("tanggal_pengadaan >= ? ");
            params.add(Date.valueOf(fromDate));
        } else if (toDate != null) {
            sql.append(whereAdded ? "AND " : "WHERE ");
            sql.append("tanggal_pengadaan <= ? ");
            params.add(Date.valueOf(toDate));
        }

        if (sorting != null) {
            switch (sorting) {
                case "Judul A-Z": sql.append("ORDER BY buku.judul ASC "); break;
                case "Judul Z-A": sql.append("ORDER BY buku.judul DESC "); break;
                case "Pengadaan Terbaru": sql.append("ORDER BY tanggal_pengadaan DESC "); break;
                case "Pengadaan Lama": sql.append("ORDER BY tanggal_pengadaan ASC "); break;
            }
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) stmt.setString(i + 1, (String) p);
                if (p instanceof Date) stmt.setDate(i + 1, (Date) p);
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