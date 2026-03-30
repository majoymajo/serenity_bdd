package com.sofka.opencart.stepdefinitions;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.core.Serenity;
import com.sofka.opencart.pages.HomePage;
import com.sofka.opencart.pages.CartPage;
import com.sofka.opencart.pages.CheckoutPage;
import com.sofka.opencart.models.GuestCheckout;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class PurchaseFlowSteps {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseFlowSteps.class);
    
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private int productCount = 0;

    @Dado("que el usuario está en la página de inicio de OpenCart")
    public void usuarioEnPaginaInicio() {
        WebDriver driver = Serenity.getDriver();
        homePage = new HomePage(driver);
        homePage.navigateTo();
        assertTrue("La página de inicio no se cargó correctamente",
                driver.getCurrentUrl().contains("opencart"));
    }

    @Cuando("el usuario agrega {int} productos al carrito")
    public void usuarioAgregaProductosAlCarrito(int numeroDeProductos) {
        homePage.clickShop();

        for (int i = 0; i < numeroDeProductos && i < homePage.getAvailableProducts().size(); i++) {
            homePage.addProductToCart(i);
            productCount++;

            assertTrue("El producto #" + (i + 1) + " no se agregó correctamente",
                    homePage.isSuccessMessageDisplayed());

            if (i < numeroDeProductos - 1) {
                homePage.navigateTo();
                homePage.clickShop();
            }
        }
    }

    @Y("el usuario visualiza el carrito de compras")
    public void usuarioVisualizaElCarrito() {
        WebDriver driver = Serenity.getDriver();
        homePage.goToCart();
        cartPage = new CartPage(driver);
    }

    @Entonces("el carrito debe contener {int} productos")
    public void elCarritoDebeContenerProductos(int cantidadEsperada) {
        assertFalse("El carrito está vacío", cartPage.isCartEmpty());
        assertTrue("El carrito no contiene los productos esperados",
                cartPage.getProductCount() >= cantidadEsperada);
    }

    @Y("el carrito debe mostrar los nombres de los productos")
    public void elCarritoDebeMostrarNombres() {
        assertTrue("No hay nombres de productos en el carrito",
                cartPage.getProductNames().size() > 0);
    }

    @Y("el carrito debe mostrar los precios de los productos")
    public void elCarritoDebeMostrarPrecios() {
        assertTrue("No hay precios visibles en el carrito",
                cartPage.getProductPrices().size() > 0);
    }

    @Cuando("el usuario procede a checkout")
    public void usuarioProcedaACheckout() {
        cartPage.proceedToCheckout();
    }

    @Y("el usuario selecciona Guest Checkout")
    public void usuarioSeleccionaGuestCheckout() {
        WebDriver driver = Serenity.getDriver();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.selectGuestCheckout();
    }

    @Y("el usuario completa los datos de envío")
    public void usuarioCompletaDatosDeEnvio() {
        GuestCheckout guest = GuestCheckout.testData();
        checkoutPage.fillShippingAddress(guest);
    }

    @Y("el usuario selecciona un método de envío")
    public void usuarioSeleccionaMetodoDeEnvio() {
        checkoutPage.selectShippingMethod();
    }

    @Y("el usuario selecciona un método de pago")
    public void usuarioSeleccionaMetodoDePago() {
        checkoutPage.selectPaymentMethodAndPlaceOrder();
    }

    @Entonces("el usuario debe ver el mensaje de confirmación {string}")
    public void usuarioDebeVerMensajeDeConfirmacion(String mensajeEsperado) {
        assertTrue("No se muestra el mensaje de confirmación",
                checkoutPage.isOrderConfirmed());

        String textoConfirmacion = checkoutPage.getConfirmationMessage();
        assertTrue("El mensaje de confirmación no contiene el texto esperado",
            textoConfirmacion != null && mensajeEsperado != null
                && textoConfirmacion.toLowerCase().contains(mensajeEsperado.toLowerCase()));
    }

    @Y("el usuario debe ser redirigido a la página de confirmación")
    public void usuarioDebeSerRedirigidoAConfirmacion() {
        WebDriver driver = Serenity.getDriver();
        assertTrue("El usuario no fue redirigido a la página de confirmación",
                driver.getCurrentUrl().contains("checkout"));
    }
}
