package Modelo;

import Controlador.Resultado;
import Controlador.Step;
import Controlador.Test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import util.File_Propiedades;
import util.TableTransformer;
import util.TipoFile;

public class GestorBD {
    
    String query;  
    Properties PROPIEDADES = new File_Propiedades().getProperties(TipoFile.query);
    ConnMySQL conexion = ConnMySQL.getInstancia();
    Statement sentencia = null;
    PreparedStatement prpSentencia = null;
    ResultSet rs =null;

    public ArrayList<String> getEscenarioDescripcion(){
        ArrayList<String> productos = new ArrayList<>();        
        try{                      
            query = PROPIEDADES.getProperty("qryProductos");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                productos.add(rs.getString("Description"));
            }
            rs.close();
            sentencia.close();
            return productos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
           limpiar();
        }
    }

    public ArrayList<String> getEscenario(){
        ArrayList<String> modulos = new ArrayList<>();        
        try{                      
            query = PROPIEDADES.getProperty("qryModulos");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                modulos.add(rs.getString("nombre"));
            }
            rs.close();
            sentencia.close();
            return modulos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
           limpiar();
        }
    }
    
    public ArrayList<String> getEjecutar(){
        ArrayList<String> ejecucion = new ArrayList<>();        
        try{                      
            query = PROPIEDADES.getProperty("qryEjecutar");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                ejecucion.add(rs.getString("tipoEjecucion"));
            }
            rs.close();
            sentencia.close();
            return ejecucion;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
           limpiar();
        }
    }
    
    public Object[][] getPasosPorTest(int test){
        Object[][] pasos;
        try{                      
            query = PROPIEDADES.getProperty("qryPasosPorTest");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setInt(1, test);
            rs = prpSentencia.executeQuery();
            pasos = TableTransformer.getDataMultiDimensional(rs);
            rs.close();
            prpSentencia.close();
            return pasos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }
    public int getContTestPorEscenario(int esc){
          int casos;
        try{                      
            query = PROPIEDADES.getProperty("qryCountTestPorProducto");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setInt(1, esc);
            rs = prpSentencia.executeQuery();
            casos = TableTransformer.getRowCount(rs);
            rs.close();
            prpSentencia.close();
            return casos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return 0;
        }finally{
            limpiar();
        }
    }    
    public ArrayList<Integer> getTestPorID(int esc){
          ArrayList<Integer> casos = new ArrayList<>();
        try{                      
            query = PROPIEDADES.getProperty("qryTestPorIDPorEscenario");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setInt(1, esc);
            rs = prpSentencia.executeQuery();
            while (rs.next()) {                
                casos.add(rs.getInt("idTestCase"));
            }
            rs.close();
            prpSentencia.close();
            return casos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }
    public ArrayList<Test> getPruebasAEjecutar(int esc){
        System.out.println("Obteniendo casos");
        ArrayList<Test> casos = new ArrayList<>();
           try{                      
            query = PROPIEDADES.getProperty("qryTestPorEscenario");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setInt(1, esc);
            System.out.println("Consultando Registros en BD");
            rs = prpSentencia.executeQuery();
            while (rs.next()) {                
                Test test = new Test();
                test.setId(rs.getInt("idTestCase"));
                test.setNombre(rs.getString("Name"));
                test.setDescripción(rs.getString("descripcion"));
                test.setEscenario(rs.getString("Description"));                
                test.setFechaEjecucion(new java.util.Date());
                casos.add(test);
            }
            rs.close();
            prpSentencia.close();
            return casos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }
    public String[] getTestPorNombre(String nomTest){
          String[] caso;
        try{                      
            query = PROPIEDADES.getProperty("qryTestPorNombre");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setString(1,"%" +nomTest + "%");
            rs = prpSentencia.executeQuery();
            caso = new String[TableTransformer.getRowCount(rs)];
            rs.beforeFirst();
            int i=0;
            while (rs.next()) {                 
                caso[i] = rs.getString("Name");
                i++;
            }
            rs.close();
            prpSentencia.close();           
            return caso;           
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }    
    public ArrayList<Step> getPasosAEjecutarPorPrueba(int idTest){
        System.out.println("Obteniendo pasos a ejecutar");
        ArrayList<Step> pasos = new ArrayList<>();
        try{                      
            query = PROPIEDADES.getProperty("qryPasosPorTest");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setInt(1, idTest);
            System.out.println("Consultando pasos en BD");
            rs = prpSentencia.executeQuery();
            
            while (rs.next()) {            
                Step paso = new Step();
                paso.setIdStep(rs.getInt("idStep"));
                paso.setDescripcion(rs.getString("descripcion"));
                paso.setComando(rs.getString("Command"));
                paso.setValor(rs.getString("Value"));
                paso.setAtributo(rs.getString("atributo"));
                paso.setTipoLocalizador(rs.getString("tipo"));
                pasos.add(paso);
            }
            rs.close();
            prpSentencia.close();    
            return pasos;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);            
            return null;
        }finally{
            limpiar();
        }
    }
    public Test getPruebasAEjecutarByName(String nombre){
         System.out.println("Obteniendo caso");
        Test test = new Test();
           try{                      
            query = PROPIEDADES.getProperty("qryTestByName");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            prpSentencia.setString(1, nombre);            
            rs = prpSentencia.executeQuery();
            while (rs.next()) {                                
                test.setId(rs.getInt("idTestCase"));
                test.setNombre(rs.getString("Name"));
                test.setDescripción(rs.getString("descripcion"));                                
                test.setFechaEjecucion(new java.util.Date());                
            }
            rs.close();
            prpSentencia.close();
            return test;
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }
    public void guardarResultado(Resultado resultado){
        System.out.println("Guardar caso");        
        try{                      
            query = PROPIEDADES.getProperty("qryResultadoObtenido");
            prpSentencia = conexion.getConexion().prepareStatement(query);
            //ACA se setean los valores obtenidos del resultado
            prpSentencia.setInt(1,resultado.getIdTest_Result());
            prpSentencia.setInt(2,resultado.getIdIteracion());
            prpSentencia.setInt(3,resultado.getIdTestCase());
            prpSentencia.setBoolean(4,resultado.isResultado());
            prpSentencia.setString(5,resultado.getObservaciones());
            int hr=0;
            int min = 0;
            int seg = 0;
            int ss=resultado.getTiempotx();
            if(ss < 59){
                min = (ss / 60);
                prpSentencia.setInt(6,hr);
                prpSentencia.setInt(7,min);
                prpSentencia.setInt(8,ss);
            }else{
                    hr= ss/3600;
                    min =(ss-hr*3600)/60;
                    seg = ss-hr*3600-min*60;
                    prpSentencia.setInt(6,hr);
                    prpSentencia.setInt(7,min);
                    prpSentencia.setInt(8,seg);
            } 
            System.out.println("Almacenando resultados en BD");
            prpSentencia.executeUpdate();                      
        }catch(SQLException ex){
            System.out.println("Fallo en BD: "+ex);             
        }finally{
            limpiar();
        }
    }
    public ResultSet getResultado(){
        Object[][] resultados;
        try{                      
            query = PROPIEDADES.getProperty("qryResultados");
            prpSentencia = conexion.getConexion().prepareStatement(query);            
            rs = prpSentencia.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.err.println("Fallo en BD: "+ex);
            return null;
        }finally{
            limpiar();
        }
    }
    private void limpiar(){
        prpSentencia= null;
        rs = null;
        query= null;
    }
}
