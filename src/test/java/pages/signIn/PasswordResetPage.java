package pages.signIn;

import framework.enums.localization.Localization;
import framework.utils.FluentWaitCondition;
import io.qameta.allure.Step;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;
import pages.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.format;

@Component
public class PasswordResetPage extends BasePage {
    private static final String PAGE_MAIN_ELEMENT = ".topbar .user-main";
    private static final String EMAIL_INPUT = "input[id='e-mail']";
    private static final String PAGE_CURRENT_LANGUAGE = ".container-fluid .select-lang-current-title";
    private static final String LANGUAGE_SWITCH_DROPDOWN = "//ul[@class='select-lang-dropdown']//a[text()='%s']";
    private static final String PASSWORD_RECOVERY_BUTTON = "input[data-bind*='recovery_password']";
    private static final String RECOVERY_SUCCESS_TITLE = "#sign-in h2[data-bind*='recovery_success_title']";
    private static final String RECOVERY_SUCCESS_SEND_TITLE = "#sign-in span[data-bind*='recovery_success_send']";
    private static final String RECOVERY_SUCCESS_SEND_EMAIL = "#sign-in b[data-bind*='form.data.email']";

    @Override
    public String getMainPageElement(){
        return PAGE_MAIN_ELEMENT;
    }
    @Step("Заполнить email для восстановления пароля")
    public PasswordResetPage setEmail(String email) {
        $(EMAIL_INPUT).sendKeys(email);
        return this;
    }

    @Step("Получить текущий язык страницы")
    public String getAuthPageLanguage() {
        $(PAGE_CURRENT_LANGUAGE).shouldBe(visible);
        return $(PAGE_CURRENT_LANGUAGE).text();
    }

    @Step("Изменить язык на странице восстановления пароля")
    /**
     * @param langKey ru - изменить язык на русский, en - изменить язык на английский
     */
    public PasswordResetPage changeAuthPageLanguage(String langKey) {
        if (!Strings.isNullOrEmpty(langKey)) {
            $(PAGE_CURRENT_LANGUAGE).shouldBe(visible).hover();
            langKey = langKey.toLowerCase() == "ru" ? "РУС" : "ENG";
            $x(format(LANGUAGE_SWITCH_DROPDOWN, langKey)).click();
        }
        return this;
    }

    @Step("Нажать на кнопку восстановления пароля")
    public PasswordResetPage clickPasswordRecoveryButton() {
        $(PASSWORD_RECOVERY_BUTTON).click();
        return this;
    }

    @Step("Проверить наличие заголовка о восстановлении пароля")
    public boolean checkRecoverySuccessTitleExists() {
        return FluentWaitCondition.ShouldBeVisible($(RECOVERY_SUCCESS_TITLE)) && $(RECOVERY_SUCCESS_TITLE).has(text(Localization.getInstance().getValue("password_recovery_page.success")));
    }

    @Step("Проверить корректность текста об отправке письма с инструкциями по смене пароля")
    public boolean checkRecoveryMessageSendText(String email) {
        return $(RECOVERY_SUCCESS_SEND_TITLE).has(text(Localization.getInstance().getValue("password_recovery_page.message_send")))
                && $(RECOVERY_SUCCESS_SEND_EMAIL).has(text(email));
    }
}
