package com.srvenient.playersettings.executor.type;

import com.srvenient.playersettings.executor.SettingExecutor;
import com.srvenient.playersettings.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class JumpModelExecutor implements SettingExecutor {

    @Override
    public @NotNull String getId() {
        return "jump";
    }

    @Override
    public void execute(@NotNull User user) {
        final Player player = user.getPlayer();

        if (user.getSettingState(getId()) == 0) {
            user.updateState(getId(), (byte) 1);
            player.removePotionEffect(PotionEffectType.JUMP);

            return;
        }

        if (user.getSettingState(getId()) == 1) {
            user.updateState(getId(), (byte) 0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
        }
    }
}
