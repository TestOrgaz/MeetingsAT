package framework.utils;

import framework.configuration.Configuration;
import org.apache.commons.lang3.RandomStringUtils;

import static java.lang.String.format;

public class RandomValuesGenerator {

    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    public static String generateRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length).toLowerCase();
    }

    public static String generateRandomEmail() {
        return generateRandomEmailInternal(Configuration.getInstance().getProperty("mailinator.email.template"));
    }

    private static String generateRandomEmailInternal(String emailTemplate) {
        Integer randomPartLength = 9;
        return format(emailTemplate, generateRandomString(randomPartLength));
    }
}
