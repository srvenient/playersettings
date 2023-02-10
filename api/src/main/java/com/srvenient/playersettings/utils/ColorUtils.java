package com.srvenient.playersettings.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ColorUtils {

    public static String colorize(String text) {
        if (
                Bukkit.getVersion().contains("1.16")
                        || Bukkit.getVersion().contains("1.17")
                        || Bukkit.getVersion().contains("1.18")
                        || Bukkit.getVersion().contains("1.19")
        ) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(text);

            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");

                match = pattern.matcher(text);
            }
        }

        text = ChatColor.translateAlternateColorCodes('&', text);

        return text;
    }

    public static List<String> colorize(List<String> texts) {
        texts.replaceAll(ColorUtils::colorize);

        return texts;
    }

}
