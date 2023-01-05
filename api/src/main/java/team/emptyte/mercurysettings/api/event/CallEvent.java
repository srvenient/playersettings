package team.emptyte.mercurysettings.api.event;

import team.emptyte.mercurysettings.api.user.User;

public interface CallEvent {

    void callEvent(User user, String type);

}
