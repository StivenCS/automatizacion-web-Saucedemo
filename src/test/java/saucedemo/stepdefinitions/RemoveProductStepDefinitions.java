package saucedemo.stepdefinitions;

import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;
import saucedemo.tasks.RemoveProduct;

public class RemoveProductStepDefinitions {

    @Y("el remueve un producto del carrito")
    public void heRemovesAProductFromTheCart() {
        OnStage.theActorInTheSpotlight().attemptsTo(RemoveProduct.fromCart());
    }
}
