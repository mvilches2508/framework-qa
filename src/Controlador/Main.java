package Controlador;

import Modelo.ConnIfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;
import util.File_Propiedades;
import util.TipoFile;

/**
 *
 * @author dlsclient
 */
public class Main {
    
   
   
    
    public static void main(String[] args) throws SQLException {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query =("SELECT e.echefoliomemo from mv_reclamo r\n"
                    + "INNER JOIN mv_envio_cheque e ON r.reclcodigo = e.reclcodigo\n"
                    + "order by r.reclfolio desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("echefoliomemo"));
                //System.out.println(rs.getString("encacodigobarra")); 
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
}
