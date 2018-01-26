package Controlador;

import java.util.Date;

public class LogCSV {
    private static LogCSV INSTANCIA = null;
    private Date fechaEjecucionTest;
    private String nombreTest;
    private int numPaso;
    private String descripcionPaso;
    private String comandoPaso;
    private String valorPaso;
    private String atributoPaso;
    private String tipoLocalizadorPaso;
    private boolean statusPaso;
    private String pasoEjecutado;

    public LogCSV() {
        INSTANCIA = this;
    }

    public LogCSV(Date fechaEjecucionTest, String nombreTest, int numPaso, String descripcionPaso, String comandoPaso, String valorPaso, String atributoPaso, String tipoLocalizadorPaso, boolean statusPaso, String pasoEjecutado) {
        this.fechaEjecucionTest = fechaEjecucionTest;
        this.nombreTest = nombreTest;
        this.numPaso = numPaso;
        this.descripcionPaso = descripcionPaso;
        this.comandoPaso = comandoPaso;
        this.valorPaso = valorPaso;
        this.atributoPaso = atributoPaso;
        this.tipoLocalizadorPaso = tipoLocalizadorPaso;
        this.statusPaso = statusPaso;
        this.pasoEjecutado = pasoEjecutado;
    }

    public static LogCSV getINSTANCIA() {
        return INSTANCIA;
    }

    public static void setINSTANCIA(LogCSV INSTANCIA) {
        LogCSV.INSTANCIA = INSTANCIA;
    }

    public Date getFechaEjecucionTest() {
        return fechaEjecucionTest;
    }

    public void setFechaEjecucionTest(Date fechaEjecucionTest) {
        this.fechaEjecucionTest = fechaEjecucionTest;
    }

    public String getNombreTest() {
        return nombreTest;
    }

    public void setNombreTest(String nombreTest) {
        this.nombreTest = nombreTest;
    }

    public int getNumPaso() {
        return numPaso;
    }

    public void setNumPaso(int numPaso) {
        this.numPaso = numPaso;
    }

    public String getDescripcionPaso() {
        return descripcionPaso;
    }

    public void setDescripcionPaso(String descripcionPaso) {
        this.descripcionPaso = descripcionPaso;
    }

    public String getComandoPaso() {
        return comandoPaso;
    }

    public void setComandoPaso(String comandoPaso) {
        this.comandoPaso = comandoPaso;
    }

    public String getValorPaso() {
        return valorPaso;
    }

    public void setValorPaso(String valorPaso) {
        this.valorPaso = valorPaso;
    }

    public String getAtributoPaso() {
        return atributoPaso;
    }

    public void setAtributoPaso(String atributoPaso) {
        this.atributoPaso = atributoPaso;
    }

    public String getTipoLocalizadorPaso() {
        return tipoLocalizadorPaso;
    }

    public void setTipoLocalizadorPaso(String tipoLocalizadorPaso) {
        this.tipoLocalizadorPaso = tipoLocalizadorPaso;
    }

    public boolean isStatusPaso() {
        return statusPaso;
    }

    public void setStatusPaso(boolean statusPaso) {
        this.statusPaso = statusPaso;
    }

    public String getPasoEjecutado() {
        return pasoEjecutado;
    }

    public void setPasoEjecutado(String pasoEjecutado) {
        this.pasoEjecutado = pasoEjecutado;
    }
}

