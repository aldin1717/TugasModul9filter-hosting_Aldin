/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul9contoh1.models;

/**
 *
 * @author MyBook Hype AMD
 */
public class KategoriBuku {
    private String kodeKategori;
    private String namaKategori;
    
    public KategoriBuku(String kodeKategori, String namaKategori) {
        this.kodeKategori = kodeKategori;
        this.namaKategori = namaKategori;
    }
    
    public String getKodeKategori() {
        return kodeKategori;
    }
    
    public void setKodeKategori(String kodeKategori) {
        this.kodeKategori = kodeKategori;
    }
    
    public String getNamaKategori() {
        return namaKategori;
    }
    
    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
    
    @Override
    public String toString() {
        return namaKategori;
    }
          
}
