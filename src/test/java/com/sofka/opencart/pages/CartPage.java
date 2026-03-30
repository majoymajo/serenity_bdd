package com.sofka.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import net.serenitybdd.core.pages.PageObject;
import java.util.List;

/**
 * Page Object para la página del carrito de compras
 */
public class CartPage extends PageObject {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators
    private By cartProducts = By.cssSelector("table.table tbody tr");
    private By cartTable = By.cssSelector(".table.table-striped");
    private By checkoutButton = By.cssSelector("a[href*='checkout']");
    private By continueShoppingButton = By.cssSelector("a[href*='continue']");
    private By cartEmptyMessage = By.cssSelector(".alert-info");
    private By productNames = By.cssSelector("td:first-child a");
    private By productPrices = By.cssSelector("td:nth-child(2)");
    private By quantities = By.cssSelector("input[name*='quantity']");
    private By subtotalPrice = By.cssSelector(".col-sm-4 strong");

    /**
     * Obtiene una lista de productos en el carrito
     */
    public List<WebElement> getCartProducts() {
        return findAll(cartProducts);
    }

    /**
     * Obtiene el número de productos en el carrito
     */
    public int getProductCount() {
        try {
            return getCartProducts().size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Verifica si el carrito está vacío
     */
    public boolean isCartEmpty() {
        try {
            return find(cartEmptyMessage).getText()
                    .contains("Your shopping cart is empty");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene los nombres de productos en el carrito
     */
    public List<WebElement> getProductNames() {
        return findAll(productNames);
    }

    /**
     * Obtiene los precios de productos en el carrito
     */
    public List<WebElement> getProductPrices() {
        return findAll(productPrices);
    }

    /**
     * Obtiene el subtotal de la compra
     */
    public String getSubtotal() {
        try {
            List<WebElement> totals = findAll(subtotalPrice);
            // El subtotal es generalmente el antepenúltimo
            return totals.get(totals.size() - 3).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Navega a checkout
     */
    public void proceedToCheckout() {
        waitForElementPresent(checkoutButton);
        find(checkoutButton).click();
        waitABit(2000);
    }

    /**
     * Continúa comprando (vuelve a la tienda)
     */
    public void continueShopping() {
        try {
            find(continueShoppingButton).click();
            waitABit(1500);
        } catch (Exception e) {
            // El botón puede no estar visible
        }
    }

    /**
     * Verifica que el carrito contenga al menos un producto
     */
    public boolean hasProducts() {
        return getProductCount() > 0;
    }

    /**
     * Espera un tiempo determinado
     */
    private void waitABit(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Espera a que un elemento esté presente en el DOM
     */
    private void waitForElementPresent(By locator) {
        getDriver().manage().timeouts().implicitlyWait(
            java.time.Duration.ofSeconds(10)
        );
    }
}
