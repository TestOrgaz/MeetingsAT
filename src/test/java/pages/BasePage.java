package pages;

import com.codeborne.selenide.WebDriverRunner;
import framework.utils.FluentWaitCondition;
import framework.utils.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.String.format;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {

    @Autowired
    protected Logger LOG;

    public abstract String getMainPageElement();

    /**
     * Проверить, что страница загрузилась
     */
    public void assertLoaded() {
        waitForAjaxAndJquery();
        Assert.assertTrue(FluentWaitCondition.ShouldBeVisible($(getMainPageElement())), format("Страница %s не загрузилась", this.getClass().getSimpleName()));
    }

    /**
     * Дождаться пока отработает AJAX и jQuery
     */
    private static void waitForAjaxAndJquery() {
        waitForJquery();
        waitForAjax();
    }

    /**
     * Дождаться пока отработает jQuery
     */
    public static void waitForJquery() {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 30);
        wait.until((WebDriver dr) -> (boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return jQuery.active == 0"));
    }

    /**
     * Дождаться пока отработает AJAX
     */
    public static void waitForAjax() {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 30);
        wait.until((WebDriver dr) -> ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Открытие страницы по URL.
     */
    public void open() {
        String url = getPageUrl();
        LOG.info("Navigate to URL: " + url);
        WebDriverRunner.getWebDriver().navigate().to(url);
    }

    /**
     * Получение URL страницы. Должен быть переопределен в каджом классе, описывающем конкретную страницу приложения.
     *
     * @return URL страницы.
     */
    protected String getPageUrl() {
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * Возвращает текущий url
     *
     * @return текущий url
     */
    public String getCurrentUrl() {
        return WebDriverRunner.url();
    }


}
