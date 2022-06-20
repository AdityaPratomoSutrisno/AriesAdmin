package com.aditya.ariesadmin.Model;

import java.io.Serializable;

public class PeriodeModel implements Serializable {

    private String id_periode;
    private String periode;

    public PeriodeModel(String id_periode, String periode) {
        this.id_periode = id_periode;
        this.periode = periode;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}
