package tests.meetings;

import entities.user.User;
import entities.user.UserGenerator;
import framework.utils.SimpleWait;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.*;
import pages.HomePage;
import pages.signIn.LoginPage;

public class AuthTests extends BaseTest {

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private UserGenerator user;
    @Autowired
    private HomePage homePage;


    @DataProvider(name = "user")
    public Object[][] createData() {
        var failedUser = user.generateRandomProfile();
        var participant = user.getUser("creator2");

        return new Object[][]{new Object[]{participant}, new Object[]{failedUser}};
    }

    @Test(dataProvider = "user")
    public void AuthTestSample(User test) { // просто для дебага
        loginPage.open();
        loginPage.logIn(test).
                assertLoaded();
        homePage.switchToCalendarForm();
    }

}
