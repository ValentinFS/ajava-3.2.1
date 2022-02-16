package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement login = $("[data-test-id=login] input");
    private SelenideElement password = $("[data-test-id=password] input");
    private SelenideElement button = $("[data-test-id=action-login].button");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        login.setValue(authInfo.getLogin());
        password.setValue(authInfo.getPassword());
        button.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        login.setValue(authInfo.getLogin());
        password.setValue(authInfo.getPassword());
        button.click();
        error.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void getDeleteAccount() {
        login.doubleClick().sendKeys(Keys.DELETE);
        password.doubleClick().sendKeys(Keys.DELETE);
    }

    public void getErrorOverAttempts() {
        $(byText("Ошибка!")).shouldBe(visible);
//        $(byText("Неверно указан код!")).shouldBe(visible);
    }
}