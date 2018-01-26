package Controlador;

import Modelo.ConnMySQL;
import Modelo.GestorBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;


public class ControladorGráfico {
    ConnMySQL conexion = ConnMySQL.getInstancia();
    
    public ResultSet consulta(String sql) {
        ResultSet res = null;
        try {
            PreparedStatement pstm = conexion.getConexion().prepareStatement(sql);
            res = pstm.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error consulta :" + e.getMessage());
        }
        return res;
    }
    
    public DefaultComboBoxModel cargarComboModulo() {
        DefaultComboBoxModel listmodel = new DefaultComboBoxModel();

        ResultSet res = this.consulta("SELECT nombre FROM modulo ORDER BY nombre ASC");
        try {
            while (res.next()) {
                listmodel.addElement(res.getString("nombre"));
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta: " + ex.getMessage());
        }
        return listmodel;
    }
    
    public DefaultComboBoxModel cargarComboProducto(String nombre) {
        DefaultComboBoxModel listmodel = new DefaultComboBoxModel();

        ResultSet res = this.consulta(" SELECT description "
                + " FROM functional_scene fs, modulo m  "
                + " WHERE fs.idModulo=m.idModulo "
                + " AND m.nombre = '" + nombre + "' "
                + " ORDER BY fs.description ASC ");
        try {
            while (res.next()) {
                listmodel.addElement(res.getString("description"));
            }
            res.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return listmodel;
    }
    
    public ArrayList<String> getEjecutar() {
        GestorBD gestor = new GestorBD();
        ArrayList<String> listaEjecutar = gestor.getEjecutar();
        if (listaEjecutar == null) {
            System.out.println("Error");
        }
        return listaEjecutar;
    }
}
