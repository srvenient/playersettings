package team.emptyte.playersettings.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import team.emptyte.playersettings.api.message.MessageHandler;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.user.User;

public class AsyncPlayerChatListener implements Listener {

    private final MessageHandler messageHandler;

    private final ModelService<User> users;

    public AsyncPlayerChatListener(
            MessageHandler messageHandler, ModelService<User> users) {
        this.messageHandler = messageHandler;
        this.users = users;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = users.findSync(player.getUniqueId().toString());

        if (user == null) {
            return;
        }

        if (user.getSettingStatus("chat") == 1) {
            event.setCancelled(true);
            messageHandler.sendMessages(player, "chat-deactivated");

            return;
        }

        Bukkit.getOnlinePlayers().forEach((players) -> {
            User users = this.users.findSync(players.getUniqueId().toString());

            if (users.getSettingStatus("chat") == 1) {
                event.getRecipients().remove(players);
            }

        });
    }

}
