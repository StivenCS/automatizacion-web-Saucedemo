package saucedemo.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import saucedemo.models.CheckoutInfo;
import saucedemo.tasks.FinalizePurchase;
import saucedemo.tasks.SelectProducts;
import saucedemo.utils.ConfigReader;
import saucedemo.utils.DataGenerator;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;
import static saucedemo.userinterface.CheckoutPage.SUCCESS_MESSAGE;
import static saucedemo.userinterface.InventoryPage.CART_BADGE;
import static saucedemo.utils.Constants.MEDIUM_WAIT_SECONDS;

public class PurchaseProductsStepDefinitions {

    @Cuando("el selecciona {string} productos y los agrega al carrito")
    public void heSelectsProductsAndAddsThemToTheCart(String quantity) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SelectProducts.quantity(Integer.parseInt(quantity))
        );
    }

    @Entonces("se verifica que se agregaron {string} productos al carrito")
    @Entonces("se verifica que quedan {string} productos en el carrito")
    public void heVerifiesTheCartHasTheExpectedQuantity(String quantity) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(Text.of(CART_BADGE), equalTo(quantity)));
    }

    @Y("el finaliza la compra con sus datos personales")
    public void heFinalizesThePurchaseWithHisPersonalData() {
        CheckoutInfo checkoutInfo = DataGenerator.generateCheckoutInfo();
        OnStage.theActorInTheSpotlight().attemptsTo(
                FinalizePurchase.withInfo(checkoutInfo)
        );
    }

    @Entonces("el verifica el mensaje de compra exitosa")
    public void heVerifiesTheSuccessfulPurchaseMessage() {
        String messageExpected = ConfigReader.get("saucedemo.messages.purchase.success");
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(SUCCESS_MESSAGE, isVisible()).forNoMoreThan(MEDIUM_WAIT_SECONDS).seconds());
        OnStage.theActorInTheSpotlight().should(seeThat(Text.of(SUCCESS_MESSAGE), equalTo(messageExpected)));
    }
}
