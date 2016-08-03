/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Util.File_Propiedades;
import Util.TableTransformer;
import Util.TipoFile;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mvilches
 */
public class GestorDataBase {
     private Properties propriedades = new File_Propiedades().getProperties(TipoFile.query);
    
    public DefaultTableModel cargaBD(){
          try {
              ConnMySql conexion = ConnMySql.getInstancia();
              String consulta = this.propriedades.getProperty("query1");
              PreparedStatement pstm = conexion.getConexion().prepareStatement(consulta,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
              ResultSet rs = pstm.executeQuery();
              return new DefaultTableModel( TableTransformer.getDataMultiDimensional(rs),TableTransformer.getColumnas(rs));
                      
                      } catch (SQLException ex) {
              System.out.println("ERROR: "+ex);
              return null;
          }
        
    }
}
