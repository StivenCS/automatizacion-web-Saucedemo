package saucedemo.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/product_details.feature",
        glue = "saucedemo.stepdefinitions",
        tags = "@ValidarProducto",
        plugin = {"pretty"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ProductDetailsRunner {
}
