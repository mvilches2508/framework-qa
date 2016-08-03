package BD;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Util.File_Propiedades;
import Util.TipoFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author mario.vilches
 */
public class ConnMySql {
        private static ConnMySql INSTANCIA = null;
        public Properties propiedades = new File_Propiedades().getProperties(TipoFile.config); 
        static Connection conexion =null; 
        private String user;
        private String pass;
        private String IP;
        private String PUERTO;
        private String database;        
    
     public ConnMySql(){}     

    public ConnMySql(String user, String Pass) throws SQLException {
        
        this.user = user;
        this.pass = Pass;
        this.IP = propiedades.getProperty("IP");
        this.PUERTO = propiedades.getProperty("PUERTO");
        this.database = propiedades.getProperty("BD");
        this.database="jdbc:mysql://"+IP+":"+PUERTO+"/"+database;              
    }
    
    public boolean conectar(){
      boolean isConect=true;
      try{
          conexion = DriverManager.getConnection(getDatabase(),getUser(),getPass());
      }catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null,""+e);
          System.out.println(""+e);
          isConect =false;
      }
      return isConect;
  }
    private synchronized static void creaInstancia() {
        if (INSTANCIA == null) { 
            INSTANCIA = new ConnMySql();
        }
    }
    public static ConnMySql getInstancia() {
    if (INSTANCIA == null) creaInstancia();
       return INSTANCIA;
    }
    public Connection getConexion()       {      
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
    
    public String getUser() {
        return user;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
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
  public void setCommit(boolean isCommit) throws SQLException
  {
      conexion.setAutoCommit(isCommit);
  }
   public void makeCommit() throws SQLException
  {
      conexion.commit();
  }
   public void comeBack() throws SQLException
  {
      conexion.rollback();
  }
}