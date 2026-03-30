package com.sofka.opencart.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends PageObject {

    private static final By CART_TOTAL = By.id("cart-total");
    private static final By CART_LINK = By.cssSelector("a[title='Shopping Cart']");
    private static final By PRODUCT_THUMBS = By.cssSelector(".product-thumb");

    public void navigateTo() {
        openAt("/index.php?route=product/category&path=24");
    }

    public String getPageUrl() {
        return getDriver().getCurrentUrl();
    }

    public void clickShop() {
        openAt("/index.php?route=product/category&path=24");
    }

    public List<WebElementFacade> getAvailableProducts() {
        return findAll(PRODUCT_THUMBS);
    }

    public void addProductToCart(int productIndex) {
        List<WebElementFacade> products = getAvailableProducts();
        if (productIndex < products.size()) {
            WebElementFacade product = products.get(productIndex);
            scrollToElement(product);
            String cartBefore = $(CART_TOTAL).getText();
            WebElement addButton = product.find(By.cssSelector("button[onclick^='cart.add']"));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", addButton);
            waitForCartTotalToChange(cartBefore);
        }
    }

    private void waitForCartTotalToChange(String previousText) {
        long deadline = System.currentTimeMillis() + 15_000;
        while (System.currentTimeMillis() < deadline) {
            String current = $(CART_TOTAL).getText();
            if (!current.equals(previousText)) {
                return;
            }
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Cart total did not update after adding product to cart");
    }

    public boolean isSuccessMessageDisplayed() {
        return $(CART_TOTAL).isCurrentlyVisible();
    }

    public void goToCart() {
        $(CART_LINK).waitUntilClickable().click();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
