package com.srvenient.playersettings.user.sql;

import com.srvenient.playersettings.user.User;

import java.util.UUID;

public interface UserSQLManager {

    User getPlayer(UUID uuid);

    void saveUser(User user);

    void updateUser(User user);

}
