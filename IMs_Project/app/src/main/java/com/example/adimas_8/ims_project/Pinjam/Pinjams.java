package com.example.adimas_8.ims_project.Pinjam;

public class Pinjams {
    private String gedung, ruang, matkul, mulai, selesai, tanggal;

    public Pinjams(String gedung, String ruang, String matkul,String mulai,String selesai,String tanggal) {
        this.gedung = gedung;
        this.ruang = ruang;
        this.tanggal= tanggal;
        this.matkul = matkul;
        this.mulai = mulai;
        this.selesai = selesai;
    }

    public String getGedung() {

        return gedung;
    }

    public void setGedung(String gedung) {

        this.gedung = gedung;
    }

    public String getRuang() {

        return ruang;
    }

    public void setRuang(String ruang) {

        this.ruang = ruang;
    }

    public String getMatkul() {

        return matkul;
    }

    public void setMatkul(String matkul) {

        this.matkul = matkul;
    }

    public String getMulai() {

        return mulai;
    }

    public void setMulai(String mulai) {

        this.mulai = mulai;
    }

    public String getSelesai() {

        return selesai;
    }

    public void setSelesai(String selesai) {

        this.selesai = selesai;
    }

    public String getTanggal() {

        return tanggal;
    }

    public void setTanggal(String tanggal) {

        this.tanggal = tanggal;
    }
}

