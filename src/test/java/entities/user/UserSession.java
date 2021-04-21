package entities.user;

import lombok.Getter;
import lombok.Setter;

public class UserSession {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    private Integer lifeTime;
}

