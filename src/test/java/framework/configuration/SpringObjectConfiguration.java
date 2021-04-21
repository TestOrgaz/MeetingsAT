package framework.configuration;

import entities.user.UserGenerator;
import framework.driver.DriverContainer;
import framework.utils.Logger;
import framework.utils.RandomValuesGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pages.HomePage;
import pages.signIn.LoginPage;

@Configuration
public class SpringObjectConfiguration {

    @Bean
    LoginPage loginPage() {
        return new LoginPage();
    }

    @Bean
    HomePage homePage() {
        return new HomePage();
    }

    @Bean
    Logger LOG() {
        return Logger.getInstance();
    }

    @Bean
    UserGenerator userGenerator() {
        return new UserGenerator();
    }

    @Bean
    RandomValuesGenerator randomGenerator() {
        return new RandomValuesGenerator();
    }

}
