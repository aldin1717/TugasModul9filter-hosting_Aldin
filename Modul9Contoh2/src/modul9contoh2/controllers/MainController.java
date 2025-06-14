/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modul9contoh2.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modul9contoh2.dao.BukuDAO;
import modul9contoh2.models.Buku;
/**
 *
 * @author MyBook Hype AMD
 */

public class MainController implements Initializable {

    // Deklarasi komponen UI dari FXML
    @FXML private ComboBox<String> cbxSorting;
    @FXML private Button btnSorting;

    @FXML private TableView<Buku> tblViewBuku;
    @FXML private TableColumn<Buku, String> colKodeBuku;
    @FXML private TableColumn<Buku, String> colKategori;
    @FXML private TableColumn<Buku, String> colJudul;
    @FXML private TableColumn<Buku, String> colPengarang;
    @FXML private TableColumn<Buku, String> colPenerbit;
    @FXML private TableColumn<Buku, Integer> colTahun;
    @FXML private TableColumn<Buku, Integer> colEdisi;
    @FXML private TableColumn<Buku, String> colPengadaan;

    // List untuk menyimpan data buku
    private ObservableList<Buku> bukuList;

    // Inisialisasi saat aplikasi dijalankan
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboboxSorting();
        initTableViewBuku();
        loadTableBuku();
    }

    // Mengisi ComboBox dengan opsi sorting
    private void initComboboxSorting() {
        cbxSorting.setItems(FXCollections.observableArrayList(
            "Judul A-Z",
            "Judul Z-A",
            "Pengadaan Terbaru",
            "Pengadaan Lama"
        ));
        cbxSorting.getSelectionModel().selectFirst();
    }

    // Mendapatkan sorting yang dipilih dari ComboBox
    public String getSelectedComboboxSorting() {
        return cbxSorting.getSelectionModel().getSelectedItem();
    }

    // Event saat tombol Sorting ditekan
    @FXML
    private void handleButtonSorting(ActionEvent event) {
        bukuList.clear();
        BukuDAO.sortingOption = getSelectedComboboxSorting();
        loadTableBuku();
    }

    // Mengatur kolom-kolom TableView sesuai properti Buku
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

    // Mengisi data ke TableView dari DAO
    private void loadTableBuku() {
        bukuList = FXCollections.observableArrayList(BukuDAO.getAllBuku());
        tblViewBuku.setItems(bukuList);
    }
}

