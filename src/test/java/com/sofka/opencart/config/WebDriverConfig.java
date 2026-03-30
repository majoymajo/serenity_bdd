package com.sofka.opencart.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.Before;

public class WebDriverConfig {

    @Before
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }
}
