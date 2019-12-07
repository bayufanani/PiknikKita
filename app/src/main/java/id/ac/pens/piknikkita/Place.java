package id.ac.pens.piknikkita;

public class Place {
    int id;
    String nama, deskripsi, gambar_utama;

    public Place(int id, String nama, String deskripsi, String gambar_utama) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.gambar_utama = gambar_utama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar_utama() {
        return gambar_utama;
    }

    public void setGambar_utama(String gambar_utama) {
        this.gambar_utama = gambar_utama;
    }
}
