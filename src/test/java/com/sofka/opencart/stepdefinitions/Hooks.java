package com.sofka.opencart.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

/**
 * Cucumber Hooks para la configuración de WebDriver
 */
public class Hooks {
    
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setupWebDriver() {
        try {
            logger.info("Inicializando WebDriver para la prueba...");
            
            // Setup ChromeDriver automáticamente
            WebDriverManager.chromedriver().setup();
            
            // Configurar opciones de Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--disable-extensions",
                "--disable-dev-shm-usage",
                "--disable-blink-features=AutomationControlled",
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
            );
            
            // Crear instancia de ChromeDriver
            WebDriver driver = new ChromeDriver(options);
            
            // Registrar el driver con Serenity
            Serenity.setDriver(driver);
            
            // Configurar timeouts implícitos usando WebDriverWait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            
            logger.info("WebDriver inicializado correctamente");
            
        } catch (Exception e) {
            logger.error("Error al inicializar WebDriver", e);
            throw new RuntimeException("No se pudo inicializar el WebDriver: " + e.getMessage(), e);
        }
    }

    @After
    public void tearDownWebDriver() {
        try {
            WebDriver driver = Serenity.getDriver();
            if (driver != null) {
                logger.info("Cerrando WebDriver...");
                driver.quit();
                logger.info("WebDriver cerrado correctamente");
            }
        } catch (Exception e) {
            logger.error("Error al cerrar WebDriver", e);
        }
    }
}
