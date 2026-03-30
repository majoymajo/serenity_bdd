package com.sofka.opencart.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.thucydides.core.annotations.Steps;
import com.sofka.opencart.pages.CartPage;
import com.sofka.opencart.pages.CheckoutPage;
import com.sofka.opencart.pages.HomePage;
import com.sofka.opencart.models.GuestCheckout;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseFlowSteps {

    @Steps
    private HomePage homePage;

    @Steps
    private CartPage cartPage;

    @Steps
    private CheckoutPage checkoutPage;

    @Dado("que el usuario está en la página de inicio de OpenCart")
    public void usuarioEnPaginaInicio() {
        homePage.open();
    }

    @Cuando("el usuario agrega {int} productos al carrito")
    public void usuarioAgregaProductosAlCarrito(int cantidad) {
        homePage.clickShop();

        for (int i = 0; i < cantidad && i < homePage.getAvailableProducts().size(); i++) {
            homePage.addProductToCart(i);

            if (i < cantidad - 1) {
                homePage.open();
                homePage.clickShop();
            }
        }
    }

    @Y("el usuario visualiza el carrito de compras")
    public void usuarioVisualizaElCarrito() {
        homePage.goToCart();
    }

    @Entonces("el carrito debe contener {int} productos")
    public void elCarritoDebeContenerProductos(int cantidadEsperada) {
        assertThat(cartPage.isCartEmpty()).as("El carrito no debería estar vacío").isFalse();
        assertThat(cartPage.getProductCount()).as("El carrito debe tener al menos %d productos", cantidadEsperada)
                .isGreaterThanOrEqualTo(cantidadEsperada);
    }

    @Y("el carrito debe mostrar los nombres de los productos")
    public void elCarritoDebeMostrarNombres() {
        assertThat(cartPage.getProductNames()).as("El carrito debe mostrar nombres").isNotEmpty();
    }

    @Y("el carrito debe mostrar los precios de los productos")
    public void elCarritoDebeMostrarPrecios() {
        assertThat(cartPage.getProductPrices()).as("El carrito debe mostrar precios").isNotEmpty();
    }

    @Cuando("el usuario procede a checkout")
    public void usuarioProcedaACheckout() {
        cartPage.proceedToCheckout();
    }

    @Y("el usuario selecciona Guest Checkout")
    public void usuarioSeleccionaGuestCheckout() {
        checkoutPage.selectGuestCheckout();
    }

    @Y("el usuario completa los datos de envío")
    public void usuarioCompletaDatosDeEnvio() {
        checkoutPage.fillShippingAddress(GuestCheckout.testData());
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
        assertThat(checkoutPage.isOrderConfirmed())
                .as("La orden debe estar confirmada").isTrue();
        assertThat(checkoutPage.getConfirmationMessage())
                .as("El mensaje de confirmación debe contener: " + mensajeEsperado)
                .contains(mensajeEsperado);
    }

    @Y("el usuario debe ser redirigido a la página de confirmación")
    public void usuarioDebeSerRedirigidoAConfirmacion() {
        assertThat(homePage.getCurrentUrl())
                .as("La URL debe contener 'checkout'")
                .contains("checkout");
    }
}
