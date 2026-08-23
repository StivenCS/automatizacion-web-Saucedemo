package saucedemo.tasks;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.List;

import static saucedemo.userinterface.CartPage.REMOVE_BUTTONS;

public class RemoveProduct implements Task {

    public static RemoveProduct fromCart() {
        return Tasks.instrumented(RemoveProduct.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<WebElementFacade> removeButtons = REMOVE_BUTTONS.resolveAllFor(actor);
        removeButtons.get(0).click();
    }
}
