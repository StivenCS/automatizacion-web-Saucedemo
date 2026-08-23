package saucedemo.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import saucedemo.models.CheckoutInfo;

import static saucedemo.userinterface.CartPage.CHECKOUT_BUTTON;
import static saucedemo.userinterface.CheckoutPage.*;

public class FinalizePurchase implements Task {

    private final CheckoutInfo checkoutInfo;

    public FinalizePurchase(CheckoutInfo checkoutInfo) {
        this.checkoutInfo = checkoutInfo;
    }

    public static FinalizePurchase withInfo(CheckoutInfo checkoutInfo) {
        return Tasks.instrumented(FinalizePurchase.class, checkoutInfo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CHECKOUT_BUTTON),
                Enter.theValue(checkoutInfo.getFirstName()).into(FIRST_NAME),
                Enter.theValue(checkoutInfo.getLastName()).into(LAST_NAME),
                Enter.theValue(checkoutInfo.getPostalCode()).into(POSTAL_CODE),
                Click.on(CONTINUE_BUTTON),
                Click.on(FINISH_BUTTON)
        );
    }
}
