package saucedemo.utils;

import net.datafaker.Faker;
import saucedemo.models.CheckoutInfo;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static CheckoutInfo generateCheckoutInfo() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String postalCode = faker.address().zipCode();

        return new CheckoutInfo(firstName, lastName, postalCode);
    }
}
