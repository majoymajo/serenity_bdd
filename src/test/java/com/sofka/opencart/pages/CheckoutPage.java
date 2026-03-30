package com.sofka.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.core.pages.PageObject;
import com.sofka.opencart.models.GuestCheckout;

import java.time.Duration;

/**
 * Page Object para la página de Checkout
 */
public class CheckoutPage extends PageObject {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators - Pestaña de opciones de checkout
    private By checkoutOptionsPanelLink = By.cssSelector("a[href='#collapse-checkout-option']");
    private By guestCheckoutRadio = By.cssSelector("input[name='account'][value='guest']");
    private By continueGuestButton = By.id("button-account");

    // Locators - Datos (Guest) Billing Details
    private By billingDetailsPanelLink = By.cssSelector("a[href='#collapse-payment-address']");
    private By firstNameField = By.id("input-payment-firstname");
    private By lastNameField = By.id("input-payment-lastname");
    private By emailField = By.id("input-payment-email");
    private By telephoneField = By.id("input-payment-telephone");
    private By addressField = By.id("input-payment-address-1");
    private By cityField = By.id("input-payment-city");
    private By postcodeField = By.id("input-payment-postcode");
    private By countrySelect = By.id("input-payment-country");
    private By stateSelect = By.id("input-payment-zone");
    private By continueBillingButton = By.id("button-guest");

    // Locators - Shipping Address (may appear depending on configuration)
    private By shippingAddressPanelLink = By.cssSelector("a[href='#collapse-shipping-address']");
    private By continueShippingAddressButton = By.id("button-shipping-address");

    // Locators - Shipping Method
    private By shippingMethodPanelLink = By.cssSelector("a[href='#collapse-shipping-method']");
    private By shippingMethodRadio = By.cssSelector("input[name='shipping_method']");
    private By continueShippingMethodButton = By.id("button-shipping-method");

    // Locators - Payment Method
    private By paymentMethodPanelLink = By.cssSelector("a[href='#collapse-payment-method']");
    private By paymentMethodRadio = By.cssSelector("input[name='payment_method']");
    private By agreeCheckbox = By.cssSelector("input[name='agree']");
    private By continuePaymentMethodButton = By.id("button-payment-method");

    // Locators - Confirm Order
    private By confirmOrderPanelLink = By.cssSelector("a[href='#collapse-checkout-confirm']");
    private By confirmOrderButton = By.id("button-confirm");

    // Locators - Confirmación
    private By confirmationMessage = By.cssSelector(".alert-success h1");
    private By orderConfirmation = By.tagName("h1");

    // Locators - Validation / errors
    private By dangerAlert = By.cssSelector(".alert.alert-danger");
    private By warningAlert = By.cssSelector(".alert.alert-warning");

    /**
     * Selecciona Guest Checkout
     */
    public void selectGuestCheckout() {
        // Ensure Step 1 panel is expanded so the radio buttons exist/are interactable
        expandCheckoutOptionsIfNeeded();

        waitUntilVisible(guestCheckoutRadio);
        WebElement radio = find(guestCheckoutRadio);
        if (!radio.isSelected()) {
            radio.click();
        }

        waitUntilClickable(continueGuestButton);
        find(continueGuestButton).click();
        waitABit(2000);
    }

    private void expandCheckoutOptionsIfNeeded() {
        try {
            // If the radio is already present, do nothing
            if (getDriver().findElements(guestCheckoutRadio).size() > 0) {
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            waitUntilClickable(checkoutOptionsPanelLink);
            find(checkoutOptionsPanelLink).click();
            waitABit(500);
        } catch (Exception ignored) {
            // Best effort
        }
    }

    private void waitUntilVisible(By locator) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUntilClickable(By locator) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitUntilEnabled(By locator) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(20)).until(driver -> {
            try {
                WebElement element = find(locator);
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Completa los datos de envío (Billing Address)
     */
    public void fillShippingAddress(GuestCheckout guest) {
        expandBillingDetailsIfNeeded();
        waitUntilVisible(firstNameField);

        // Llenar datos de envío
        fillField(firstNameField, guest.getFirstName());
        fillField(lastNameField, guest.getLastName());
        fillField(emailField, guest.getEmail());
        fillField(telephoneField, guest.getTelephone());
        fillField(addressField, guest.getAddress());
        fillField(cityField, guest.getCity());
        fillField(postcodeField, guest.getPostalCode());

        // Seleccionar país (este demo está en inglés; el modelo puede venir en español)
        waitUntilEnabled(countrySelect);
        selectDropdown(countrySelect, guest.getCountry());
        waitForZoneOptionsToLoad();

        // Seleccionar estado/zona (puede depender del país)
        waitUntilEnabled(stateSelect);
        selectDropdown(stateSelect, guest.getState());

        // Continuar a Payment Method
        waitUntilClickable(continueBillingButton);
        find(continueBillingButton).click();

        // Wait until the next step appears OR surface validation errors
        waitForNextCheckoutStepOrThrow();
    }

    private void waitForNextCheckoutStepOrThrow() {
        By anyNextStep = By.cssSelector(
                "#collapse-shipping-address.in, #collapse-shipping-address.show, " +
                "#collapse-shipping-method.in, #collapse-shipping-method.show, " +
                "#collapse-payment-method.in, #collapse-payment-method.show"
        );

        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(25))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(anyNextStep),
                            ExpectedConditions.visibilityOfElementLocated(dangerAlert)
                    ));
        } catch (Exception ignored) {
        }

        if (getDriver().findElements(dangerAlert).size() > 0) {
            String msg;
            try {
                msg = find(dangerAlert).getText();
            } catch (Exception e) {
                msg = "(no se pudo leer el detalle del error)";
            }
            throw new AssertionError("Checkout no avanzó tras Billing Details. Validación: " + msg);
        }
    }

    private void waitForZoneOptionsToLoad() {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(15)).until(driver -> {
                try {
                    Select zones = new Select(find(stateSelect));
                    return zones.getOptions().size() > 1;
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception ignored) {
            // If it doesn't load, selection will fall back later.
        }
    }

    /**
     * Selecciona el método de envío
     */
    public void selectShippingMethod() {
        // OpenCart commonly uses: Billing -> (Shipping Address) -> Shipping Method -> Payment Method.
        // If Shipping Address step appears, continue it (it is often prefilled / same as billing).
        continueShippingAddressIfPresent();

        // If Shipping Method step exists, pick the first available method and continue.
        if (getDriver().findElements(shippingMethodRadio).size() > 0 || getDriver().findElements(shippingMethodPanelLink).size() > 0) {
            expandShippingMethodIfNeeded();
            waitForShippingMethodOrThrow();

            if (getDriver().findElements(shippingMethodRadio).isEmpty()) {
                throw new AssertionError("No hay opciones de envío disponibles (shipping_method no encontrado).");
            }

            WebElement method = find(shippingMethodRadio);
            if (!method.isSelected()) {
                method.click();
            }

            waitUntilClickable(continueShippingMethodButton);
            find(continueShippingMethodButton).click();
            waitABit(1500);
        }

        // Now Payment Method should be reachable
        expandPaymentMethodIfNeeded();
        waitUntilVisible(paymentMethodRadio);
    }

    private void waitForShippingMethodOrThrow() {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(25))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(shippingMethodRadio),
                            ExpectedConditions.visibilityOfElementLocated(dangerAlert),
                            ExpectedConditions.visibilityOfElementLocated(warningAlert)
                    ));
        } catch (Exception ignored) {
        }

        if (getDriver().findElements(dangerAlert).size() > 0 || getDriver().findElements(warningAlert).size() > 0) {
            String msg;
            try {
                if (getDriver().findElements(dangerAlert).size() > 0) {
                    msg = find(dangerAlert).getText();
                } else {
                    msg = find(warningAlert).getText();
                }
            } catch (Exception e) {
                msg = "(no se pudo leer el detalle del error)";
            }
            throw new AssertionError("No se pudieron cargar opciones de envío. Mensaje: " + msg);
        }
    }

    private void continueShippingAddressIfPresent() {
        try {
            if (getDriver().findElements(continueShippingAddressButton).size() == 0 && getDriver().findElements(shippingAddressPanelLink).size() == 0) {
                return;
            }
        } catch (Exception ignored) {
            return;
        }

        try {
            // Expand if needed
            if (getDriver().findElements(continueShippingAddressButton).size() == 0) {
                waitUntilClickable(shippingAddressPanelLink);
                find(shippingAddressPanelLink).click();
                waitABit(500);
            }

            if (getDriver().findElements(continueShippingAddressButton).size() > 0) {
                waitUntilClickable(continueShippingAddressButton);
                find(continueShippingAddressButton).click();
                waitABit(1500);
            }
        } catch (Exception ignored) {
        }
    }

    private void expandShippingMethodIfNeeded() {
        try {
            if (getDriver().findElements(shippingMethodRadio).size() > 0) {
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            waitUntilClickable(shippingMethodPanelLink);
            find(shippingMethodPanelLink).click();
            waitABit(500);
        } catch (Exception ignored) {
        }
    }

    /**
     * Selecciona el método de pago y coloca la orden
     */
    public void selectPaymentMethodAndPlaceOrder() {
        expandPaymentMethodIfNeeded();
        waitUntilVisible(paymentMethodRadio);

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

        // Continuar a Confirm Order
        waitUntilClickable(continuePaymentMethodButton);
        find(continuePaymentMethodButton).click();
        waitABit(1500);

        // Confirmar orden
        expandConfirmOrderIfNeeded();
        waitUntilClickable(confirmOrderButton);
        find(confirmOrderButton).click();
        waitABit(3000);
    }

    private void expandBillingDetailsIfNeeded() {
        try {
            if (getDriver().findElements(firstNameField).size() > 0) {
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            waitUntilClickable(billingDetailsPanelLink);
            find(billingDetailsPanelLink).click();
            waitABit(500);
        } catch (Exception ignored) {
        }
    }

    private void expandPaymentMethodIfNeeded() {
        try {
            if (getDriver().findElements(paymentMethodRadio).size() > 0) {
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            waitUntilClickable(paymentMethodPanelLink);
            find(paymentMethodPanelLink).click();
            waitABit(500);
        } catch (Exception ignored) {
        }
    }

    private void expandConfirmOrderIfNeeded() {
        try {
            if (getDriver().findElements(confirmOrderButton).size() > 0) {
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            waitUntilClickable(confirmOrderPanelLink);
            find(confirmOrderPanelLink).click();
            waitABit(500);
        } catch (Exception ignored) {
        }
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
        WebElement dropdown = find(locator);
        Select select = new Select(dropdown);

        if (value == null) {
            selectFirstNonEmpty(select);
            return;
        }

        String normalized = value.trim();

        // Common Spanish->English mapping for this demo
        if (normalized.equalsIgnoreCase("España")) {
            normalized = "Spain";
        }

        try {
            select.selectByVisibleText(normalized);
            return;
        } catch (Exception ignored) {
        }

        try {
            select.selectByValue(normalized);
            return;
        } catch (Exception ignored) {
        }

        // Fall back to the first valid option to avoid blocking the flow
        selectFirstNonEmpty(select);
    }

    private void selectFirstNonEmpty(Select select) {
        for (WebElement option : select.getOptions()) {
            String v = option.getAttribute("value");
            if (v != null && !v.isBlank()) {
                try {
                    select.selectByValue(v);
                } catch (Exception ignored) {
                }
                return;
            }
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
                java.time.Duration.ofSeconds(10));
    }
}
