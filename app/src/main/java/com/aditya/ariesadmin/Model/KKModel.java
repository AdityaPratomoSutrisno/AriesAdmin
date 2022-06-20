package com.aditya.ariesadmin.Model;

import java.io.Serializable;

public class KKModel implements Serializable {

    private String id_kk;
    private String nomor_iuran_kk;
    private String nik_kk;
    private String nama_kk;
    private String tempat_lahir;
    private String tanggal_lahir;
    private String jk;
    private String nomo_hp;
    private String alamat_kk;
    int status;

    public KKModel(String id_kk, String nomor_iuran_kk, String nama_kk, String alamat_kk) {
        this.id_kk = id_kk;
        this.nomor_iuran_kk = nomor_iuran_kk;
        this.nama_kk = nama_kk;
        this.alamat_kk = alamat_kk;
    }

    public KKModel(String id_kk, String nomor_iuran_kk, String nik_kk, String nama_kk, String tempat_lahir, String tanggal_lahir, String jk, String nomo_hp, String alamat_kk, int status) {
        this.id_kk = id_kk;
        this.nomor_iuran_kk = nomor_iuran_kk;
        this.nik_kk = nik_kk;
        this.nama_kk = nama_kk;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.jk = jk;
        this.nomo_hp = nomo_hp;
        this.alamat_kk = alamat_kk;
        this.status = status;
    }

    public String getId_kk() {
        return id_kk;
    }

    public void setId_kk(String id_kk) {
        this.id_kk = id_kk;
    }

    public String getNik_kk() {
        return nik_kk;
    }

    public void setNik_kk(String nik_kk) {
        this.nik_kk = nik_kk;
    }

    public String getNama_kk() {
        return nama_kk;
    }

    public void setNama_kk(String nama_kk) {
        this.nama_kk = nama_kk;
    }

    public String getAlamat_kk() {
        return alamat_kk;
    }

    public void setAlamat_kk(String alamat_kk) {
        this.alamat_kk = alamat_kk;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getNomor_iuran_kk() {
        return nomor_iuran_kk;
    }

    public void setNomor_iuran_kk(String nomor_iuran_kk) {
        this.nomor_iuran_kk = nomor_iuran_kk;
    }

    public String getNomo_hp() {
        return nomo_hp;
    }

    public void setNomo_hp(String nomo_hp) {
        this.nomo_hp = nomo_hp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
