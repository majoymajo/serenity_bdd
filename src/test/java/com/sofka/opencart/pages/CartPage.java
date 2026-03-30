package com.sofka.opencart.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class CartPage extends PageObject {

    private static final By CART_ROWS = By.cssSelector("table.table tbody tr");
    private static final By CHECKOUT_BUTTON = By.cssSelector("a[href*='checkout']");
    private static final By EMPTY_MESSAGE = By.cssSelector(".alert-info");
    private static final By PRODUCT_NAME_LINKS = By.cssSelector("td:first-child a");
    private static final By PRODUCT_PRICES = By.cssSelector("td:nth-child(2)");
    private static final By SUBTOTAL_AMOUNTS = By.cssSelector(".col-sm-4 strong");

    public List<WebElementFacade> getCartProducts() {
        return findAll(CART_ROWS);
    }

    public int getProductCount() {
        return getCartProducts().size();
    }

    public boolean isCartEmpty() {
        try {
            return find(EMPTY_MESSAGE).getText().contains("Your shopping cart is empty");
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElementFacade> getProductNames() {
        return findAll(PRODUCT_NAME_LINKS);
    }

    public List<WebElementFacade> getProductPrices() {
        return findAll(PRODUCT_PRICES);
    }

    public String getSubtotal() {
        List<WebElementFacade> totals = findAll(SUBTOTAL_AMOUNTS);
        if (totals.size() >= 3) {
            return totals.get(totals.size() - 3).getText();
        }
        return "";
    }

    public void proceedToCheckout() {
        $(CHECKOUT_BUTTON).waitUntilClickable().click();
    }

    public boolean hasProducts() {
        return getProductCount() > 0;
    }
}
