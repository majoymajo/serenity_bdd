package com.sofka.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import net.serenitybdd.core.pages.PageObject;
import java.util.List;

/**
 * Page Object para la página principal de OpenCart
 */
public class HomePage extends PageObject {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators
    private By shopLink = By.linkText("Shop");
    private By cartLink = By.cssSelector("a[title='Shopping Cart']");
    private By productLinks = By.cssSelector(".product-thumb");
    private By addToCartButtons = By.cssSelector(".btn-primary");
    private By cartCounter = By.cssSelector(".badge");
    private By successMessage = By.cssSelector(".alert-success");

    /**
     * Navega a la página de inicio
     */
    public void navigateTo() {
        getDriver().navigate().to("http://opencart.abstracta.us/");
        waitABit(2000);
    }

    /**
     * Abre la página de tienda
     */
    public void clickShop() {
        waitForElementPresent(shopLink);
        find(shopLink).click();
        waitABit(2000);
    }

    /**
     * Obtiene la lista de productos disponibles
     */
    public List<WebElement> getAvailableProducts() {
        return findAll(productLinks);
    }

    /**
     * Agrega un producto al carrito por su número de índice
     * @param productIndex Índice del producto (0-based)
     */
    public void addProductToCart(int productIndex) {
        List<WebElement> products = getAvailableProducts();
        if (productIndex < products.size()) {
            // Scroll para hacer visible el producto
            scrollToElement(products.get(productIndex));
            waitABit(500);
            
            // Buscar el botón "Add to Cart" más cercano
            WebElement addButton = products.get(productIndex)
                    .findElement(By.cssSelector(".btn-primary"));
            addButton.click();
            waitABit(1500);
        }
    }

    /**
     * Verifica que el mensaje de éxito sea mostrado
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            return find(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navega al carrito de compras
     */
    public void goToCart() {
        waitForElementPresent(cartLink);
        find(cartLink).click();
        waitABit(2000);
    }

    /**
     * Obtiene el número de items en el carrito
     */
    public String getCartCount() {
        try {
            return find(cartCounter).getText();
        } catch (Exception e) {
            return "0";
        }
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
     * Hace scroll hasta un elemento
     */
    private void scrollToElement(WebElement element) {
        getDriver().executeScript("arguments[0].scrollIntoView(true);", element);
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
