package com.sofka.opencart.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.core.Serenity;
import com.sofka.opencart.pages.HomePage;
import com.sofka.opencart.pages.CartPage;
import com.sofka.opencart.pages.CheckoutPage;
import com.sofka.opencart.models.Product;
import com.sofka.opencart.models.GuestCheckout;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;

/**
 * Step Definitions para el flujo de compra en OpenCart
 */
public class PurchaseFlowSteps {

    private WebDriver driver;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private int productCount = 0;

    // Hooks - Inicializar páginas antes de cada escenario
    public PurchaseFlowSteps() {
        this.driver = Serenity.getDriver();
    }

    /**
     * Dado: El usuario accede a la página de inicio de OpenCart
     */
    @Given("the user is on the OpenCart home page")
    public void userOnHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateTo();
        assertTrue("La página de inicio no se cargó correctamente", 
                driver.getCurrentUrl().contains("opencart"));
    }

    /**
     * Cuando: El usuario agrega dos productos al carrito
     */
    @When("the user adds {int} products to the cart")
    public void userAddsProductsToCart(int numberOfProducts) {
        homePage.clickShop();
        
        for (int i = 0; i < numberOfProducts && i < homePage.getAvailableProducts().size(); i++) {
            homePage.addProductToCart(i);
            productCount++;
            
            // Verificar que el producto se agregó
            assertTrue("El producto #" + (i + 1) + " no se agregó correctamente",
                    homePage.isSuccessMessageDisplayed());
            
            // Volver a la tienda para agregar el siguiente producto
            if (i < numberOfProducts - 1) {
                homePage.navigateTo();
                homePage.clickShop();
            }
        }
    }

    /**
     * Cuando: El usuario visualiza el carrito
     */
    @When("the user views the shopping cart")
    public void userViewsCart() {
        homePage.goToCart();
        cartPage = new CartPage(driver);
    }

    /**
     * Entonces: El carrito debe contener los productos agregados
     */
    @Then("the cart should contain {int} products")
    public void cartShouldContainProducts(int expectedCount) {
        assertFalse("El carrito está vacío", cartPage.isCartEmpty());
        assertTrue("El carrito no contiene los productos esperados",
                cartPage.getProductCount() >= expectedCount);
    }

    /**
     * Y: El usuario verifica que el carrito contiene los nombres de los productos
     */
    @And("the cart should display product names")
    public void cartDisplaysProductNames() {
        assertTrue("No hay nombres de productos en el carrito",
                cartPage.getProductNames().size() > 0);
    }

    /**
     * Y: El usuario verifica que se muestren los precios
     */
    @And("the cart should display product prices")
    public void cartDisplaysPrices() {
        assertTrue("No hay precios visible en el carrito",
                cartPage.getProductPrices().size() > 0);
    }

    /**
     * Cuando: El usuario procede a checkout
     */
    @When("the user proceeds to checkout")
    public void userProceedsToCheckout() {
        cartPage.proceedToCheckout();
    }

    /**
     * Y: El usuario selecciona Guest Checkout
     */
    @And("the user selects Guest Checkout")
    public void userSelectsGuestCheckout() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.selectGuestCheckout();
    }

    /**
     * Y: El usuario completa los datos de envío
     */
    @And("the user fills in the shipping address")
    public void userFillsShippingAddress() {
        GuestCheckout guest = GuestCheckout.testData();
        checkoutPage.fillShippingAddress(guest);
    }

    /**
     * Y: El usuario selecciona el método de envío
     */
    @And("the user selects a shipping method")
    public void userSelectsShippingMethod() {
        checkoutPage.selectShippingMethod();
    }

    /**
     * Y: El usuario selecciona el método de pago
     */
    @And("the user selects a payment method")
    public void userSelectsPaymentMethod() {
        checkoutPage.selectPaymentMethodAndPlaceOrder();
    }

    /**
     * Entonces: El usuario debe ver el mensaje de confirmación
     */
    @Then("the user should see the order confirmation message")
    public void userSeesConfirmationMessage() {
        assertTrue("No se muestra el mensaje de confirmación",
                checkoutPage.isOrderConfirmed());
        
        String confirmationText = checkoutPage.getConfirmationMessage();
        assertTrue("El mensaje de confirmación no contiene el texto esperado",
                confirmationText.contains("Your order has been placed"));
    }

    /**
     * Entonces: El usuario debe ser redirigido a la página de confirmación
     */
    @Then("the user should be redirected to the confirmation page")
    public void userRedirectedToConfirmation() {
        assertTrue("El usuario no fue redirigido a la página de confirmación",
                driver.getCurrentUrl().contains("checkout"));
    }
}
