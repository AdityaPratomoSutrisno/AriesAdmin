package com.aditya.ariesadmin.Model;

import java.io.Serializable;

public class IuranModel implements Serializable {

    private String id_iuran;
    private String id_warga_iuran;
    private String nomor_iuran;
    private String nama_lengkap_iuran;
    private String nominal_iuran;
    private String id_periode_iuran;
    private String periode_iuran;
    private String nomor_hp;
    private String tanggal_iuran;

    public IuranModel(String id_iuran, String id_warga_iuran, String nominal_iuran, String id_periode_iuran, String tanggal_iuran) {
        this.id_iuran = id_iuran;
        this.id_warga_iuran = id_warga_iuran;
        this.nominal_iuran = nominal_iuran;
        this.id_periode_iuran = id_periode_iuran;
        this.tanggal_iuran = tanggal_iuran;
    }

    public IuranModel(String id_iuran , String nomor_iuran, String nama_lengkap_iuran, String nominal_iuran, String tanggal_iuran, String nomor_hp) {
        this.id_iuran = id_iuran;
        this.nomor_iuran = nomor_iuran;
        this.nama_lengkap_iuran = nama_lengkap_iuran;
        this.nominal_iuran = nominal_iuran;
        this.tanggal_iuran = tanggal_iuran;
        this.nomor_hp = nomor_hp;
    }

    public String getId_iuran() {
        return id_iuran;
    }

    public void setId_iuran(String id_iuran) {
        this.id_iuran = id_iuran;
    }

    public String getId_warga_iuran() {
        return id_warga_iuran;
    }

    public void setId_warga_iuran(String id_warga_iuran) {
        this.id_warga_iuran = id_warga_iuran;
    }

    public String getNomor_iuran() {
        return nomor_iuran;
    }

    public void setNomor_iuran(String nomor_iuran) {
        this.nomor_iuran = nomor_iuran;
    }

    public String getNama_lengkap_iuran() {
        return nama_lengkap_iuran;
    }

    public void setNama_lengkap_iuran(String nama_lengkap_iuran) {
        this.nama_lengkap_iuran = nama_lengkap_iuran;
    }

    public String getNominal_iuran() {
        return nominal_iuran;
    }

    public void setNominal_iuran(String nominal_iuran) {
        this.nominal_iuran = nominal_iuran;
    }

    public String getId_periode_iuran() {
        return id_periode_iuran;
    }

    public void setId_periode_iuran(String id_periode_iuran) {
        this.id_periode_iuran = id_periode_iuran;
    }

    public String getPeriode_iuran() {
        return periode_iuran;
    }

    public void setPeriode_iuran(String periode_iuran) {
        this.periode_iuran = periode_iuran;
    }

    public String getTanggal_iuran() {
        return tanggal_iuran;
    }

    public void setTanggal_iuran(String tanggal_iuran) {
        this.tanggal_iuran = tanggal_iuran;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public void setNomor_hp(String nomor_hp) {
        this.nomor_hp = nomor_hp;
    }
}
