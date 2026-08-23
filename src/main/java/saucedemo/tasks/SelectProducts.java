package saucedemo.tasks;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import java.util.List;

import static saucedemo.userinterface.InventoryPage.ADD_TO_CART_BUTTONS;
import static saucedemo.userinterface.InventoryPage.CART_ICON;

public class SelectProducts implements Task {

    private final int quantity;

    public SelectProducts(int quantity) {
        this.quantity = quantity;
    }

    public static SelectProducts quantity(int quantity) {
        return Tasks.instrumented(SelectProducts.class, quantity);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<WebElementFacade> addToCartButtons = ADD_TO_CART_BUTTONS.resolveAllFor(actor);

        addToCartButtons.stream()
                .limit(quantity)
                .forEach(WebElementFacade::click);

        actor.attemptsTo(Click.on(CART_ICON));
    }
}
