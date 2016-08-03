package BD;


/**
 *
 * @author mario.vilches
 */
import Util.File_Propiedades;
import Util.TipoFile;
import java.sql.*;
import java.util.Properties;

public class ConnSybase{

    
  private Connection conexion=null;
  
  public ConnSybase() {
      
          try
        {
             Properties propiedades = new File_Propiedades().getProperties(TipoFile.config); 
              String database="jdbc:jtds:sybase://"+propiedades.getProperty("IpBD")+":"+propiedades.getProperty("puerto")+"/"+propiedades.getProperty("bd"); //ISOCERT **QA
            conexion = DriverManager.getConnection(database,propiedades.getProperty("User"),propiedades.getProperty("Pass"));
                        
        }catch(SQLException e)
        {
            System.out.println(e);
        }        
    }
  public Connection getConexion()
  {
      return conexion;
  }
  public Connection cerrarConexion(){
      try{
          conexion.close();
      }catch(SQLException ex){
          System.out.println(ex);
      }
      conexion=null;
      return conexion;
  }

}		
