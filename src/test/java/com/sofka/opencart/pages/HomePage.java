package com.sofka.opencart.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class HomePage extends PageObject {


    private static final By CART_LINK = By.cssSelector("a[title='Shopping Cart']");
    private static final By PRODUCT_THUMBS = By.cssSelector(".product-thumb");
    private static final By SUCCESS_ALERT = By.cssSelector(".alert-success");

    public void navigateTo() {
        openAt("/index.php?route=product/category&path=20");
    }

    public String getPageUrl() {
        return getDriver().getCurrentUrl();
    }

    public void clickShop() {
        openAt("/index.php?route=product/category&path=20");
    }

    public List<WebElementFacade> getAvailableProducts() {
        return findAll(PRODUCT_THUMBS);
    }

    public void addProductToCart(int productIndex) {
        List<WebElementFacade> products = getAvailableProducts();
        if (productIndex < products.size()) {
            WebElementFacade product = products.get(productIndex);
            scrollToElement(product);
            product.find(By.cssSelector(".btn-primary")).click();
            $(SUCCESS_ALERT).withTimeoutOf(Duration.ofSeconds(10)).waitUntilVisible();
        }
    }

    public boolean isSuccessMessageDisplayed() {
        return $(SUCCESS_ALERT).isCurrentlyVisible();
    }

    public void goToCart() {
        $(CART_LINK).waitUntilClickable().click();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
