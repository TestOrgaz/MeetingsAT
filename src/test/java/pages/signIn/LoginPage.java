package pages.signIn;

import entities.user.User;
import framework.enums.localization.Localization;
import framework.enums.urls.Urls;
import io.qameta.allure.Step;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;
import pages.BasePage;
import pages.HomePage;

import static com.codeborne.selenide.Condition.*;
import static java.lang.String.format;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Component
public class LoginPage extends BasePage {

    @Autowired
    private HomePage homePage;
    private PasswordResetPage passwordResetPage;

    private static final String PAGE_MAIN_ELEMENT = ".signin-form.special";
    private static final String EMAIL_INPUT = "input[id='e-mail']";
    private static final String PASSWORD_INPUT = "input[id='password']";
    private static final String EMAIL_PLACEHOLDER = "label[class='helper-field'][for='e-mail']";
    private static final String PASSWORD_PLACEHOLDER = "label[class='helper-field'][for='password']";
    private static final String LOGIN_BUTTON = "//button/span[text()='%s']";
    private static final String REMEMBER_ME_CHECKBOX = "//div[@class='checkable-text'][text()='%s']";
    private static final String REMEMBER_ME_CHECKBOX_STATE = "//div[@class='checkable-input']/div";
    private static final String RESET_PASSWORD_BUTTON = "a[data-bind*='common_restore']";
    private static final String PAGE_CURRENT_LANGUAGE = ".container-fluid .select-lang-current-title";
    private static final String LANGUAGE_SWITCH_DROPDOWN = "//ul[@class='select-lang-dropdown']//a[text()='%s']";

    @Override
    protected String getPageUrl() {
        return Urls.SIGNIN.getHomeUrl();
    }

    @Override
    public String getMainPageElement(){
        return PAGE_MAIN_ELEMENT;
    }

    @Step("Авторизоваться под пользователем")
    public HomePage logIn(String email, String password) {
        $(EMAIL_INPUT).shouldBe(visible).sendKeys(email);
        $(PASSWORD_INPUT).sendKeys(password);
        $x(format(LOGIN_BUTTON, Localization.getInstance().getValue("sign_in_page.log_in"))).click();
        return homePage;
    }

    @Step("Авторизоваться под пользователем")
    public HomePage logIn(User user) {
        if (user != null) {
            return logIn(user.getEmail(), user.getPassword());
        }
        throw new NullPointerException("Пользователь не задан");
    }

    @Step("Получить состояние кнопки 'Запомнить меня'")
    public boolean getRememberMeCheckboxState() {
        return $x(REMEMBER_ME_CHECKBOX_STATE).has(attribute("class", "jq-checkbox checked"));
    }

    @Step("Включить флаг 'Запомнить меня'")
    public LoginPage turnOnRememberMeButton() {
        if (!getRememberMeCheckboxState()) {
            $x(format(REMEMBER_ME_CHECKBOX, Localization.getInstance().getValue("sign_in_page.remember_me"))).click();
        }
        return this;
    }

    @Step("Выключить флаг 'Запомнить меня'")
    public LoginPage turnOffRememberMeButton() {
        if (getRememberMeCheckboxState()) {
            $x(format(REMEMBER_ME_CHECKBOX, Localization.getInstance().getValue("sign_in_page.remember_me"))).click();
        }
        return this;
    }

    @Step("Получить значение плейсхолдера поля email")
    public String getEmailPlaceholder() {
        return $(EMAIL_PLACEHOLDER).getText();
    }

    @Step("Получить значение плейсхолдера поля пароль")
    public String getPasswordPlaceholder() {
        return $(PASSWORD_PLACEHOLDER).getText();
    }

    @Step("Получить текущий язык страницы")
    public String getAuthPageLanguage() {
        $(PAGE_CURRENT_LANGUAGE).shouldBe(visible);
        return $(PAGE_CURRENT_LANGUAGE).text();
    }

    @Step("Нажать на кнопку восстановления пароля")
    public PasswordResetPage clickResetPasswordButton() {
        $(RESET_PASSWORD_BUTTON).click();
        return passwordResetPage;
    }

    @Step("Изменить язык на странице авторизации")
    /**
     * @param langKey ru - изменить язык на русский, en - изменить язык на английский
     */
    public LoginPage changeAuthPageLanguage(String langKey) {
        if (!Strings.isNullOrEmpty(langKey)) {
            $(PAGE_CURRENT_LANGUAGE).shouldBe(visible).hover();
            langKey = langKey.toLowerCase() == "ru" ? "РУС" : "ENG";
            $x(format(LANGUAGE_SWITCH_DROPDOWN, langKey)).click();
        }
        return this;
    }
}
