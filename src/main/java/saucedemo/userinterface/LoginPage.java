package saucedemo.userinterface;


import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final Target USER = Target.the("Send email").located(By.xpath("//input[@id='user-name']"));
    public static final Target PASSWORD = Target.the("Send password").located(By.xpath("//input[@id='password']"));
    public static final Target GET_INTO = Target.the("Click ingresar").located(By.id("login-button"));
    public static final Target INVALID_CREDENTIALS_MESSAGE = Target.the("Invalid credentials error message").located(By.cssSelector("h3[data-test='error']"));
}
