package saucedemo.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class InventoryPage {
    public static final Target ADD_TO_CART_BUTTONS = Target.the("Add to cart buttons").located(By.cssSelector(".btn_inventory"));
    public static final Target CART_ICON = Target.the("Shopping cart icon").located(By.cssSelector(".shopping_cart_link"));
    public static final Target CART_BADGE = Target.the("Shopping cart item count badge").located(By.cssSelector(".shopping_cart_badge"));
    public static final Target PRODUCT_NAMES = Target.the("Product names in inventory").located(By.cssSelector(".inventory_item_name"));
    public static final Target PRODUCT_DESCRIPTIONS = Target.the("Product descriptions in inventory").located(By.cssSelector(".inventory_item_desc"));
    public static final Target PRODUCT_PRICES = Target.the("Product prices in inventory").located(By.cssSelector(".inventory_item_price"));
}
