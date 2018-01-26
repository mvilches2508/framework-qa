package Controlador;

import java.io.File;
import java.util.Date;

public class Test {
    private static Test INSTANCIA = null;
    private String nombre, descripción, escenario, modulo;
    private int id, idEscenario;    
    private boolean resultado, status;
    private String obsResultado;
    private File[] evidencia;
    private Date fechaEjecucion;

    public Test() {
        INSTANCIA = this;
    }

    public Test(String nombre, String descripción, String escenario, String modulo, int id, int idEscenario, boolean resultado, boolean status, String obsResultado, File[] evidencia, Date fechaEjecucion) {
        this.nombre = nombre;
        this.descripción = descripción;
        this.escenario = escenario;
        this.id = id;
        this.idEscenario= idEscenario;
        this.resultado = resultado;
        this.status=status;
        this.obsResultado = obsResultado;
        this.evidencia = evidencia;
        this.fechaEjecucion = fechaEjecucion;
        this.modulo= modulo;
        INSTANCIA = this;
    }
    
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripción() {
        return descripción;
    }
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
    public String getEscenario() {
        return escenario;
    }
    public void setEscenario(String escenario) {
        this.escenario = escenario;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdEscenario() {
        return idEscenario;
    }

    public void setIdEscenario(int idEscenario) {
        this.idEscenario = idEscenario;
    }
    
    public boolean isResultado() {
        return resultado;
    }
    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getObsResultado() {
        return obsResultado;
    }
    public void setObsResultado(String obsResultado) {
        this.obsResultado = obsResultado;
    }
    public File[] getEvidencia() {
        return evidencia;
    }
    public void setEvidencia(File[] evidencia) {
        this.evidencia = evidencia;
    }
    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }
    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }   
    
     private synchronized static void creaInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Test();
        }
    }

    public static Test getInstancia() {
        if (INSTANCIA == null) {
            creaInstancia();            
        }
        return INSTANCIA;
    }
}
