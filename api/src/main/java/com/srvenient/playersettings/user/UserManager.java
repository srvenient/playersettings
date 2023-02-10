package com.srvenient.playersettings.user;

import java.util.UUID;

public interface UserManager {

    User getUser(UUID uuid);

    void updateUser(User user);

}
