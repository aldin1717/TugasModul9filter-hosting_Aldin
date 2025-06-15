/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package tugasmodul9filterdansorting.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import tugasmodul9filterdansorting.dao.BukuDAO;
import tugasmodul9filterdansorting.models.Buku;

public class MainController implements Initializable {

    @FXML private ComboBox<String> cbxKategori;
    @FXML private ComboBox<String> cbxSorting;
    @FXML private DatePicker datePickerFrom;
    @FXML private DatePicker datePickerTo;
    @FXML private Button btnFilter, btnReset;
    @FXML private TableView<Buku> tblViewBuku;
    @FXML private TableColumn<Buku, String> colKodeBuku, colKategori, colJudul, colPengarang, colPenerbit, colPengadaan;
    @FXML private TableColumn<Buku, Integer> colTahun, colEdisi;

    private ObservableList<Buku> bukuList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxKategori.setItems(FXCollections.observableArrayList(BukuDAO.getAllKategori()));
        cbxKategori.getSelectionModel().selectFirst();
        cbxSorting.setItems(FXCollections.observableArrayList("Judul A-Z", "Judul Z-A", "Pengadaan Terbaru", "Pengadaan Lama"));
        cbxSorting.getSelectionModel().clearSelection();
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        initTable();
        loadTable(null, null, null, null);
    }

    private void initTable() {
        colKodeBuku.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getKodeBuku()));
        colKategori.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNamaKategori()));
        colJudul.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getJudul()));
        colPengarang.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPengarang()));
        colPenerbit.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPenerbit()));
        colTahun.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTahun()).asObject());
        colEdisi.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getEdisi()).asObject());
        colPengadaan.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTanggalPengadaan()));
    }

    private void loadTable(String kategori, String sorting, LocalDate from, LocalDate to) {
        bukuList.setAll(BukuDAO.getFilteredSortedByDateRange(kategori, sorting, from, to));
        tblViewBuku.setItems(bukuList);
    }

    @FXML
    private void handleFilterAndSort() {
        loadTable(
            cbxKategori.getValue(),
            cbxSorting.getValue(),
            datePickerFrom.getValue(),
            datePickerTo.getValue()
        );
    }

    @FXML
    private void handleReset() {
        cbxKategori.getSelectionModel().selectFirst();
        cbxSorting.getSelectionModel().clearSelection();
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        loadTable(null, null, null, null);
    }
}