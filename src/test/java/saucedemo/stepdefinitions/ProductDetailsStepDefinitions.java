package saucedemo.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.Text;
import saucedemo.tasks.SelectProducts;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static saucedemo.userinterface.CartPage.*;
import static saucedemo.userinterface.InventoryPage.*;

public class ProductDetailsStepDefinitions {

    @Cuando("el consulta el precio y la descripcion del primer producto del inventario")
    public void heChecksTheFirstProductDetails() {
        Actor actor = OnStage.theActorInTheSpotlight();

        actor.remember("productName", PRODUCT_NAMES.resolveAllFor(actor).get(0).getText());
        actor.remember("productDescription", PRODUCT_DESCRIPTIONS.resolveAllFor(actor).get(0).getText());
        actor.remember("productPrice", PRODUCT_PRICES.resolveAllFor(actor).get(0).getText());
    }

    @Y("el agrega ese producto al carrito")
    public void heAddsThatProductToTheCart() {
        OnStage.theActorInTheSpotlight().attemptsTo(SelectProducts.quantity(1));
    }

    @Entonces("el precio y la descripcion en el carrito coinciden con los del inventario")
    public void heVerifiesTheCartMatchesInventoryDetails() {
        Actor actor = OnStage.theActorInTheSpotlight();

        String expectedName = actor.recall("productName");
        String expectedDescription = actor.recall("productDescription");
        String expectedPrice = actor.recall("productPrice");

        actor.should(
                seeThat(Text.of(CART_ITEM_NAME), equalTo(expectedName)),
                seeThat(Text.of(CART_ITEM_DESCRIPTION), equalTo(expectedDescription)),
                seeThat(Text.of(CART_ITEM_PRICE), equalTo(expectedPrice))
        );
    }
}
