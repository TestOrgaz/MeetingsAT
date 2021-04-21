package tests.meetings;

import framework.configuration.SpringDriverConfiguration;
import framework.configuration.SpringJDBCConfiguration;
import framework.configuration.SpringObjectConfiguration;
import framework.driver.DriverContainer;
import framework.enums.localization.Localization;
import framework.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.sql.Driver;

import static java.lang.String.format;

@SpringBootTest(classes = {SpringObjectConfiguration.class, SpringJDBCConfiguration.class})
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected Logger LOG;

    @BeforeSuite
    public void beforeSuite() {
        Localization.getInstance().setLocale(Localization.Locale.RU);
    }

    @BeforeMethod
    public void beforeMethod(ITestContext testContext) {
        DriverContainer.setDrivers();
        LOG.info(format("Test '%s' started", testContext.getName()));
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestContext testContext) {
        DriverContainer.quit();
        LOG.info(format("Test '%s' finished", testContext.getName()));
    }


}
