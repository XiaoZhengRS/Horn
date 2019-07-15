package com.xiaozhengkeji.horn;

import com.xiaozhengkeji.horn.mysql.MySql;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class papi extends PlaceholderHook {
    //注册到HOOK

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("s")) {
            try {
                return String.valueOf(MySql.getPlayerLb(player));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "作者小正";
    }
}
