package Controlador;

import java.util.Date;

public class Resultado {
    private int idTest_Result, idIteracion, idTestCase, tiempotx;
    private boolean resultado;
    private String observaciones;
    private Date fecha;

    public Resultado() {
    }

    public Resultado(int idTest_Result, int idIteracion, int idTestCase, boolean resultado, String observaciones, Date fecha) {
        this.idTest_Result = idTest_Result;
        this.idIteracion = idIteracion;
        this.idTestCase = idTestCase;
        this.resultado = resultado;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }

    public int getTiempotx() {
        return tiempotx;
    }

    public void setTiempotx(int tiempotx) {
        this.tiempotx = tiempotx;
    }

    public int getIdTest_Result() {
        return idTest_Result;      
    }

    public void setIdTest_Result(int idTest_Result) {
        this.idTest_Result = idTest_Result;
    }

    public int getIdIteracion() {   
        return idIteracion;
    }

    public void setIdIteracion(int idIteracion) {
        this.idIteracion = idIteracion;
    }

    public int getIdTestCase() {
        return idTestCase;
    }

    public void setIdTestCase(int idTestCase) {
        this.idTestCase = idTestCase;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }     
}
