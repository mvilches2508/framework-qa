/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeleniumDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;


/**
 *
 * @author mvilches
 */
public class DriverFactory {
    
    /***
     * Fábrica de browser
     * @psram browser CHROME
     * @return Webdriver directo para hacer uso
     */    
    public static WebDriver getDriver (int browser){
        final int CHROME = 1;
        final int OPERA = 2;
        final int IEXPLORER = 3;
        final int SAFARI = 4;
        final int EDGE = 5;
        
        WebDriver driver = null;
        switch(browser){           
            case CHROME:
                    driver = new ChromeDriver();
                break;
            case OPERA:
                    driver = new OperaDriver();
                break;
            case IEXPLORER:
                    driver = new InternetExplorerDriver();
                break;
            case SAFARI:
                    driver = new SafariDriver();
                break;
            case EDGE:
                    driver = new EdgeDriver();
                break;  
            default:
                  return new FirefoxDriver();
               
        }
        return driver;
    }
    
}
