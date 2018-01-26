package Controlador;

import Controlador.Execution;
import Controlador.LogCSV;
import Modelo.GestorBD;
import com.csvreader.CsvWriter;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import static java.awt.event.InputEvent.BUTTON1_MASK;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcesoPrincipal {//Contiene todos los métodos a ejecutar
    //Variebles globales
    SeleniumController selenium = new SeleniumController();
    WebDriver driver = null;
    private final int DLS= 20;
    private final int WEB = 55;
    GestorBD gestorBD = new GestorBD();
    private int STATUS = 0; 
    Execution evidenciaEjecucion;
    private int CONTEO=0, precondicionEjecutar, numPasos, csv;
    private boolean leerPDF;
    LogCSV log = new LogCSV();
    String encargo, modificarNominaReparto;
    Informix informix = new Informix();
    
    
    
    public ProcesoPrincipal() {
    }

    public Execution getEvidenciaEjecucion() {
        return evidenciaEjecucion;
    }

    public void setEvidenciaEjecucion(Execution evidenciaEjecucion) {
        this.evidenciaEjecucion = evidenciaEjecucion;
    }
   
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public int getPrecondicionEjecutar() {
        return precondicionEjecutar;
    }

    public void setPrecondicionEjecutar(int precondicionEjecutar) {
        this.precondicionEjecutar = precondicionEjecutar;
    }

    public int getNumPasos() {
        return numPasos;
    }

    public void setNumPasos(int numPasos) {
        this.numPasos = numPasos;
    }

    public boolean isLeerPDF() {
        return leerPDF;
    }

    public void setLeerPDF(boolean leerPDF) {
        this.leerPDF = leerPDF;
    }

    public String getEncargo() {
        return encargo;
    }

    public void setEncargo(String encargo) {
        this.encargo = encargo;
    }

    public String getModificarNominaReparto() {
        return modificarNominaReparto;
    }

    public void setModificarNominaReparto(String modificarNominaReparto) {
        this.modificarNominaReparto = modificarNominaReparto;
    } 
    

    private void preCondicion() {//precondición para acceder a DLS
        try {
            String parent = driver.getWindowHandle();

            Set<String> pops = driver.getWindowHandles();{
                Iterator<String> it = pops.iterator();
                while (it.hasNext()) {

                    String popupHandle = it.next().toString();
                    if (!popupHandle.contains(parent)) {
                        driver.switchTo().window(popupHandle);
                        System.out.println("Nombre de ventana: "+driver.switchTo().window(popupHandle).getTitle());
                    }
                }
            }
            evidenciaEjecucion.creaCarpetaDeLanzamiento();
            Thread.sleep(3000);
            System.out.println("=== REALIZANDO LOGIN ===");
            Thread.sleep(3000);
            driver.findElement(By.id("usuario")).clear();
            Thread.sleep(1000);
            driver.findElement(By.id("usuario")).sendKeys("MXVS");
            Thread.sleep(1000);
            driver.findElement(By.id("clave")).clear();
            Thread.sleep(1000);
            driver.findElement(By.id("clave")).sendKeys("1234");
            Thread.sleep(1000);
            driver.findElement(By.name("button")).click();
            Thread.sleep(4000);
            driver.get("http://cargaqa.starken.cl/ddls/emision/nueva/inicio.do");
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.err.println("FALLO EN EJECUCION DE LOGIN :" + ex);
            STATUS = 1;
        }
    }
    
    private void preCondicion2() {//precondición para acceder a emisión web
        try {
            evidenciaEjecucion.creaCarpetaDeLanzamiento();
            System.out.println("=== REALIZANDO LOGIN ===");
            Thread.sleep(3000);
            driver.findElement(By.id("formlogin:rutUsuario")).clear();
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:rutUsuario")).sendKeys("13130387-4");
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:rutEmpresa")).clear();
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:rutEmpresa")).sendKeys("76211240-K");
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:password")).clear();
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:password")).sendKeys("1234");
            Thread.sleep(1000);
            driver.findElement(By.id("formlogin:btnEnviar")).click();
            Thread.sleep(3000);
        } catch (Exception ex) {
            System.err.println("FALLO EN EJECUCION DE LOGIN :" + ex);
            STATUS = 1;
        }
    }
    
    
    /***
     * todo: ARMAR secuencia completa en BD
     * @throws InterruptedException 
     */
    
    /* ESTE FLUJO CORRESPONDE AL FLUJO PRINCIPAL (CORRESPONDE A LA EJECUCION PRINCIPAL DEL PROCESO   */
    public void executeTest() throws InterruptedException, IOException, AWTException {//Ejecuta los casos DLS
        preCondicion();
        if (STATUS == 0) {
            StringBuffer sLog = new StringBuffer();
            ArrayList<Test> casos = gestorBD.getPruebasAEjecutar(DLS); //Llama los casos pertenecientes a un escenario
            System.out.println("Ejecutando Casos del Escenario: Proceso DLS\n");
            csv=0;
            for (int i = 0; i < casos.size(); i++) { // AL FINALIZAR UN CASO DE PRUEBA, VUELVE A ENTRAR A ESTE FOR ....
                
                Test test = casos.get(i);//
                evidenciaEjecucion.creaCarpetaDePrueba(test.getNombre());//Colocar el nombre de la carpeta en la cual contiene las evidencias
                System.out.println("Prueba: " + test.getNombre());//Nombre de la prueba
                System.out.println("Id: " + test.getId());// ID de la prueba (ID que tiene asignado en BD)
                System.out.println("Nombre: " + test.getNombre());//Nombre de la prueba
                System.out.println("Fecha: " + test.getFechaEjecucion());// Fecha de ejecución
                System.out.println("Descripcion: " + test.getDescripción());//Descripción 
                System.out.println("Escenario: " + test.getEscenario());// 
                log.setNombreTest(test.getNombre());//log en excel
                log.setFechaEjecucionTest(test.getFechaEjecucion());//log en excel
                ArrayList<Step> pasos = gestorBD.getPasosAEjecutarPorPrueba(test.getId());//Los pasos a ejecutar por test
                System.out.println("---------------------Script de PASOS------------------------");
                int p = 1;
                //INICIO CONTEO DE TIEMPO DE PRUEBA
                Thread contador = lanzadorDeConteoTX();//INICIO DE CRONOMETRO (REGISTRA EL TIEMPO DE EJECUCIÓN)
                contador.start();
                for (int j = 0; j < pasos.size(); j++) {//Los pasos a ejecutar por test. pOR EJEMPLO: ESTE FOR EACH CORRESPONDE A CASO: VALIDA EDAD DEL ASEGURADO
                    boolean isOK = true;
                    Step paso = pasos.get(j);
                    getFoto(driver, test, paso);
                    System.out.println("Ejecutando paso: " + p);//número del paso
                    System.out.println("Descripcion paso: " + paso.getDescripcion());//descripción del paso
                    System.out.println("Localizador: " + paso.getTipoLocalizador());//localizador (ID,NAME,XPATH,CSS; etc,etc)
                    System.out.println("Sobre el campo: " + paso.getAtributo());//El campo en el cual se está ejecutando la acción
                    System.out.println("Buscando el elemento en la pagina");
                    WebElement elemento = getElemento(driver, paso.getTipoLocalizador(), paso.getAtributo());
                    if (elemento == null) {// Busca error Server Error in
                        sLog.append(getCausaNoSuchElement());
                        System.out.println("Causa: " + sLog.toString());
                        j = pasos.size() + 1;
                        isOK = false;
                    } else {
                        System.out.println("Ejecutando comando: " + paso.getComando());//Continua la prueba, ejecuta el comando (click, sendKeyns, etc)
                        System.out.println("Con el dato: " + paso.getValor());// Valor a operar
                        if (isEjecutaComando(paso.getComando(), elemento, paso.getValor())) {//Verica si se ejecuta todo el paso
                            System.out.println("Paso Ejecutado!");
                            System.out.println("");
                            System.out.println("---------------------------------------------------------\n");
                        } else {// Error si no se ejecuta el paso (elemento no está en la página, timeout, se cambió de posición, etc)
                            System.out.println("---------------------------------------------------------\n");
                            sLog.append("ERROR: ");
                            sLog.append("Al ejecutar el paso : " + paso.getDescripcion());//Indica el paso que no se pudo ejecutar
                            sLog.append("");
                            isOK = false;
                        }
                        getFoto(driver, test, paso);//Genero la evidencia
                        p++;//incrementa número 
                        setNumPasos(p);
                    }
                    test.setResultado(isOK);//VERICA SI EL CASO FUE ÉXITOSO
                    if (test.isResultado() == true) {
                        test.setObsResultado("Prueba finalizada con exito");
                    } else {
                        System.out.println("Error al ejecutar el test");
                        test.setObsResultado(sLog.toString());
                    }
                    log.setNumPaso(p);//LOG EXCEL
                    log.setDescripcionPaso(paso.getDescripcion());
                    log.setTipoLocalizadorPaso(paso.getTipoLocalizador());
                    log.setStatusPaso(isOK);
                    if (log.isStatusPaso() == true) {
                        log.setPasoEjecutado("Paso Ejecutado!");
                    } else {
                        log.setPasoEjecutado("Error al ejecutar el paso : " + paso.getDescripcion());
                    }
                    csv++;
                    log.setAtributoPaso(paso.getAtributo());
                    log.setComandoPaso(paso.getComando());
                    log.setValorPaso(paso.getValor());
                    crearLog2();
                }
                System.out.println("---------------------FINALIZA LA EJECUCION DEL CASO: " + test.getNombre() +" "+test.getObsResultado()+ "------------------------" + "\n");
                driver.switchTo().defaultContent();//SALIR DEL IFRAME
                
                contador.stop();//PARAR CRONOMETRO (REGISTRA EL TIEMPO DE EJECUCIÓN)
                System.out.println("tiempo: " + CONTEO + "seg");
                Resultado resultado = new Resultado();// GUARDAR RESULTADO EN BD
                resultado.setIdIteracion(p);
                resultado.setIdTestCase(test.getId());
                resultado.setObservaciones(test.getObsResultado());
                resultado.setResultado(test.isResultado());
                resultado.setFecha(test.getFechaEjecucion());
                resultado.setTiempotx(CONTEO);
                gestorBD.guardarResultado(resultado);
                CONTEO = 0;
            }
        }
    }
    public void executeTestByName(String nomTest) throws InterruptedException, IOException, AWTException {
        String cadena = nomTest;
        int p1 = cadena.indexOf("DLS");
        int p2 = cadena.indexOf("Web");
        
        if (p1 != -1) {
            preCondicion();//DLS   
        } else if(p2 != -1) {
            preCondicion2();//Emisión web
        } 
        boolean isOK = true;
        
        if (STATUS == 0) {
            StringBuffer sLog = new StringBuffer();
            Test test = gestorBD.getPruebasAEjecutarByName(nomTest);//Busca el caso de prueba a ejecutar
            System.out.println("Ejecutando Caso: " + test.getNombre() + "\n");
            evidenciaEjecucion.creaCarpetaDePrueba(test.getNombre());
            System.out.println("Prueba: " + test.getNombre());
            System.out.println("Id: " + test.getId());
            System.out.println("Nombre: " + test.getNombre());
            System.out.println("Fecha: " + test.getFechaEjecucion());
            System.out.println("Descripcion: " + test.getDescripción());
            log.setNombreTest(test.getNombre());
            log.setFechaEjecucionTest(test.getFechaEjecucion());
            ArrayList<Step> pasos = gestorBD.getPasosAEjecutarPorPrueba(test.getId());
            System.out.println("---------------------Script de PASOS------------------------");
            int p = 1;
            //INICIO CONTEO DE TIEMPO DE PRUEBA
            Thread contador = lanzadorDeConteoTX();
            contador.start();
            csv=0;
            for (int j = 0; j < pasos.size(); j++) {
                Step paso = pasos.get(j);
                getFoto(driver, test, paso);
                System.out.println("Ejecutando paso: " + p);
                System.out.println("Descripcion paso: " + paso.getDescripcion());
                System.out.println("Localizador: " + paso.getTipoLocalizador());
                System.out.println("Sobre el campo: " + paso.getAtributo());
                System.out.println("Buscando el elemento en la pagina");
                WebElement elemento = getElemento(driver, paso.getTipoLocalizador(), paso.getAtributo());
                if (elemento == null) {
                    sLog.append(getCausaNoSuchElement());
                    System.out.println("Causa: " + sLog.toString());
                    j = pasos.size() + 1;
                    isOK = false;
                } else {
                    System.out.println("Ejecutando comando: " + paso.getComando());
                    System.out.println("Con el dato: " + paso.getValor());
                    if (isEjecutaComando(paso.getComando(), elemento, paso.getValor())) {
                        System.out.println("Paso Ejecutado!");
                        System.out.println("");
                        System.out.println("---------------------------------------------------------\n");
                    } else {
                        System.out.println("---------------------------------------------------------\n");
                        sLog.append("ERROR: ");
                        sLog.append("Al ejecutar el paso : " + paso.getDescripcion());
                        sLog.append("");
                        isOK = false;
                    }
                    getFoto(driver, test, paso);
                    p++;
                    setNumPasos(p);
                }
                test.setResultado(isOK);
                log.setNumPaso(p);
                log.setDescripcionPaso(paso.getDescripcion());
                log.setTipoLocalizadorPaso(paso.getTipoLocalizador());
                log.setStatusPaso(isOK);
                if (log.isStatusPaso() == true) {
                    log.setPasoEjecutado("Paso Ejecutado!");
                } else {
                    log.setPasoEjecutado("Error al ejecutar el paso : " + paso.getDescripcion());
                }
                csv++;
                log.setAtributoPaso(paso.getAtributo());
                log.setComandoPaso(paso.getComando());
                log.setValorPaso(paso.getValor());
                crearLog2();
            }        
            System.out.println("---------------------FINALIZA LA EJECUCION DEL CASO: " + test.getNombre() + "------------------------" + "\n");
            if (isOK == true) {
                System.out.println("CASO DE PRUEBA OK!");
                test.setObsResultado("Prueba finalizada con exito");
            } else {
                System.err.println("CASO DE PRUEBA ERRONEO");
                test.setObsResultado("Error al ejecutar el test");
            }
            contador.stop();
            System.out.println("tiempo: " + CONTEO + "seg");
            Resultado resultado = new Resultado();
            resultado.setIdIteracion(p);
            resultado.setIdTestCase(test.getId());
            resultado.setObservaciones(test.getObsResultado());
            resultado.setResultado(test.isResultado());
            resultado.setFecha(test.getFechaEjecucion());
            resultado.setTiempotx(CONTEO);
            gestorBD.guardarResultado(resultado);
            CONTEO = 0;
        }     
    }
    
    public void executeTestWeb() throws InterruptedException, IOException, AWTException {//Ejecuta los casos DLS
        preCondicion2();
        if (STATUS == 0) {
            StringBuffer sLog = new StringBuffer();
            System.out.println(WEB);
            ArrayList<Test> casos = gestorBD.getPruebasAEjecutar(informix.getDls()); //Llama los casos pertenecientes a la funcionalidad seleccionada
            System.out.println("Ejecutando Casos del Escenario: Emision Web\n");
            csv = 0;
            for (int i = 0; i < casos.size(); i++) { // AL FINALIZAR UN CASO DE PRUEBA, VUELVE A ENTRAR A ESTE FOR ....
                Test test = casos.get(i);//
                evidenciaEjecucion.creaCarpetaDePrueba(test.getNombre());//Colocar el nombre de la carpeta en la cual contiene las evidencias
                System.out.println("Prueba: " + test.getNombre());//Nombre de la prueba
                System.out.println("Id: " + test.getId());// ID de la prueba (ID que tiene asignado en BD)
                System.out.println("Nombre: " + test.getNombre());//Nombre de la prueba
                System.out.println("Fecha: " + test.getFechaEjecucion());// Fecha de ejecución
                System.out.println("Descripcion: " + test.getDescripción());//Descripción 
                System.out.println("Escenario: " + test.getEscenario());// 
                log.setNombreTest(test.getNombre());//log en excel
                log.setFechaEjecucionTest(test.getFechaEjecucion());//log en excel
                ArrayList<Step> pasos = gestorBD.getPasosAEjecutarPorPrueba(test.getId());//Los pasos a ejecutar por test
                System.out.println("---------------------Script de PASOS------------------------");
                int p = 1;
                //INICIO CONTEO DE TIEMPO DE PRUEBA
                Thread contador = lanzadorDeConteoTX();//INICIO DE CRONOMETRO (REGISTRA EL TIEMPO DE EJECUCIÓN)
                contador.start();
                for (int j = 0; j < pasos.size(); j++) {//Los pasos a ejecutar por test. POR EJEMPLO: ESTE FOR EACH CORRESPONDE A CASO: VALIDA EDAD DEL ASEGURADO
                    boolean isOK = true;
                    Step paso = pasos.get(j);
                    getFoto(driver, test, paso);
                    System.out.println("Ejecutando paso: " + p);//número del paso
                    System.out.println("Descripcion paso: " + paso.getDescripcion());//descripción del paso
                    System.out.println("Localizador: " + paso.getTipoLocalizador());//localizador (ID,NAME,XPATH,CSS, etc,etc)
                    System.out.println("Sobre el campo: " + paso.getAtributo());//El campo en el cual se está ejecutando la acción
                    System.out.println("Buscando el elemento en la pagina");
                    WebElement elemento = getElemento(driver, paso.getTipoLocalizador(), paso.getAtributo());
                    if (elemento == null) {// Busca error Server Error in
                        sLog.append(getCausaNoSuchElement());
                        System.out.println("Causa: " + sLog.toString());
                        j = pasos.size() + 1;
                        isOK = false;
                    } else {
                        System.out.println("Ejecutando comando: " + paso.getComando());//Continua la prueba, ejecuta el comando (click, sendKeyns, etc)
                        System.out.println("Con el dato: " + paso.getValor());// Valor a operar
                        if (isEjecutaComando(paso.getComando(), elemento, paso.getValor())) {//Verica si se ejecuta todo el paso
                            System.out.println("Paso Ejecutado!");
                            System.out.println("");
                            System.out.println("---------------------------------------------------------\n");
                        } else {// Error si no se ejecuta el paso (elemento no está en la página, timeout, se cambió de posición, etc)
                            System.out.println("---------------------------------------------------------\n");
                            sLog.append("ERROR: ");
                            sLog.append("Al ejecutar el paso : " + paso.getDescripcion());//Indica el paso que no se pudo ejecutar
                            sLog.append("");
                            isOK = false;
                        }
                        getFoto(driver, test, paso);//Genero la evidencia
                        p++;//incrementa número 
                        setNumPasos(p);
                    }
                    test.setResultado(isOK);//VERICA SI EL CASO FUE ÉXITOSO
                    if (test.isResultado() == true) {
                        test.setObsResultado("Prueba finalizada con exito");
                    } else {
                        System.out.println("Error al ejecutar el test");
                        test.setObsResultado(sLog.toString());
                    }
                    log.setNumPaso(p);//LOG EXCEL
                    log.setDescripcionPaso(paso.getDescripcion());
                    log.setTipoLocalizadorPaso(paso.getTipoLocalizador());
                    log.setStatusPaso(isOK);
                    if (log.isStatusPaso() == true) {
                        log.setPasoEjecutado("Paso Ejecutado!");
                    } else {
                        log.setPasoEjecutado("Error al ejecutar el paso : " + paso.getDescripcion());
                    }
                    csv++;
                    log.setAtributoPaso(paso.getAtributo());
                    log.setComandoPaso(paso.getComando());
                    log.setValorPaso(paso.getValor());
                    crearLog2();
                }
                System.out.println("---------------------FINALIZA LA EJECUCION DEL CASO: " + test.getNombre() + " " + test.getObsResultado() + "------------------------" + "\n");

                contador.stop();//PARAR CRONOMETRO (REGISTRA EL TIEMPO DE EJECUCIÓN)
                System.out.println("tiempo: " + CONTEO + "seg");
                Resultado resultado = new Resultado();// GUARDAR RESULTADO EN BD
                resultado.setIdIteracion(p);
                resultado.setIdTestCase(test.getId());
                resultado.setObservaciones(test.getObsResultado());
                resultado.setResultado(test.isResultado());
                resultado.setFecha(test.getFechaEjecucion());
                resultado.setTiempotx(CONTEO);
                gestorBD.guardarResultado(resultado);
                CONTEO = 0;
            }
        }
    }
    
    
    /*  IMPORTANTE: CORRESPONDE A LOCALIZADOR DE CADA ELEMENTO DE LA PAGINA*/
    
    private WebElement getElemento(WebDriver webDriver, String tipo, String dato) throws MalformedURLException, IOException, InterruptedException {
        WebElement elemento = null; //Localizador
        WebDriverWait wait = null;
        try {
            switch (tipo) {
                case "ID":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;
                case "NAME":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(dato)));
                    break;
                case "XPATH":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;
                case "id1":
                    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[style='overflow: visible; height: 500px; width: 600px;']")));
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;
                case "id2":
                    driver.switchTo().defaultContent();
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;
                case "xpath2":
                    driver.navigate().back();
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;
                case "CSS":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(dato)));
                    break;
                case "name1":
                    driver.switchTo().frame("Embpage1");
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(dato)));
                    break;
                case "xpath1":
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;
                case "xpath3":
                    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[style='overflow: visible; height: 290px; width: 700px;']")));
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;
                case "xpath4":
                    driver.switchTo().defaultContent();
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dato)));
                    break;    
                case "id3":
                    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[style='height: 150px; width: 400px;']")));
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;
                case "id4":
                    driver.switchTo().frame("GB_frame").switchTo().frame("GB_frame");
                    wait = new WebDriverWait(webDriver, 30);
                    elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dato)));
                    break;   
            }
        } catch (TimeoutException ex) {
            System.out.println("Error: se ha producido una demora mayor a la esperada (30seg)" + " " + dato);
            //driver.close();
            return null;
        }
        return elemento;
    }
    
    /*  IMPORTANTE: CORRESPONDE A LA ACCIÓN DE CADA ELEMENTO DE LA PAGINA*/
    private boolean isEjecutaComando(String comando, WebElement elemento, String dato) throws InterruptedException, AWTException {
        boolean i = false;   //Comandos a ejecutar
        Robot robot = new Robot();
        Actions builder = new Actions(driver);
        String m=null;
        switch (comando) {
            case "sendKeys":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                elemento.sendKeys(dato);//Rescata datos de la tabla "data"
                Thread.sleep(3000);
                i = true;
                break;
            case "sendKeys1":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                elemento.sendKeys(dato);//Rescata datos de la tabla "data"
                Thread.sleep(6000);
                i = true;
                break; 
            case "sendKeys2":
                elemento.clear();
                Thread.sleep(1000);
                System.out.println("Escribiendo..");
                elemento.sendKeys(dato);//Rescata datos de la tabla "data"
                Thread.sleep(3000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                i = true;
                break;
            case "select":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                new Select(elemento).selectByVisibleText(dato);//Rescata datos de la tabla "data"
                Thread.sleep(1000);
                i = true;
                break;
            case "select1":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                Thread.sleep(2000);
                new Select(elemento).selectByValue("1");//Rescata datos de la tabla "data"
                i = true;
                break;
            case "select2":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                new Select(elemento).selectByValue("1");//Rescata datos de la tabla "data"
                Thread.sleep(1000);
                i = true;
                break;
            case "select3":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                new Select(elemento).selectByValue("15");//Rescata datos de la tabla "data"
                Thread.sleep(1000);
                i = true;
                break;
            case "selectAgencia":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                new Select(elemento).selectByValue("1467");//Rescata datos de la tabla "data"
                Thread.sleep(1000);
                i = true;
                break; 
            case "selectUsuario":
                System.out.println("Seleccionando..");
                Thread.sleep(1000);
                new Select(elemento).selectByValue("6241");//Rescata datos de la tabla "data"
                Thread.sleep(1000);
                i = true;
                break;    
            case "click":
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(2000);
                i = true;
                break;
            case "verifyText"://Verificar texto
                if (dato.equals(elemento.getText())) {//El texto contenido en la tabla "data" es igual al capturado en la página
                    System.out.println("Mensaje encontrado: " + dato);
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    i = true;
                } else {
                    System.out.println("Mensaje no encontrado!" + elemento.getText());
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    i = false;
                }
                Thread.sleep(1000);
                break;
            case "verifyText1":
                if (dato.equals(elemento.getText())) {//El texto contenido en la tabla "data" es igual al capturado en la página
                    System.out.println("Mensaje encontrado: " + dato);
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    i = true;
                } else {
                    System.out.println("Mensaje no encontrado!" + elemento.getText());
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    i = false;
                }
                Thread.sleep(1000);
                break;
            case "verifyText2"://Verificar texto
                if (dato.equals(elemento.getText())) {//El texto contenido en la tabla "data" es igual al capturado en la página
                    System.out.println("Error al guardar");
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    i = false;
                } else {
                    System.out.println("Transaccion exitosa");
                    Thread.sleep(2000);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    i = true;
                }
                Thread.sleep(1000);
                break;    
            case "clickAndWait"://Click para llevar a otra página
                System.out.println("Esperando");
                elemento.click();
                Thread.sleep(5000);
                i = true;
                break;
            case "click1"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(10000);
                i = true;
                break;
            case "click2"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(3000);
                i = true;
                break;
            case "click3"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(6000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                i = true;
                break;
            case "click4"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(3000);
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                Thread.sleep(2000);
                i = true;
                break;
            case "click5":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i=true;
                break;
			case "click6"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;	
			case "click7"://Click sin llevar a otra página
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break; 	
            case "click8"://Click presionando tecla F3
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                Teclado teclado = new Teclado();
                teclado.setTexto("prueba");
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                i = true;
                break;             
            case "clickAndWait1"://Click para llevar a otra página
                elemento.click();
                Thread.sleep(10000);
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                Thread.sleep(3000);
                i = true;
                break;
            case "clickAndWait2":
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                elemento.click();
                i = true;
                break;
            case "clickAndWait3":
                builder.moveToElement(elemento).perform();
                elemento.click();
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait4":
                System.out.println("Haciendo Click en ...");
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_F3);
                robot.keyRelease(KeyEvent.VK_F3);
                i = true;
                break;
            case "clickAndWait5":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait6":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait7":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait8":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait9":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait10":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);                
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait11":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait12":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait13":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
             case "clickAndWait14":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait15":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait16":
                builder.moveToElement(elemento) .perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;
            case "clickAndWait17":
                builder.moveToElement(elemento).perform();
                elemento.click();
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
                Thread.sleep(1000);
                i = true;
                break;    
           case "encargo":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarCodigo();
                Thread.sleep(2000);
                System.out.println(informix.getEncargoCodigo());
                elemento.sendKeys(informix.getEncargoCodigo());
                Thread.sleep(2000);
                i = true;
                break;
            case "despacho":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarCodigoDespacho();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getDespacho());
                Thread.sleep(2000);
                i = true;
                break;
            case "despachoSorter":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarNominaContenedora();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getNominaContenedora());
                Thread.sleep(2000);
                i = true;
                break;    
            case "folioRendicion":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarFolioRendicion();
                elemento.sendKeys(informix.getFolioRendicion());
                Thread.sleep(2000);
                i = true;
                break;
            case "entrega":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarEntrega();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getEntrega());
                Thread.sleep(2000);
                i = true;
                break;
            case "cierre":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarCierre();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getCierre());
                Thread.sleep(2000);
                i = true;
                break;
            case "decomiso":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarDecomiso();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getRendicionEntrega());
                Thread.sleep(2000);
                i = true;
                break;
            case "comprobanteEntrega":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarComprobanteEntrega();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getRendicionEntrega());
                Thread.sleep(2000);
                i = true;
                break;
            case "rendirComprobante":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarRendicionComprobanteEntrega();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getRendicionEntrega());
                Thread.sleep(2000);
                i = true;
                break;
            case "rendicionEntrega":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarRendicionEntrega();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getRendicionEntrega());
                Thread.sleep(2000);
                i = true;
                break;
            case "despachoReparto":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarDespachoReparto();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getDespachoReparto());
                Thread.sleep(2000);
                i = true;
                break;
            case "documentoTributario":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarDocumentoTributario();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getDocumentosTributarios());
                Thread.sleep(2000);
                i = true;
                break;
            case "rendicionDevolucion":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarRendicionDevolucion();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getDespachoReparto());
                Thread.sleep(2000);
                i = true;
                break;    
            case "anulacionEntrega":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarAnulacionEncargo();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getAnulacion());
                encargo=informix.getAnulacionEncargo();
                Thread.sleep(2000);
                i = true;
                break;
            case "anulacionEncargo":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                System.out.println(getEncargo());
                elemento.sendKeys(getEncargo());
                Thread.sleep(1000);
                i = true;
                break;
            case "anulacion":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarAnulacion();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getAnulacion());
                setEncargo(informix.getAnulacionEncargo());
                Thread.sleep(1000);
                i = true;
                break;
            case "modificarNominaReparto":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarNominaReparto();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getModificarNomina());
                setModificarNominaReparto(informix.getModificar());
                Thread.sleep(1000);
                i = true;
                break;
            case "modificar":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                elemento.sendKeys(getModificarNominaReparto());
                i = true;
                break;
            case "nominaContenedora":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarNominaContenedora();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getNominaContenedora());
                Thread.sleep(1000);
                i = true;
                break;
            case "modificarNomina":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.modificarNomina();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getModificarNomina());
                Thread.sleep(1000);
                i = true;
                break;
            case "reclamo":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarReclamo();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getReclamo());
                Thread.sleep(1000);
                i = true;
                break;
            case "memo":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarMemo();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getReclamo());
                Thread.sleep(1000);
                i = true;
                break;
            case "memosNominas":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarMemosNonimas();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getReclamo());
                Thread.sleep(1000);
                i = true;
                break;    
            case "rezago":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                informix.generarRezago();
                Thread.sleep(2000);
                elemento.sendKeys(informix.getCodigos());
                setEncargo(informix.getEncargoCodigo());
                Thread.sleep(1000);
                i = true;
                break;
            case "rezagoEncargo":
                elemento.clear();
                System.out.println("Escribiendo..");
                Thread.sleep(1000);
                elemento.sendKeys(getEncargo());
                Thread.sleep(1000);
                i = true;
                break;     
            default:
                i = false;
                break;
        }
        return i;
    }
    
    private void getFoto(WebDriver webDriver, Test caso, Step paso){//Genera evidencia
        try {
            File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);            
            FileUtils.copyFile(screenshot, new File(evidenciaEjecucion.getPath()+"\\"+caso.getNombre()+"--"+getNumPasos()+".jpg"));
             //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            //File screenshot2 = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);            
            //FileUtils.copyFile(screenshot2, new File(evidenciaEjecucion.getPath()+"\\"+caso.getNombre()+"--"+getNumPasos()+"-2.jpg"));
            //((JavascriptExecutor) driver).executeScript("window.scrollTo(100, document.body.scrollHeight)");        
        } catch (IOException ex) {
            System.out.println("Error al capturar secuencia: "+ex);
        }
    }
    private Thread lanzadorDeConteoTX() { //Lleva el tiempo de ejecución de las pruebas
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        CONTEO = CONTEO + 1;
                    }                       
                } catch (Exception ex) {
                    System.err.println("Ha ocurrido un error: " + ex);
                }
            }
        });
        return hilo;
    }
    private String getCausaNoSuchElement() {//Elemento no encontrado
        try {
            if (driver.findElement(By.xpath("//h1")).getText().matches("Server Error in")) {
                WebElement elementoCausa = driver.findElement(By.xpath("//h1"));
                return elementoCausa.getText();
            } else {
                //driver.close();
                return "No encuentra causa aparente";
            }
        } catch (NoSuchElementException ex) {
            //driver.close();
                return "No se encuentra el elemento en el sistema y no es por caida \n Favor revisar script!!";
        }
    }
    
    private void crearLog2() {//Crea el log en formato csv
        List<LogCSV> log2 = new ArrayList<LogCSV>();
        log2.add(log);

        try {
            String outputFile = ("C:\\Users\\manuelfvalles\\Documents\\Log\\log.csv");
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
            
            if(csv==1){
            csvOutput.write("Nombre del test");
            csvOutput.write("Fecha de ejecucion");
            csvOutput.write("Paso numero:");
            csvOutput.write("Descripcion");
            csvOutput.write("Comando");
            csvOutput.write("Atributo");
            csvOutput.write("Localizador");
            csvOutput.write("Dato");
            csvOutput.write("Estado");
            csvOutput.endRecord();
            }

            csvOutput.write(log.getNombreTest());
            csvOutput.write(log.getFechaEjecucionTest().toString());

            csvOutput.write(String.valueOf(log.getNumPaso()));
            csvOutput.write(log.getDescripcionPaso());
            csvOutput.write(log.getComandoPaso());
            csvOutput.write(log.getAtributoPaso());
            csvOutput.write(log.getTipoLocalizadorPaso());
            csvOutput.write(log.getValorPaso());
            csvOutput.write(log.getPasoEjecutado());
            csvOutput.endRecord();

            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Genera log en formato csv
}