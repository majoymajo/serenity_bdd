package com.sofka.opencart.webdriver;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalEdgeDriverSource implements DriverSource {

    private static final String DEFAULT_EDGE_DRIVER_RELATIVE = ".cache\\selenium\\msedgedriver\\win64\\146.0.3856.62\\msedgedriver.exe";

    @Override
    public WebDriver newDriver() {
        ensureEdgeDriverSystemProperty();

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.setAcceptInsecureCerts(true);

        return new EdgeDriver(options);
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private static void ensureEdgeDriverSystemProperty() {
        String configured = System.getProperty("webdriver.edge.driver");
        if (configured != null && !configured.isBlank() && Files.exists(Paths.get(configured))) {
            return;
        }

        String userHome = System.getProperty("user.home");
        if (userHome == null || userHome.isBlank()) {
            throw new IllegalStateException("No se pudo determinar user.home para ubicar msedgedriver.exe");
        }

        Path defaultPath = Paths.get(userHome, DEFAULT_EDGE_DRIVER_RELATIVE.split("\\\\"));
        if (!Files.exists(defaultPath)) {
            throw new IllegalStateException(
                    "No se encontró msedgedriver.exe. Configura -Dwebdriver.edge.driver con la ruta al driver. "
                            + "Ruta esperada: " + defaultPath);
        }

        System.setProperty("webdriver.edge.driver", defaultPath.toAbsolutePath().toString());
    }
}
