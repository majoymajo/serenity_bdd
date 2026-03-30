package com.sofka.opencart.runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.sofka.opencart.stepdefinitions",
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json"
        }
)
public class PurchaseFlowTestRunner {
}
