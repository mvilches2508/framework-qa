package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.File_Propiedades;
import util.TipoFile;

/**
 *
 * @author dlsclient
 * IP: 172.20.5.11
Puerto: 1525
Usuario: pmrg
Contraseña: pablo00
Informix Server=qa_soc
DB: dls_pool_xs
 
Cadena de conexión:
jdbc:informix-sqli://172.20.5.11:1525/dls_pool_xs:INFORMIXSERVER=qa_soc
 */

public final class ConnIfx {

    private static ConnIfx INSTANCIA = null;
    private static Properties PROPIEDADES = new File_Propiedades().getProperties(TipoFile.config);
    static Connection conexion = null;
    private String user, pass, ip, puerto, database,databaseServer, url;
   
    public ConnIfx() {
        this.user = PROPIEDADES.getProperty("userIfx");
        this.pass = PROPIEDADES.getProperty("passwordIfx");
        this.ip = PROPIEDADES.getProperty("ipbdIfx");
        this.puerto = PROPIEDADES.getProperty("puertoIfx");
        this.databaseServer = PROPIEDADES.getProperty("db_serverNameIfx");
        this.database = PROPIEDADES.getProperty("db_nameIfx");
        //jdbc:informix-sqli://172.20.5.11:1525/dls_pool_xs:INFORMIXSERVER=qa_soc
        //jdbc:informix-sqli://172.20.5.11:1525/dls_pool_xs:INFORMIXSERVER=qa_soc
        this.url = "jdbc:informix-sqli://" + this.ip + ":" + this.puerto + "/" + this.databaseServer +":INFORMIXSERVER="+this.database;
        conectar();
        INSTANCIA = this;
    }

    public boolean conectar() {
        boolean isConect = true;
        try {
            
            String driver = "com.informix.jdbc.IfxDriver";
            Class.forName(driver);
            conexion = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException e) {
            System.out.println("causa: \n"+  e.getCause());
            System.out.println("El sistema no conecta a BD :" + this.database);
            isConect = false;
        } catch (ClassNotFoundException ex) {
            System.out.println("causa: \n"+  ex.getCause());
            
        }
        return isConect;
    }

    private synchronized static void creaInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ConnIfx();
        }
    }

    public static ConnIfx getInstancia() {
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
        } catch (SQLException e) {
            System.out.println("El sistema fallo al cerrar conexión a bd:" + this.database);
            System.out.println("" + e.getMessage());
        }
    }
}
    
