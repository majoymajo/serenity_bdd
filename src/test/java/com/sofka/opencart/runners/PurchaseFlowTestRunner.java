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

    @BeforeClass
    public static void forceOfflineWebDriverConfig() {
        // Ensure Serenity/Selenium uses a locally available driver and never tries to download one.
        System.setProperty("wdm.enabled", "false");
        System.setProperty("wdm.offline", "true");

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
        }
    }
}
