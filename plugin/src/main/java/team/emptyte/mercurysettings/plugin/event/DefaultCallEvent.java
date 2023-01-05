package team.emptyte.mercurysettings.plugin.event;

import team.emptyte.mercurysettings.api.event.CallEvent;
import team.emptyte.mercurysettings.api.event.EventType;
import team.emptyte.mercurysettings.api.user.User;
import team.emptyte.mercurysettings.plugin.MercurySettings;

public class DefaultCallEvent implements CallEvent {

    private final MercurySettings plugin;

    public DefaultCallEvent(MercurySettings plugin) {
        this.plugin = plugin;
    }

    @Override
    public void callEvent(User user, String type) {
        switch (type) {
            case "visibility" -> EventType.VISIBILITY.getAction().execute(plugin, user);
            case "chat" -> EventType.CHAT.getAction().execute(plugin, user);
            case "double-jump" -> EventType.DOUBLE_JUMP.getAction().execute(plugin, user);
            case "mount" -> EventType.MOUNT.getAction().execute(plugin, user);
            case "fly" -> EventType.FLY.getAction().execute(plugin, user);
        }
    }
}
