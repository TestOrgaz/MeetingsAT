package framework.enums.urls;

public enum UrlsPatterns {
    BASE("^https:\\/\\/alpha\\.webinar\\.ru"),
    PROD_BASE("^https:\\/\\/events\\.webinar\\.ru"),
    EVENT("\\/event\\/\\d+\\/\\d+\\/edit$");

    private String pattern;

    UrlsPatterns(String pattern) {
        this.pattern = pattern;
    }

    public String getBaseAndTargetPattern() {
        return BASE.getPattern() + pattern;
    }
    public String getBaseAndTargetProdPattern() {
        return PROD_BASE.getPattern() + pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
