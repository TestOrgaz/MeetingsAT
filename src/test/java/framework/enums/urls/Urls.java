package framework.enums.urls;


import framework.configuration.Configuration;

public enum Urls {
    BASE("/"),
    SIGNIN("/signin"),
    SIGNUP("/signup"),
    FILES("/files"),
    PEOPLE("/people"),
    ONBOARDING("/onboarding"),
    TARIFFS("/business/tariffs"),
    BRANDING("/business/branding"),
    REVENUE("/business/revenue"),
    SIGNIN_RESET("/signin-reset"),
    ORGANIZATION("/business/organization");

    private static final Configuration CONFIG = Configuration.getInstance();

    private String urlPart;

    Urls(String urlPart) {
        this.urlPart = urlPart;
    }

    public String getHomeUrl() {
        return CONFIG.getProperty("url.home") + urlPart;
    }

    public String getEventsUrl() {
        return CONFIG.getProperty("url.events") + urlPart;
    }

    public String getUrlPart() {
        return urlPart;
    }
}
