
package Controlador;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Execution {
    private static Execution INSTANCIA = null;
    private String path ="";
    private String pathOriginal ="";
    private File archivo = null;
    private Date fechaEjecucion = new java.util.Date();
    private String caso="";

    public Execution() {
    }

    public String getPathOriginal() {
        return pathOriginal;
    }

    public void setPathOriginal(String pathOriginal) {
        this.pathOriginal = pathOriginal;
    }
    
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public File getArchivo() {
        return archivo;
    }
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
    public String getFechaEjecucion() {
        Calendar calendario = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmm");    
        return df.format(calendario.getTime());
    }
    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }
    public String getCaso() {
        return caso;
    }
    public void setCaso(String caso) {
        this.caso = caso;
    }
    private synchronized static void creaInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Execution();
        }
    }
    public static Execution getInstancia() {
        if (INSTANCIA == null) {
            creaInstancia();            
        }
        return INSTANCIA;
    }
    
    public void creaCarpetaDeLanzamiento(){
        File carpeta= new File(pathOriginal);
        carpeta.mkdir();
    }
    public void creaCarpetaDePrueba(String prueba){
        setPath(getPathOriginal()+"\\"+prueba);
        File carpetaPrueba =new File(getPath());
        carpetaPrueba.mkdir();
    }
}
