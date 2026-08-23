package saucedemo.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class VerifyMessage implements Question<String> {

    private Target target;

    public VerifyMessage(Target target) {
        this.target = target;
    }

    public static VerifyMessage expected(Target target) {
        return new VerifyMessage(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(target).answeredBy(actor);
    }
}
