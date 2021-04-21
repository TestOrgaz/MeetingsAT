package framework.configuration;

import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.reflect.TypeToken;
import framework.enums.urls.Browser;
import framework.utils.FileManager;
import framework.utils.JSONUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;

import static java.lang.String.format;

// Необходимо довести до ума
@Configuration
public class SpringDriverConfiguration {
    public static final Long ELEMENT_TIMEOUT = framework.configuration.Configuration.getInstance().getTimeout("timeout.element.wait");
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    private String getPlatformName() {
        String platform;
        if (PLATFORM.contains("win")) {
            platform = "windows";
        } else if (PLATFORM.contains("linux")) {
            platform = "linux";
        } else if (PLATFORM.contains("mac")) {
            platform = "mac os";
        } else {
            throw new IllegalArgumentException(format("Platform %s is not supported", PLATFORM));
        }
        return platform;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        String fileWithOptions = FileManager.getAbsolutePath(framework.configuration.Configuration.getInstance().getProperty("browser.driver_options.file"));
        JSONObject options = (JSONObject) JSONUtils.readFromFile(fileWithOptions).get(Browser.CHROME.getBrowser());
        options = (JSONObject) options.get(getPlatformName());

        String[] args = JSONUtils.getStringArray(options, "args");
        chromeOptions.addArguments(args);
        JSONObject experimentalOptions = (JSONObject) options.get("experimentalOptions");
        String[] excludeSwitches = JSONUtils.getStringArray(experimentalOptions, "excludeSwitches");
        chromeOptions.setExperimentalOption("excludeSwitches", excludeSwitches);

        chromeOptions.setExperimentalOption("prefs", JSONUtils.mapToObject((JSONObject) experimentalOptions.get("prefs"), new TypeToken<HashMap<String, Object>>() {
        }.getType()));

        return chromeOptions;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        String fileWithOptions = FileManager.getAbsolutePath(framework.configuration.Configuration.getInstance().getProperty("browser.driver_options.file"));
        JSONObject options = (JSONObject) JSONUtils.readFromFile(fileWithOptions).get(Browser.FIREFOX.getBrowser());
        options = (JSONObject) options.get(getPlatformName());

        String[] args = JSONUtils.getStringArray(options, "args");
        firefoxOptions.addArguments(args);

        return firefoxOptions;
    }

    @Bean(name = "chrome")
    public WebDriver chromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = getChromeOptions();
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(driver);

        com.codeborne.selenide.Configuration.timeout = ELEMENT_TIMEOUT;
        com.codeborne.selenide.Configuration.fastSetValue = true;
        com.codeborne.selenide.Configuration.pageLoadStrategy = "normal";

        return WebDriverRunner.getWebDriver();
    }

    @Bean(name = "firefox")
    public WebDriver geckoDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = getFirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        WebDriverRunner.setWebDriver(driver);

        com.codeborne.selenide.Configuration.timeout = ELEMENT_TIMEOUT;
        com.codeborne.selenide.Configuration.fastSetValue = true;
        com.codeborne.selenide.Configuration.pageLoadStrategy = "normal";

        return WebDriverRunner.getWebDriver();
    }

}




