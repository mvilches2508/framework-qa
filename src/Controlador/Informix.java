package Controlador;

import Modelo.ConnIfx;
import Modelo.ConnMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import util.File_Propiedades;
import util.TipoFile;

/**
 *
 * @author Inovabiz
 */
public class Informix {

    private String encargoCodigo, recepcionEncargo, codigoDespacho;
    private String codigos, folioRendicion;
    private String despacho, entrega, rendicionEntrega, despachoReparto;
    private String documentosTributarios, anulacion, anulacionEncargo;
    private String modificarNomina, modificar, cierre, nominaContenedora, reclamo;
    private int dls;
    SeleniumController selenium = new SeleniumController();

    public Informix() {
    }

    public Informix(String encargoCodigo, String recepcionEncargo, String codigoDespacho, String codigos, String folioRendicion, String modulo, String despacho, String entrega, String rendicionEntrega, String despachoReparto, String documentosTributarios, String anulacion, String anulacionEncargo, String modificarNomina, String modificar, String cierre, String nominaContenedora, int dls) {
        this.encargoCodigo = encargoCodigo;
        this.recepcionEncargo = recepcionEncargo;
        this.codigoDespacho = codigoDespacho;
        this.codigos = codigos;
        this.folioRendicion = folioRendicion;
        this.despacho = despacho;
        this.entrega = entrega;
        this.rendicionEntrega = rendicionEntrega;
        this.despachoReparto = despachoReparto;
        this.documentosTributarios = documentosTributarios;
        this.anulacion = anulacion;
        this.anulacionEncargo = anulacionEncargo;
        this.modificarNomina = modificarNomina;
        this.modificar = modificar;
        this.cierre = cierre;
        this.nominaContenedora = nominaContenedora;
        this.dls=dls;
    }
  

    public String getEncargoCodigo() {
        return encargoCodigo;
    }

    public void setEncargoCodigo(String encargoCodigo) {
        this.encargoCodigo = encargoCodigo;
    }

    public String getRecepcionEncargo() {
        return recepcionEncargo;
    }

    public void setRecepcionEncargo(String recepcionEncargo) {
        this.recepcionEncargo = recepcionEncargo;
    }

    public String getCodigoDespacho() {
        return codigoDespacho;
    }

    public void setCodigoDespacho(String codigoDespacho) {
        this.codigoDespacho = codigoDespacho;
    }

    public String getFolioRendicion() {
        return folioRendicion;
    }

    public void setFolioRendicion(String folioRendicion) {
        this.folioRendicion = folioRendicion;
    }

    public String getDespacho() {
        return despacho;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getRendicionEntrega() {
        return rendicionEntrega;
    }

    public void setRendicionEntrega(String rendicionEntrega) {
        this.rendicionEntrega = rendicionEntrega;
    }

    public String getDespachoReparto() {
        return despachoReparto;
    }

    public void setDespachoReparto(String despachoReparto) {
        this.despachoReparto = despachoReparto;
    }

    public String getDocumentosTributarios() {
        return documentosTributarios;
    }

    public void setDocumentosTributarios(String documentosTributarios) {
        this.documentosTributarios = documentosTributarios;
    }

    public String getAnulacion() {
        return anulacion;
    }

    public void setAnulacion(String anulacion) {
        this.anulacion = anulacion;
    }

    public String getAnulacionEncargo() {
        return anulacionEncargo;
    }

    public void setAnulacionEncargo(String anulacionEncargo) {
        this.anulacionEncargo = anulacionEncargo;
    }

    public String getModificarNomina() {
        return modificarNomina;
    }

    public void setModificarNomina(String modificarNomina) {
        this.modificarNomina = modificarNomina;
    }

    public String getModificar() {
        return modificar;
    }

    public void setModificar(String modificar) {
        this.modificar = modificar;
    }

    public String getCierre() {
        return cierre;
    }

    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    public String getNominaContenedora() {
        return nominaContenedora;
    }

    public void setNominaContenedora(String nominaContenedora) {
        this.nominaContenedora = nominaContenedora;
    }

    public String getReclamo() {
        return reclamo;
    }

    public void setReclamo(String reclamo) {
        this.reclamo = reclamo;
    }

    public String getCodigos() {
        return codigos;
    }

    public void setCodigos(String codigos) {
        this.codigos = codigos;
    }

    public int getDls() {
        return dls;
    }

    public void setDls(int dls) {
        this.dls = dls;
    }
    
    
    public void generarCodigo() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (3) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setEncargoCodigo(rs.getString("encacodigobarra"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarCodigoDespacho() throws SQLException {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (4) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    +"   order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setDespacho(rs.getString("encacodigobarra"));      
            }
           
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }finally{
             rs.close();
            sentencia.close();
        }
    }
    
    public void generarFolioRendicion() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 rendfolio from mv_rendicion\n"
                    + "inner join ma_usuario on ma_usuario.usuacodigo=mv_rendicion.usuacodigocrea\n"
                    + "where ma_usuario.usuausuario=\"MXVS\" order by rendfolio desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setFolioRendicion(rs.getString("rendfolio"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarEntrega() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (7) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setEntrega(rs.getString("odflcodigo"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarRendicionEntrega() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (9) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.compcodigoentrega=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setRendicionEntrega(rs.getString("odflcodigo"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    
    public void generarComprobanteEntrega() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (7) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.compcodigoentrega=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setRendicionEntrega(rs.getString("odflcodigo"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarRendicionComprobanteEntrega() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (7) \n"
                    + "  AND A.tentcodigo=2 \n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.compcodigoentrega!=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setRendicionEntrega(rs.getString("odflcodigo"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarDecomiso() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "and a.eprocodigo in (7) \n"
                    + "and b.nomicodigoactual=0\n"
                    + "and a.compcodigoentrega=0\n"
                    + "and a.odflfechemicorta>='2017-12-01'\n"
                    + "and a.agencodigodestino= 1467\n"
                    + "and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "order by b.odflcodigo desc\n");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setRendicionEntrega(rs.getString("odflcodigo"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarDespachoReparto() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (8) \n"
                    + "  and a.tentcodigo=2 \n"
                    //+ "  AND a.tpagcodigo in (2,3)\n"
                    + "  AND a.tpagcodigo=1\n"
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflcodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setDespachoReparto(rs.getString("encacodigobarra"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarRendicionDevolucion() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (9) \n"
                    + "  and a.tentcodigo=2 \n"
                    /*+ "  AND a.tpagcodigo in (2,3)\n"*/
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by a.odflfechemicorta desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setDespachoReparto(rs.getString("odflcodigo"));      
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarDocumentoTributario() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.nomicodigoactual\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (9)\n"
                    + "  AND a.tentcodigo=2\n"
                    + "  AND a.tpagcodigo=3\n"
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2017-12-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.nomicodigoactual desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setDocumentosTributarios(rs.getString("nomicodigoactual"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarAnulacionEncargo() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo, b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (7)\n"
                    + "  AND a.tentcodigo=2\n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.odflfechemicorta>='2017-12-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.odflcodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setAnulacion(rs.getString("odflcodigo"));
                setAnulacionEncargo(rs.getString("encacodigobarra"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarAnulacion() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.odflcodigo,b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (3)\n"
                    + "  AND a.tentcodigo=2\n"
                    + "  and b.nomicodigoactual=0\n"
                    + "  and a.odflfechemicorta>='2018-01-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.odflcodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setAnulacion(rs.getString("odflcodigo"));
                setAnulacionEncargo(rs.getString("encacodigobarra"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }

    public void generarNominaReparto() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.nomicodigoactual, b.encacodigobarra\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (8)\n"
                    + "  AND a.tentcodigo=2\n"
                    + "  AND a.tpagcodigo=3\n"
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2017-12-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.nomicodigoactual desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setModificarNomina(rs.getString("nomicodigoactual"));
                setModificar(rs.getString("encacodigobarra"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarCierre() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.nomicodigoactual\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (1)\n"
                    + "  AND a.tentcodigo=2\n"
                    /*+ "  AND A.tpagcodigo=1\n"*/
                    + "  and b.nomicodigoactual!=0\n"
                    + "  and a.odflfechemicorta>='2017-12-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.nomicodigoactual desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setCierre(rs.getString("nomicodigoactual"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarNominaContenedora() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 a.nomicodigo\n"
                    + "from mv_nomina a, ma_tipo_nomina b\n"
                    + "where a.tnomcodigo=b.tnomcodigo\n"
                    + "  and a.nomifechacreacion>='2017-01-01'\n"
                    + "  and b.tnomiscontenedor=1\n"
                    + "  and b.tnomversion>0\n"
                    + "order by a.nomicodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setNominaContenedora(rs.getString("nomicodigo"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void modificarNomina() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 b.nomicodigoactual\n"
                    + "from mv_orden_flete a, mv_encargo b\n"
                    + "where a.odflcodigo=b.odflcodigo\n"
                    + "  and a.eprocodigo in (6)\n"
                    + "  AND A.tentcodigo=2\n"
                    + "  and a.odflfechemicorta>='2017-12-01'\n"
                    + "  and a.agencodigodestino= 1467\n"
                    + "  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)\n"
                    + "  order by b.nomicodigoactual desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setModificarNomina(rs.getString("nomicodigoactual"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }

    public void generarReclamo() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 reclfolio\n"
                    + "from mv_reclamo\n"
                    + "order by reclcodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setReclamo(rs.getString("reclfolio"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }   
    
    public void generarMemo() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 ef.efinfoliomemo\n"
                    + "from mv_emision_finiquito as ef\n"
                    + "inner join mv_reclamo as r on ef.reclcodigo=r.reclcodigo\n"
                    + "order by ef.efinfechacrea desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setReclamo(rs.getString("efinfoliomemo"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarMemosNonimas() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("SELECT first 1 e.echefoliomemo from mv_reclamo r\n"
                    + "INNER JOIN mv_envio_cheque e ON r.reclcodigo = e.reclcodigo\n"
                    + "order by e.echecodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setReclamo(rs.getString("echefoliomemo"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
    
    public void generarRezago() {
        String query;
        ConnIfx conexion = ConnIfx.getInstancia();
        Statement sentencia = null;
        PreparedStatement prpSentencia = null;
        ResultSet rs = null;
        try {
            query = ("select first 1 o.odflcodigo, b.encacodigobarra\n"
                    + "from mv_orden_flete o, of_estado_pe ope, mv_encargo b\n"
                    + "where o.odfltotalencargos=1"
                    + "and b.odflcodigo=o.odflcodigo\n"
                    + "and o.odflcodigo=ope.num_orden\n"
                    + "and o.eprocodigo=7\n"
                    + "and o.agencodigodestino=1467\n"
                    + "order by o.odflcodigo desc");
            sentencia = conexion.getConexion().createStatement();
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                setCodigos(rs.getString("odflcodigo"));
                setEncargoCodigo(rs.getString("encacodigobarra"));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException ex) {
            System.out.println("Fallo en BD: " + ex);
        }
    }
}