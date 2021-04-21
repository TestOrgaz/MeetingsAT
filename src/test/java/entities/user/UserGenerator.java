package entities.user;


import framework.utils.RandomValuesGenerator;
import framework.utils.JSONUtils;
import lombok.var;

public class UserGenerator {

    private static final String USERS_FILE = "users.json";

    private static String getTestDataFilePath(String fileName) {
        return framework.configuration.Configuration.getInstance().getTestDataPath(fileName);
    }

    public User generateRandomProfile() {
        Integer randomLength = 8;
        var user = new User();
        user.setEmail(RandomValuesGenerator.generateRandomEmail());
        user.setFirstName(RandomValuesGenerator.generateRandomString(randomLength));
        user.setLastName(RandomValuesGenerator.generateRandomString(randomLength));
        user.setPassword(RandomValuesGenerator.generateRandomString(randomLength));
        user.setAbout(RandomValuesGenerator.generateRandomString(randomLength));
        user.setChatName(RandomValuesGenerator.generateRandomString(randomLength));
        user.setCompany(RandomValuesGenerator.generateRandomString(randomLength));
        user.setPhone(RandomValuesGenerator.generateRandomNumber(randomLength));
        user.setPosition(RandomValuesGenerator.generateRandomString(randomLength));
        return user;
    }

    public static User getUser(String userKey) {
        String filePath = getTestDataFilePath(USERS_FILE);
        return JSONUtils.mapToObject(filePath, userKey, User.class);
    }

}

