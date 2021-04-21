package framework.driver;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Интерфейс, описывающий фабрику для создания экземпляра веб-драйвера.
 */
public interface IDriverFactory {
    RemoteWebDriver getDriver();
}
