package com.sofka.opencart.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner para ejecutar las pruebas E2E con Serenity BDD
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.sofka.opencart.stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = false,
        tags = "@smoke or @regression"
)
public class PurchaseFlowTestRunner {
    // This class remains empty, it is used only as a holder for the @RunWith annotation
    // and the @CucumberOptions to configure the test run
}
