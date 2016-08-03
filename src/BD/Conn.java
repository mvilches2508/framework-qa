/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
/**
 *
 * @author mvilches
 */
public abstract class Conn {
    
    public String user="";
    public String pass="";
    public String IP="";
    public String PUERTO="";
    public String database="";
    public static Connection conexion=null;  

    public Conn() {
    }
    
    

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getIP() {
        return IP;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }
    public String getPUERTO() {
        return PUERTO;
    }
    public void setPUERTO(String PUERTO) {
        this.PUERTO = PUERTO;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public static Connection getConexion() {
        return conexion;
    }
    public static void setConexion(Connection conexion) {
        Conn.conexion = conexion;
    }    
    
}
