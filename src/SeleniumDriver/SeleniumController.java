/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeleniumDriver;

import Util.TipoFile;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author mvilches
 */
public class SeleniumController {
    private String url = "";
    private int browser = 0;
    WebDriver driver =null;

    public SeleniumController(String url, int browser) {
        this.url = url;
        this.browser=browser;
    }
    
    public void execute() throws IOException{        
        
        try {
            driver = DriverFactory.getDriver(browser);
                    driver.get(this.url);
                    
            Thread.sleep(2000);
            String resultado=driver.findElement(By.id("_1_WAR_webformportlet_INSTANCE_S2RH2Obym0Q1_fieldOptionalErrorfield2")).getText();
            File foto = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(foto, new File("C:\\AMEXXI\\capacitacion"));
            
            if(resultado.contains("ste campo es obligator"))
                System.out.println("PRUEBA EXITOSA");
            else
                System.out.println("PRUEBA ERRONEA");
            /*
            driver.get("http://www.google.com/");
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            // Now you can do whatever you need to do with it, for example copy somewhere
                FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"))
            */
        } catch (InterruptedException ex) {
            Logger.getLogger(SeleniumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void execStep(){
        
    }
    
}
