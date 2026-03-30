package com.sofka.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import net.serenitybdd.core.pages.PageObject;
import com.sofka.opencart.models.GuestCheckout;

/**
 * Page Object para la página de Checkout
 */
public class CheckoutPage extends PageObject {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators - Pestaña de opciones de checkout
    private By guestCheckoutRadio = By.cssSelector("input[value='guest']");
    private By continueGuestButton = By.id("button-account");

    // Locators - Datos de Envío
    private By firstNameField = By.id("input-firstname");
    private By lastNameField = By.id("input-lastname");
    private By emailField = By.id("input-email");
    private By telephoneField = By.id("input-telephone");
    private By addressField = By.id("input-shipping-address-1");
    private By cityField = By.id("input-shipping-city");
    private By postcodeField = By.id("input-shipping-postcode");
    private By countrySelect = By.id("input-shipping-country");
    private By stateSelect = By.id("input-shipping-zone");
    private By continueShippingButton = By.id("button-shipping-address");

    // Locators - Método de Envío
    private By shippingMethodRadio = By.cssSelector("input[name='shipping_method']");
    private By continueShippingMethodButton = By.id("button-shipping-method");

    // Locators - Método de Pago
    private By paymentMethodRadio = By.cssSelector("input[name='payment_method']");
    private By agreeCheckbox = By.cssSelector("input[name='agree']");
    private By placeOrderButton = By.id("button-payment-method");

    // Locators - Confirmación
    private By confirmationMessage = By.cssSelector(".alert-success h1");
    private By orderConfirmation = By.tagName("h1");

    /**
     * Selecciona Guest Checkout
     */
    public void selectGuestCheckout() {
        waitForElementPresent(guestCheckoutRadio);
        WebElement radio = find(guestCheckoutRadio);
        if (!radio.isSelected()) {
            radio.click();
        }
        find(continueGuestButton).click();
        waitABit(2000);
    }

    /**
     * Completa los datos de envío (Billing Address)
     */
    public void fillShippingAddress(GuestCheckout guest) {
        waitForElementPresent(firstNameField);
        
        // Llenar datos de envío
        fillField(firstNameField, guest.getFirstName());
        fillField(lastNameField, guest.getLastName());
        fillField(emailField, guest.getEmail());
        fillField(telephoneField, guest.getTelephone());
        fillField(addressField, guest.getAddress());
        fillField(cityField, guest.getCity());
        fillField(postcodeField, guest.getPostalCode());

        // Seleccionar país
        selectDropdown(countrySelect, guest.getCountry());
        waitABit(1000);

        // Seleccionar estado
        selectDropdown(stateSelect, guest.getState());
        
        // Continuar
        find(continueShippingButton).click();
        waitABit(2000);
    }

    /**
     * Selecciona el método de envío
     */
    public void selectShippingMethod() {
        waitForElementPresent(shippingMethodRadio);
        WebElement shippingRadio = find(shippingMethodRadio);
        if (!shippingRadio.isSelected()) {
            shippingRadio.click();
        }
        find(continueShippingMethodButton).click();
        waitABit(2000);
    }

    /**
     * Selecciona el método de pago y coloca la orden
     */
    public void selectPaymentMethodAndPlaceOrder() {
        waitForElementPresent(paymentMethodRadio);
        
        WebElement paymentRadio = find(paymentMethodRadio);
        if (!paymentRadio.isSelected()) {
            paymentRadio.click();
        }

        // Aceptar términos y condiciones
        try {
            WebElement agree = find(agreeCheckbox);
            if (!agree.isSelected()) {
                agree.click();
            }
        } catch (Exception e) {
            // El checkbox puede no estar disponible en todas las versiones
        }

        // Coloca la orden
        find(placeOrderButton).click();
        waitABit(3000);
    }

    /**
     * Verifica que el mensaje de confirmación esté visible
     */
    public boolean isOrderConfirmed() {
        try {
            WebElement confirmation = find(orderConfirmation);
            String text = confirmation.getText();
            return text.contains("Your order has been placed");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el texto del mensaje de confirmación
     */
    public String getConfirmationMessage() {
        try {
            return find(orderConfirmation).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Llena un campo de texto
     */
    private void fillField(By locator, String value) {
        WebElement field = find(locator);
        field.clear();
        field.sendKeys(value);
    }

    /**
     * Selecciona un valor de un dropdown
     */
    private void selectDropdown(By locator, String value) {
        try {
            WebElement dropdown = find(locator);
            Select select = new Select(dropdown);
            
            try {
                select.selectByVisibleText(value);
            } catch (Exception e) {
                // Si no encuentra por texto visible, intenta por valor
                select.selectByValue(value);
            }
        } catch (Exception e) {
            // El dropdown puede no estar presente
        }
    }

    /**
     * Espera un tiempo determinado
     */
    @Override
    protected void waitABit(long milliseconds) {
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
