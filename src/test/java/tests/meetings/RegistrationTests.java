package tests.meetings;

import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import pages.RegistrationPage;

public class RegistrationTests {
    @Test(description = "blablabla")
    @TmsLink("111")
    @Issue("11111")
    public void vasya(){
        RegistrationPage page = new RegistrationPage();
    }
}
