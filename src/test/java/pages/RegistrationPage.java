package pages;

import io.qameta.allure.Step;
import org.springframework.stereotype.Component;

@Component
public class RegistrationPage extends BasePage {

    private static final String PAGE_MAIN_ELEMENT = ".topbar .user-main";

    @Override
    public String getMainPageElement(){
        return PAGE_MAIN_ELEMENT;
    }
}
