package saucedemo.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import saucedemo.questions.VerifyMessage;
import saucedemo.tasks.Login;
import saucedemo.utils.ConfigReader;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;
import static saucedemo.userinterface.HomePage.SUCCESS_LOGIN_MESSAGE;
import static saucedemo.userinterface.LoginPage.INVALID_CREDENTIALS_MESSAGE;
import static saucedemo.utils.Constants.BASE_URL_LOGIN;
import static saucedemo.utils.Constants.SHORT_WAIT_SECONDS;

public class LoginSaucedemoStepDefinitions {
    @Managed
    WebDriver driver;

    @Dado("que stiven castro quiere ingresar a la pagina de saucedemo")
    public void stivenCastroOpensTheSaucedemoLoginPage() {
        Actor actor = Actor.named("Tester");
        OnStage.theActorCalled(actor.getName()).can(BrowseTheWeb.with(driver));
        OnStage.theActorCalled(actor.getName()).wasAbleTo(Open.url(BASE_URL_LOGIN));
    }

    @Cuando("el se autentica con credenciales validas")
    public void heLogsInWithValidCredentials() {
        String username = ConfigReader.get("saucedemo.credentials.valid.username");
        String password = ConfigReader.get("saucedemo.credentials.valid.password");
        OnStage.theActorInTheSpotlight().attemptsTo(Login.withCredentials(username, password));
    }

    @Cuando("el se autentica con credenciales invalidas")
    public void heLogsInWithInvalidCredentials() {
        String username = ConfigReader.get("saucedemo.credentials.valid.username");
        String password = ConfigReader.get("saucedemo.credentials.invalid.password");
        OnStage.theActorInTheSpotlight().attemptsTo(Login.withCredentials(username, password));
    }

    @Entonces("el verifica el mensaje de login exitoso")
    public void heVerifiesTheSuccessfulLoginMessage() {
        String messageExpected = ConfigReader.get("saucedemo.messages.login.success");
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(SUCCESS_LOGIN_MESSAGE, isVisible()).forNoMoreThan(SHORT_WAIT_SECONDS).seconds());
        OnStage.theActorInTheSpotlight().should(seeThat(VerifyMessage.expected(SUCCESS_LOGIN_MESSAGE), equalTo(messageExpected)));
    }

    @Entonces("el verifica el mensaje de login fallido")
    public void heVerifiesTheFailedLoginMessage() {
        String messageExpected = ConfigReader.get("saucedemo.messages.login.error");
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(INVALID_CREDENTIALS_MESSAGE, isVisible()).forNoMoreThan(SHORT_WAIT_SECONDS).seconds());
        OnStage.theActorInTheSpotlight().should(seeThat(VerifyMessage.expected(INVALID_CREDENTIALS_MESSAGE), equalTo(messageExpected)));
    }
}
