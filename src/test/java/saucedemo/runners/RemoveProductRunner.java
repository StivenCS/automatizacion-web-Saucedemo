package saucedemo.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/remove_products.feature",
        glue = "saucedemo.stepdefinitions",
        tags = "@RemoverProducto",
        plugin = {"pretty"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RemoveProductRunner {
}
