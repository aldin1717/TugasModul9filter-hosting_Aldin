/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modul9contoh1.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modul9contoh1.dao.BukuDAO;
import modul9contoh1.dao.KategoriBukuDAO;
import modul9contoh1.models.Buku;
import modul9contoh1.models.KategoriBuku;
/**
 *
 * @author MyBook Hype AMD
 */

public class MainController implements Initializable {

    // Deklarasi komponen UI yang di-inject dari FXML
    @FXML private ComboBox<KategoriBuku> cbxKategori;
    @FXML private DatePicker dpcDari;
    @FXML private DatePicker dpcSampai;
    @FXML private TextField txtJudul;
    @FXML private Button btnFilter;

    // Komponen TableView dan kolom-kolomnya
    @FXML private TableView<Buku> tblViewBuku;
    @FXML private TableColumn<Buku, String> colJudul;
    @FXML private TableColumn<Buku, String> colKategori;
    @FXML private TableColumn<Buku, String> colKodeBuku;
    @FXML private TableColumn<Buku, String> colPenerbit;
    @FXML private TableColumn<Buku, String> colPengarang;
    @FXML private TableColumn<Buku, Integer> colTahun;
    @FXML private TableColumn<Buku, Integer> colEdisi;
    @FXML private TableColumn<Buku, String> colPengadaan;

    // ObservableList untuk menyimpan data buku
    private ObservableList<Buku> bukuList;

    /**
     * Method untuk memuat data kategori ke dalam ComboBox
     */
    private void loadDataKategori() {
        KategoriBukuDAO kategoriBukuDAO = new KategoriBukuDAO();
        ObservableList<KategoriBuku> kategoriBukuList = FXCollections.observableArrayList(
            kategoriBukuDAO.getAllKategoriBuku()
        );
        cbxKategori.setItems(kategoriBukuList);
    }

    /**
     * Method untuk mendapatkan kode kategori yang dipilih
     */
    public String getSelectedKodeKategori() {
        KategoriBuku selected = cbxKategori.getSelectionModel().getSelectedItem();
        if (selected != null) {
            return selected.getKodeKategori();
        }
        return null;
    }

    /**
     * Method untuk memuat data buku ke TableView
     */
    private void loadDataBuku() {
        bukuList = FXCollections.observableArrayList(BukuDAO.getAllBuku());
        tblViewBuku.setItems(bukuList);
    }

    /**
     * Handler untuk tombol filter
     */
    @FXML
    private void handleBtnFilter(ActionEvent event) {
        if (bukuList != null) bukuList.clear();

        // Filter judul
        BukuDAO.filterJudul = txtJudul.getText();

        // Filter tanggal
        LocalDate dari = dpcDari.getValue();
        LocalDate sampai = dpcSampai.getValue();
        if (dari != null && sampai != null) {
            BukuDAO.filterTanggalDari = dari.toString();
            BukuDAO.filterTanggalSampai = sampai.toString();
        }

        // Filter kategori
        if (getSelectedKodeKategori() != null) {
            BukuDAO.filterKategori = getSelectedKodeKategori();
        }

        // Muat ulang data buku
        loadDataBuku();
    }

    /**
     * Method untuk inisialisasi TableView dan kolom-kolomnya
     */
    private void initTableViewBuku() {
        colKodeBuku.setCellValueFactory(new PropertyValueFactory<>("kodeBuku"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colPengarang.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        colPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        colTahun.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        colEdisi.setCellValueFactory(new PropertyValueFactory<>("edisi"));
        colPengadaan.setCellValueFactory(new PropertyValueFactory<>("tanggalPengadaan"));
    }

    /**
     * Method initialize yang dijalankan saat controller di-load
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataKategori();
        initTableViewBuku();
        loadDataBuku();
    }
}

