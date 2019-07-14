package com.xiaozhengkeji.horn;

import lk.vexview.VexView;
import lk.vexview.api.VexViewAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Tool {
    //发送全部玩家

    public static void messALL(String str){
        Collection<? extends Player> OnlinePlayer = Bukkit.getOnlinePlayers();
        usePlayerTitle(OnlinePlayer, str);
    }
    public static void usePlayerTitle(Collection<? extends Player> ps,String say) {
        ps.forEach(player -> VexViewAPI.sendFlowView(player, say, 5));
        ps.forEach(player -> player.sendMessage(say));
    }

    //本子替换

    public static String isStringMess(Player p, String str, String mes){
        str = str.replace("<player>", p.getName());
        str = str.replace("<mes>", mes);
        return str;
    }
}
