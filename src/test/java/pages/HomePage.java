package pages;

import framework.enums.urls.Urls;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;

@Component
public class HomePage extends BasePage {

    private static final String PAGE_MAIN_ELEMENT = ".topbar .user-main";
    private static final String CALENDAR_FORM_BUTTON = ".icon.icon-calendar";

    @Override
    protected String getPageUrl() { return Urls.BASE.getHomeUrl(); }

    @Override
    public String getMainPageElement(){
        return PAGE_MAIN_ELEMENT;
    }

    public void switchToCalendarForm(){
        $(CALENDAR_FORM_BUTTON).click();
    }


}
