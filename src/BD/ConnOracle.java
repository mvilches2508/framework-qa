/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class ConnOracle {
    public String driver, url, ip, bd, usr, pass;
    public Connection conexion;
    private static ConnOracle INSTANCIA = null;
    
    public ConnOracle(){}
    public ConnOracle(String ip, String bd, String usr, String pass) {
        driver = "oracle.jdbc.driver.OracleDriver";
        this.bd = bd;
        this.usr = usr;
        this.pass = pass;
        url = new String("jdbc:oracle:thin:@" + ip + ":1521:" + bd);
        try {
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, usr, pass);
            System.out.println("Conexion a Base de Datos " + bd + " Ok");
        } catch (Exception exc) {
            System.out.println("Error al tratar de abrir la base de Datos" + bd + " : " + exc);
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    public Connection CerrarConexion() throws SQLException {
        conexion.close();
        conexion = null;
        return conexion;
    }
     private synchronized static void creaInstancia() {
        if (INSTANCIA == null) { 
            INSTANCIA = new ConnOracle();
        }
    }
    public static ConnOracle getInstancia() {
    if (INSTANCIA == null) creaInstancia();
       return INSTANCIA;
    }
}
