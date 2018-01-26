package Controlador;

import Modelo.ConnMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.stage.PopupWindow;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SeleniumController {

    private WebDriver driver;
    private String baseUrl, funcionalidad;
    Execution evidenciaEjecucion = new Execution();

    public SeleniumController() {
    }

    public SeleniumController(WebDriver driver, String baseUrl, String funcionalidad) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.funcionalidad = funcionalidad;
    }
    
    public Execution getEvidenciaEjecucion() {
        return evidenciaEjecucion;
    }

    public void setEvidenciaEjecucion(Execution evidenciaEjecucion) {
        this.evidenciaEjecucion = evidenciaEjecucion;
    }

    public String getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }
    
    
    public void setUp(String rutaEvidencia) throws Exception {//Módulos
        evidenciaEjecucion.setPathOriginal(rutaEvidencia);
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Inovabiz\\Desktop\\QA\\drivers\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\dlsclient\\Desktop\\Carpeta\\drivers\\drivers\\geckodriver.exe");
        //System.setProperty("phantomjs.binary.path", "C:\\Users\\Inovabiz\\Desktop\\QA\\drivers\\drivers\\phantomjs.exe");
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        //driver = new PhantomJSDriver();
        System.out.println("=== LEVANTANDO EL BROWSER ===");
        baseUrl = "http://cargaqa.starken.cl";/*http://dlsqa.starken.cl/";*//*"http://cargaqa.dls.cl";*/
        System.out.println("define url");
        System.out.println("REDIRECCIONANDO....");
        driver.get(baseUrl);
        
    }

    public void setUpWeb(String rutaEvidencia) throws Exception { //Para asistencia en viajes
        evidenciaEjecucion.setPathOriginal(rutaEvidencia);
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\manuelfvalles\\Documents\\drivers\\drivers\\chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\dlsclient\\Desktop\\Carpeta\\drivers\\drivers\\geckodriver.exe");
        //System.setProperty("phantomjs.binary.path", "C:\\Users\\Inovabiz\\Desktop\\QA\\drivers\\drivers\\phantomjs.exe");
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new PhantomJSDriver();	
        System.out.println("=== LEVANTANDO EL BROWSER ===");
        baseUrl = "http://emisionwebqa.starken.cl"; 
        System.out.println("define url");
        System.out.println("REDIRECCIONANDO....");
        driver.get(baseUrl);
    }
    
    public void producto(String producto) throws Exception {               
        switch(producto){
            case "Emisión Normal":
                ProcesoPrincipal emisionNormal = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal()+"\\Proceso DLS"+" "+evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: "+evidenciaEjecucion.getPathOriginal());
                setFuncionalidad("Emisión Normal");
                emisionNormal.setEvidenciaEjecucion(evidenciaEjecucion);
                emisionNormal.setDriver(driver);
                emisionNormal.executeTest();                
                break;
            case "Emisión Retiro a Domicilio":
                ProcesoPrincipal retiroDomicilio = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                retiroDomicilio.setEvidenciaEjecucion(evidenciaEjecucion);
                retiroDomicilio.setDriver(driver);
                retiroDomicilio.executeTest();
                break;
            case "Emisión Contingente":
                ProcesoPrincipal emisionContingente = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                emisionContingente.setEvidenciaEjecucion(evidenciaEjecucion);
                emisionContingente.setDriver(driver);
                emisionContingente.executeTest();
                break;
            case "Retiro dummy":
                ProcesoPrincipal retiroDummy = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                retiroDummy.setEvidenciaEjecucion(evidenciaEjecucion);
                retiroDummy.setDriver(driver);
                retiroDummy.executeTest();
                break;
            case "Emisión Nómina Agencia":
                ProcesoPrincipal nominaAgencia = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                nominaAgencia.setEvidenciaEjecucion(evidenciaEjecucion);
                nominaAgencia.setDriver(driver);
                nominaAgencia.executeTest();
                break;
            case "Despachar Nomina Agencia":
                ProcesoPrincipal despacharNominaAgencia = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                despacharNominaAgencia.setEvidenciaEjecucion(evidenciaEjecucion);
                despacharNominaAgencia.setDriver(driver);
                despacharNominaAgencia.executeTest();
                break;
            case "Recepción Nominas":
                ProcesoPrincipal recepcionNominas = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                recepcionNominas.setEvidenciaEjecucion(evidenciaEjecucion);
                recepcionNominas.setDriver(driver);
                recepcionNominas.executeTest();
                break;
            case "Entrega agencia":
                ProcesoPrincipal entregaAgencia = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                entregaAgencia.setEvidenciaEjecucion(evidenciaEjecucion);
                entregaAgencia.setDriver(driver);
                entregaAgencia.executeTest();
                break;
            case "Rendición comprobantes":
                ProcesoPrincipal rendicionComprobantes = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                rendicionComprobantes.setEvidenciaEjecucion(evidenciaEjecucion);
                rendicionComprobantes.setDriver(driver);
                rendicionComprobantes.executeTest();
                break;
            case "Emisión nomina de reparto":
                ProcesoPrincipal NominaReparto = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                NominaReparto.setEvidenciaEjecucion(evidenciaEjecucion);
                NominaReparto.setDriver(driver);
                NominaReparto.executeTest();
                break;
            case "Despacho nomina reparto":
                ProcesoPrincipal despachoNominaReparto = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                despachoNominaReparto.setEvidenciaEjecucion(evidenciaEjecucion);
                despachoNominaReparto.setDriver(driver);
                despachoNominaReparto.executeTest();
                break;
            case "Rendicion y devolucion nominas de reparto":
                ProcesoPrincipal rendicionDevolucion = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                rendicionDevolucion.setEvidenciaEjecucion(evidenciaEjecucion);
                rendicionDevolucion.setDriver(driver);
                rendicionDevolucion.executeTest();
                break;
            case "Emisión documentos tributarios reparto":
                ProcesoPrincipal documentosTributarios = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                documentosTributarios.setEvidenciaEjecucion(evidenciaEjecucion);
                documentosTributarios.setDriver(driver);
                documentosTributarios.executeTest();
                break;
            case "Liquidacion nomina":
                ProcesoPrincipal liquidacionNomina = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                liquidacionNomina.setEvidenciaEjecucion(evidenciaEjecucion);
                liquidacionNomina.setDriver(driver);
                liquidacionNomina.executeTest();
                break;
            case "Asignación de documentos":
                ProcesoPrincipal asignacionDocumentos = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                setFuncionalidad("Asignación de documentos");
                asignacionDocumentos.setEvidenciaEjecucion(evidenciaEjecucion);
                asignacionDocumentos.setDriver(driver);
                asignacionDocumentos.executeTest();                
                break;
            case "Apertura de caja":
                ProcesoPrincipal aperturaCaja = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                setFuncionalidad("Apertura de caja");
                aperturaCaja.setEvidenciaEjecucion(evidenciaEjecucion);
                aperturaCaja.setDriver(driver);
                aperturaCaja.executeTest();                
                break;
            case "Cierre de caja":
                ProcesoPrincipal cierreCaja = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                cierreCaja.setEvidenciaEjecucion(evidenciaEjecucion);
                cierreCaja.setDriver(driver);
                cierreCaja.executeTest();
                break;
            case "Nominacion Sorter":
                ProcesoPrincipal nominacionSorter = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                nominacionSorter.setEvidenciaEjecucion(evidenciaEjecucion);
                nominacionSorter.setDriver(driver);
                nominacionSorter.executeTest();
                break;
            case "Despacho nomina contenedora":
                ProcesoPrincipal despachoNominaContenedora = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                despachoNominaContenedora.setEvidenciaEjecucion(evidenciaEjecucion);
                despachoNominaContenedora.setDriver(driver);
                despachoNominaContenedora.executeTest();
                break;
            case "Modificar despacho nomina":
                ProcesoPrincipal modificarDespachoNomina = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                modificarDespachoNomina.setEvidenciaEjecucion(evidenciaEjecucion);
                modificarDespachoNomina.setDriver(driver);
                modificarDespachoNomina.executeTest();
                break;
            case "Anulación":
                ProcesoPrincipal anulacion = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                anulacion.setEvidenciaEjecucion(evidenciaEjecucion);
                anulacion.setDriver(driver);
                anulacion.executeTest();
                break;
            case "Anulación comprobante de entrega":
                ProcesoPrincipal anulacionComprobanteEntrega = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                anulacionComprobanteEntrega.setEvidenciaEjecucion(evidenciaEjecucion);
                anulacionComprobanteEntrega.setDriver(driver);
                anulacionComprobanteEntrega.executeTest();
                break;
            case "Entrega contingente":
                ProcesoPrincipal entregaContingente = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                entregaContingente.setEvidenciaEjecucion(evidenciaEjecucion);
                entregaContingente.setDriver(driver);
                entregaContingente.executeTest();
                break;
            case "Redestinación destinatario":
                ProcesoPrincipal redestinacionDestinatario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                redestinacionDestinatario.setEvidenciaEjecucion(evidenciaEjecucion);
                redestinacionDestinatario.setDriver(driver);
                redestinacionDestinatario.executeTest();
                break;
            case "Redestinación agencia":
                ProcesoPrincipal redestinacionAgencia = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                redestinacionAgencia.setEvidenciaEjecucion(evidenciaEjecucion);
                redestinacionAgencia.setDriver(driver);
                redestinacionAgencia.executeTest();
                break;
            case "Redestinación agencia/destinatario":
                ProcesoPrincipal redestinacionAgenciaDestinatario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                redestinacionAgenciaDestinatario.setEvidenciaEjecucion(evidenciaEjecucion);
                redestinacionAgenciaDestinatario.setDriver(driver);
                redestinacionAgenciaDestinatario.executeTest();
                break;
            case "Modificar nomina reparto":
                ProcesoPrincipal modificarNominaReparto = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                modificarNominaReparto.setEvidenciaEjecucion(evidenciaEjecucion);
                modificarNominaReparto.setDriver(driver);
                modificarNominaReparto.executeTest();
                break;
            case "Ingreso de reclamo":
                ProcesoPrincipal ingresoReclamo = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                ingresoReclamo.setEvidenciaEjecucion(evidenciaEjecucion);
                ingresoReclamo.setDriver(driver);
                ingresoReclamo.executeTest();
                break;
            case "Recepcion reclamo":
                ProcesoPrincipal recepcionReclamo = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                recepcionReclamo.setEvidenciaEjecucion(evidenciaEjecucion);
                recepcionReclamo.setDriver(driver);
                recepcionReclamo.executeTest();
                break;
            case "Generacion de informe ejecutivo":
                ProcesoPrincipal informeEjecutivo = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                informeEjecutivo.setEvidenciaEjecucion(evidenciaEjecucion);
                informeEjecutivo.setDriver(driver);
                informeEjecutivo.executeTest();
                break;
            case "Comite reclamo resolucion":
                ProcesoPrincipal reclamoResolucion = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                reclamoResolucion.setEvidenciaEjecucion(evidenciaEjecucion);
                reclamoResolucion.setDriver(driver);
                reclamoResolucion.executeTest();
                break;
            case "Generacion despacho de carta":
                ProcesoPrincipal despachoCarta = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                despachoCarta.setEvidenciaEjecucion(evidenciaEjecucion);
                despachoCarta.setDriver(driver);
                despachoCarta.executeTest();
                break;
            case "Cierre orden flete":
                ProcesoPrincipal cierreOF = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                cierreOF.setEvidenciaEjecucion(evidenciaEjecucion);
                cierreOF.setDriver(driver);
                cierreOF.executeTest();
                break;
            case "Ingreso decomiso":
                ProcesoPrincipal ingresoDecomiso = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                ingresoDecomiso.setEvidenciaEjecucion(evidenciaEjecucion);
                ingresoDecomiso.setDriver(driver);
                ingresoDecomiso.executeTest();
                break;
            case "Autorizar la solicitud de decomiso":
                ProcesoPrincipal autorizarDecomiso = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                autorizarDecomiso.setEvidenciaEjecucion(evidenciaEjecucion);
                autorizarDecomiso.setDriver(driver);
                autorizarDecomiso.executeTest();
                break;
            case "Generacion acta decomiso":
                ProcesoPrincipal actaDecomiso = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                actaDecomiso.setEvidenciaEjecucion(evidenciaEjecucion);
                actaDecomiso.setDriver(driver);
                actaDecomiso.executeTest();
                break;
            case "Ejecucion cierre de decomiso":
                ProcesoPrincipal cierreDecomiso = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                cierreDecomiso.setEvidenciaEjecucion(evidenciaEjecucion);
                cierreDecomiso.setDriver(driver);
                cierreDecomiso.executeTest();
                break;
            case "Recepcion nominas contenedoras":
                ProcesoPrincipal nominasContenedoras = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                nominasContenedoras.setEvidenciaEjecucion(evidenciaEjecucion);
                nominasContenedoras.setDriver(driver);
                nominasContenedoras.executeTest();
                break;
            case "Ingreso a bodega 3D":
                ProcesoPrincipal ingresoBodega = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                ingresoBodega.setEvidenciaEjecucion(evidenciaEjecucion);
                ingresoBodega.setDriver(driver);
                ingresoBodega.executeTest();
                break;
            case "Emision del finiquito y solicitud de cheque":
                ProcesoPrincipal finiquito = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                finiquito.setEvidenciaEjecucion(evidenciaEjecucion);
                finiquito.setDriver(driver);
                finiquito.executeTest();
                break;
            case "Generacion de solicitud de cheque":
                ProcesoPrincipal solicitudCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                solicitudCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                solicitudCheque.setDriver(driver);
                solicitudCheque.executeTest();
                break;
            case "Recepcion de cheques":
                ProcesoPrincipal recepcionCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                recepcionCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                recepcionCheque.setDriver(driver);
                recepcionCheque.executeTest();
                break;
            case "Apelacion de reclamos":
                ProcesoPrincipal apelacionCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                apelacionCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                apelacionCheque.setDriver(driver);
                apelacionCheque.executeTest();
                break;
            case "Envio de cheques a agencias":
                ProcesoPrincipal envioCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                envioCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                envioCheque.setDriver(driver);
                envioCheque.executeTest();
                break;
            case "Recepcion de cheques en agencia":
                ProcesoPrincipal recepcionChequeAgencia = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                recepcionChequeAgencia.setEvidenciaEjecucion(evidenciaEjecucion);
                recepcionChequeAgencia.setDriver(driver);
                recepcionChequeAgencia.executeTest();
                break;
            case "Gestion de cheques":
                ProcesoPrincipal gestionCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                gestionCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                gestionCheque.setDriver(driver);
                gestionCheque.executeTest();
                break;
            case "Entrega de cheques":
                ProcesoPrincipal entregaCheque = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                entregaCheque.setEvidenciaEjecucion(evidenciaEjecucion);
                entregaCheque.setDriver(driver);
                entregaCheque.executeTest();
                break;
            case "Seguimiento reclamo":
                ProcesoPrincipal seguimientoReclamo = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                seguimientoReclamo.setEvidenciaEjecucion(evidenciaEjecucion);
                seguimientoReclamo.setDriver(driver);
                seguimientoReclamo.executeTest();
                break;
            case "Re-impresion de documentos":
                ProcesoPrincipal reimpresionDocumentos = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Proceso DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                reimpresionDocumentos.setEvidenciaEjecucion(evidenciaEjecucion);
                reimpresionDocumentos.setDriver(driver);
                reimpresionDocumentos.executeTest();
                break;
            case "Anulacion de emision web":
                ProcesoPrincipal anulacionEmisionWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                setFuncionalidad("Anulacion de emision web");
                System.out.println(getFuncionalidad());
                anulacionEmisionWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                anulacionEmisionWeb.setDriver(driver);               
                anulacionEmisionWeb.executeTestWeb();
                break;
            case "Reimpresion web":
                ProcesoPrincipal reimpresionWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                reimpresionWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                reimpresionWeb.setDriver(driver);
                reimpresionWeb.executeTestWeb();
                break;
            case "Pre emision masiva web":
                ProcesoPrincipal preEMisionWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                preEMisionWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                preEMisionWeb.setDriver(driver);
                preEMisionWeb.executeTestWeb();
                break;
            case "Emision manual web":
                ProcesoPrincipal emisionManualWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                emisionManualWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                emisionManualWeb.setDriver(driver);
                emisionManualWeb.executeTestWeb();
                break;
            case "Transformador masivo web":
                ProcesoPrincipal transformadorWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                transformadorWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                transformadorWeb.setDriver(driver);
                transformadorWeb.executeTestWeb();
                break;
            case "Seguimiento multiple web":
                ProcesoPrincipal seguimientoWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                seguimientoWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                seguimientoWeb.setDriver(driver);
                seguimientoWeb.executeTestWeb();
                break;
            case "Reimpresion masiva web":
                ProcesoPrincipal reimpresionMasivaWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                reimpresionMasivaWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                reimpresionMasivaWeb.setDriver(driver);
                reimpresionMasivaWeb.executeTestWeb();
                break;
            case "Nominacion web":
                ProcesoPrincipal nominacionWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                nominacionWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                nominacionWeb.setDriver(driver);
                nominacionWeb.executeTestWeb();
                break;
            case "Pendientes de emision web":
                ProcesoPrincipal pendientesEmisionWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                pendientesEmisionWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                pendientesEmisionWeb.setDriver(driver);
                pendientesEmisionWeb.executeTestWeb();
                break;
            case "Mantenedor":
                ProcesoPrincipal mantenedorWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                mantenedorWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                mantenedorWeb.setDriver(driver);
                mantenedorWeb.executeTestWeb();
                break;    
            case "Emision masiva web":
                ProcesoPrincipal emisionMasivaWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                emisionMasivaWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                emisionMasivaWeb.setDriver(driver);
                emisionMasivaWeb.executeTestWeb();
                break;
            case "Cobertura web":
                ProcesoPrincipal coberturaWeb = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\Emision Web" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                coberturaWeb.setEvidenciaEjecucion(evidenciaEjecucion);
                coberturaWeb.setDriver(driver);
                coberturaWeb.executeTestWeb();
                break;
            case "Anulacion comprobante entrega":
                ProcesoPrincipal anulacionComprobante = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                anulacionComprobante.setEvidenciaEjecucion(evidenciaEjecucion);
                anulacionComprobante.setDriver(driver);
                anulacionComprobante.executeTest();
                break;
            case "Rezago":
                ProcesoPrincipal rezago = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                rezago.setEvidenciaEjecucion(evidenciaEjecucion);
                rezago.setDriver(driver);
                rezago.executeTest();
                break;    
            case "Crear inventario":
                ProcesoPrincipal crearInventario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                crearInventario.setEvidenciaEjecucion(evidenciaEjecucion);
                crearInventario.setDriver(driver);
                crearInventario.executeTest();
                break;
            case "Asignar usuario":
                ProcesoPrincipal asignarUsuario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                asignarUsuario.setEvidenciaEjecucion(evidenciaEjecucion);
                asignarUsuario.setDriver(driver);
                asignarUsuario.executeTest();
                break;
            case "Tomar inventario":
                ProcesoPrincipal tomarInventario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                tomarInventario.setEvidenciaEjecucion(evidenciaEjecucion);
                tomarInventario.setDriver(driver);
                tomarInventario.executeTest();
                break;
            case "Cierre inventario":
                ProcesoPrincipal cierreInventario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                cierreInventario.setEvidenciaEjecucion(evidenciaEjecucion);
                cierreInventario.setDriver(driver);
                cierreInventario.executeTest();
                break;
            case "Listar inventario":
                ProcesoPrincipal listarInventario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                listarInventario.setEvidenciaEjecucion(evidenciaEjecucion);
                listarInventario.setDriver(driver);
                listarInventario.executeTest();
                break;  
            case "Consulta inventario":
                ProcesoPrincipal consultaInventario = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                consultaInventario.setEvidenciaEjecucion(evidenciaEjecucion);
                consultaInventario.setDriver(driver);
                consultaInventario.executeTest();
                break;  
            case "Control aduana":
                ProcesoPrincipal controlAduana = new ProcesoPrincipal();
                evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal() + "\\DLS" + " " + evidenciaEjecucion.getFechaEjecucion().toString());
                System.out.println("Ruta: " + evidenciaEjecucion.getPathOriginal());
                controlAduana.setEvidenciaEjecucion(evidenciaEjecucion);
                controlAduana.setDriver(driver);
                controlAduana.executeTest();
                break;   
        }
    }//Ejecuta universo de pruebas.
    
    public void test(String nombreTest) throws Exception {        
        ProcesoPrincipal test = new ProcesoPrincipal();
        evidenciaEjecucion.setPathOriginal(evidenciaEjecucion.getPathOriginal()+"\\Test"+" "+evidenciaEjecucion.getFechaEjecucion().toString());
        System.out.println("Ruta: "+evidenciaEjecucion.getPathOriginal());
        test.setEvidenciaEjecucion(evidenciaEjecucion);
        test.setDriver(driver);
        test.executeTestByName(nombreTest);
    }//Ejecuta un test en específico.
}
