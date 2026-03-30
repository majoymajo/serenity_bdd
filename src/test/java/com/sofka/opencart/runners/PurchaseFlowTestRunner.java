package com.sofka.opencart.runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.sofka.opencart.stepdefinitions", plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json"
}, tags = "@smoke or @regression")
public class PurchaseFlowTestRunner {

    static {
        // JUnit creates the Cucumber/Serenity runner very early; set properties at class-load time.
        forceOfflineWebDriverConfig();
    }

    @BeforeClass
    public static void forceOfflineWebDriverConfig() {
        // Ensure Serenity's internal WebDriverManager setup does not attempt any network calls.
        // (Serenity 3.1.0 invokes WebDriverManager even when a driver path is provided.)
        System.setProperty("wdm.avoidExternalConnections", "true");
        System.setProperty("wdm.avoidBrowserDetection", "true");
        System.setProperty("wdm.avoidResolutionCache", "true");
        System.setProperty("wdm.avoidFallback", "true");

        // Prefer Edge on this Windows environment.
        System.setProperty("webdriver.driver", "edge");

        // Use the cached driver location (created by previous WebDriverManager/Selenium Manager runs).
        // If it exists, set it explicitly so driver resolution doesn't hit the network.
        Path cachedEdgeDriver = Paths.get(
                System.getProperty("user.home"),
                ".cache",
                "selenium",
                "msedgedriver",
                "win64",
                "146.0.3856.62",
                "msedgedriver.exe"
        );

        if (Files.exists(cachedEdgeDriver)) {
            System.setProperty("webdriver.edge.driver", cachedEdgeDriver.toAbsolutePath().toString());
            System.setProperty("wdm.edgeDriverVersion", "146.0.3856.62");
            System.setProperty("wdm.cachePath", Paths.get(System.getProperty("user.home"), ".cache", "selenium").toString());
        }
    }
}
