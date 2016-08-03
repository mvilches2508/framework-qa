/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mvilches
 */
public class TableTransformer {

    public TableTransformer() {
    }
    
    public static int getRowCount(ResultSet rs)throws SQLException{
        int i =0;
        rs.beforeFirst();
        while(rs.next()){i++;}
        return i;
    }
    public static Object[][] getDataMultiDimensional(ResultSet rs)
    {
        try{             
            int columnas = rs.getMetaData().getColumnCount();            
            int filas = getRowCount(rs);
            rs.beforeFirst();
            Object[][] data = new Object[filas][columnas];
              
                    for (int j = 1; j <= filas; j++) {
                        rs.next();
                        for (int i = 1; i <= columnas; i++) { 
                            data [j-1][i-1] =  rs.getObject(i);                           
                        }                    
                    }                
                   return data;
        }         
         catch(SQLException ex)
          {
               System.out.println("Error :" + ex.getMessage());
               return null;
          }
    }
    
    public static Object[] getColumnas(ResultSet rs)
    {
        Object[] cabeceras= null;
        try{             
            rs.beforeFirst();
            int columnas = rs.getMetaData().getColumnCount();
            cabeceras = new Object[columnas];
                for (int i = 1; i <= columnas; i++) {
                        cabeceras[i-1] = rs.getMetaData().getColumnName(i);
                    }     
            return cabeceras;
        }         
         catch(SQLException ex)
          {
               System.out.println("Error :" + ex.getMessage());
               return null;
          }
    }
    
     public static void getData(ResultSet rs)
    {
         try{ 
                int columnas = 1 + rs.getMetaData().getColumnCount();
                System.out.println("columnas: "+columnas);
                int filas = rs.getRow();                
                System.out.println("filas: "+filas);
                    
                for (int i = 1; i < columnas; i++) {                        
                        System.out.println("columna :"+rs.getMetaData().getColumnName(i));
                    }
                
                while(rs.next())
                {
                    rs.getString(1);
                }                
        }         
         catch(SQLException ex)
          {
               System.out.println("Error :" + ex.getMessage());
          }
    }
    
}
