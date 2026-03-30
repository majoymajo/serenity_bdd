package com.sofka.opencart.pages;

import com.sofka.opencart.models.GuestCheckout;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class CheckoutPage extends PageObject {

    private static final By GUEST_RADIO = By.cssSelector("input[value='guest']");
    private static final By CONTINUE_ACCOUNT_BUTTON = By.id("button-account");

    private static final By FIRST_NAME = By.id("input-firstname");
    private static final By LAST_NAME = By.id("input-lastname");
    private static final By EMAIL = By.id("input-email");
    private static final By TELEPHONE = By.id("input-telephone");
    private static final By ADDRESS = By.id("input-shipping-address-1");
    private static final By CITY = By.id("input-shipping-city");
    private static final By POSTCODE = By.id("input-shipping-postcode");
    private static final By COUNTRY_SELECT = By.id("input-shipping-country");
    private static final By STATE_SELECT = By.id("input-shipping-zone");
    private static final By CONTINUE_SHIPPING_BUTTON = By.id("button-shipping-address");

    private static final By SHIPPING_METHOD_RADIO = By.cssSelector("input[name='shipping_method']");
    private static final By CONTINUE_SHIPPING_METHOD = By.id("button-shipping-method");

    private static final By PAYMENT_METHOD_RADIO = By.cssSelector("input[name='payment_method']");
    private static final By AGREE_CHECKBOX = By.cssSelector("input[name='agree']");
    private static final By PLACE_ORDER_BUTTON = By.id("button-payment-method");

    private static final By ORDER_CONFIRMATION_HEADING = By.tagName("h1");

    public void selectGuestCheckout() {
        WebElementFacade radio = $(GUEST_RADIO).waitUntilVisible();
        if (!radio.isSelected()) {
            radio.click();
        }
        $(CONTINUE_ACCOUNT_BUTTON).click();
    }

    public void fillShippingAddress(GuestCheckout guest) {
        $(FIRST_NAME).withTimeoutOf(Duration.ofSeconds(10)).waitUntilVisible();

        typeInto(FIRST_NAME, guest.getFirstName());
        typeInto(LAST_NAME, guest.getLastName());
        typeInto(EMAIL, guest.getEmail());
        typeInto(TELEPHONE, guest.getTelephone());
        typeInto(ADDRESS, guest.getAddress());
        typeInto(CITY, guest.getCity());
        typeInto(POSTCODE, guest.getPostalCode());

        selectByText(COUNTRY_SELECT, guest.getCountry());
        $(STATE_SELECT).waitUntilEnabled();
        selectByText(STATE_SELECT, guest.getState());

        $(CONTINUE_SHIPPING_BUTTON).click();
    }

    public void selectShippingMethod() {
        WebElementFacade radio = $(SHIPPING_METHOD_RADIO).waitUntilVisible();
        if (!radio.isSelected()) {
            radio.click();
        }
        $(CONTINUE_SHIPPING_METHOD).click();
    }

    public void selectPaymentMethodAndPlaceOrder() {
        WebElementFacade paymentRadio = $(PAYMENT_METHOD_RADIO).waitUntilVisible();
        if (!paymentRadio.isSelected()) {
            paymentRadio.click();
        }
        if ($(AGREE_CHECKBOX).isPresent() && !$(AGREE_CHECKBOX).isSelected()) {
            $(AGREE_CHECKBOX).click();
        }
        $(PLACE_ORDER_BUTTON).click();
    }

    public boolean isOrderConfirmed() {
        return $(ORDER_CONFIRMATION_HEADING)
                .withTimeoutOf(Duration.ofSeconds(15))
                .waitUntilVisible()
                .getText()
                .contains("Your order has been placed");
    }

    public String getConfirmationMessage() {
        return $(ORDER_CONFIRMATION_HEADING).getText();
    }

    private void typeInto(By locator, String value) {
        WebElementFacade field = $(locator);
        field.clear();
        field.sendKeys(value);
    }

    private void selectByText(By locator, String visibleText) {
        try {
            Select select = new Select(find(locator));
            select.selectByVisibleText(visibleText);
        } catch (Exception e) {
            new Select(find(locator)).selectByValue(visibleText);
        }
    }
}
