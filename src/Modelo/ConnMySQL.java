package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import javax.swing.JOptionPane;
import util.File_Propiedades;
import util.TipoFile;

/**
 *
 * @author qaexterno
 */
public class ConnMySQL {

    private static ConnMySQL INSTANCIA = null;
    private static Properties PROPIEDADES = new File_Propiedades().getProperties(TipoFile.config);
    static Connection conexion = null;
    private String user, pass, ip, puerto, database, url;
   
    public ConnMySQL() {
        this.user = PROPIEDADES.getProperty("userMy");
        this.pass = PROPIEDADES.getProperty("passwordMy");
        this.ip = PROPIEDADES.getProperty("ipbdMy");
        this.puerto = PROPIEDADES.getProperty("puertoMy");
        this.database = PROPIEDADES.getProperty("db_nameMy");
        this.url = "jdbc:mysql://" + this.ip + ":" + this.puerto + "/" + this.database;
        conectar();
        INSTANCIA = this;
    }

    public boolean conectar() {
        boolean isConect = true;
        try {
            conexion = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getCause());
            System.out.println("El sistema no conecta a BD :" + this.database);
            isConect = false;
        }
        return isConect;
    }

    private synchronized static void creaInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ConnMySQL();
        }
    }

    public static ConnMySQL getInstancia() {
        if (INSTANCIA == null) {
            creaInstancia();            
        }
        return INSTANCIA;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (Exception e) {
            System.out.println("El sistema fallo al cerrar conexión a bd:" + this.database);
            System.out.println("" + e.getMessage().toString());
        }
    }
}
