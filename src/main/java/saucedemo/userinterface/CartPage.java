package saucedemo.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CartPage {
    public static final Target CHECKOUT_BUTTON = Target.the("Checkout button").located(By.id("checkout"));
    public static final Target CART_ITEM_NAME = Target.the("Cart item name").located(By.cssSelector(".cart_item .inventory_item_name"));
    public static final Target CART_ITEM_DESCRIPTION = Target.the("Cart item description").located(By.cssSelector(".cart_item .inventory_item_desc"));
    public static final Target CART_ITEM_PRICE = Target.the("Cart item price").located(By.cssSelector(".cart_item .inventory_item_price"));
    public static final Target REMOVE_BUTTONS = Target.the("Remove buttons in cart").located(By.cssSelector(".cart_item button[id^='remove']"));
}
