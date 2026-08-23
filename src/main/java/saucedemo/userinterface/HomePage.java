package saucedemo.userinterface;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class HomePage {

    public static final Target SUCCESS_LOGIN_MESSAGE = Target.the("Swag Labs app logo").located(By.xpath("//div[@class='app_logo' and text()='Swag Labs']"));

}
