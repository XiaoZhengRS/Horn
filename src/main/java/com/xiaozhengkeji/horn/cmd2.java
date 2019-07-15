package com.xiaozhengkeji.horn;

import com.xiaozhengkeji.horn.data.Data;
import com.xiaozhengkeji.horn.mysql.MySql;
import com.xiaozhengkeji.horn.tcp.TcpClient;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static org.bukkit.Bukkit.getPlayer;

public class cmd2 implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0] == null){
                p.sendMessage("参数未输入!");
                return true;
            }
            if (p.hasPermission("horn.sys")) {
                if (args[0].equalsIgnoreCase("lb")) {
                    try {
                        String say = "";
                        for (int i = 1; i < args.length; i++) {
                            say = say + " " + args[i];
                        }
                        say = Tool.isStringMess(p, Data.mes2, say);
                        MySql.getPlayerUP(p, -1);
                        Tool.messALL(say);
                        for (int data : Data.Server) {
                            try {
                                new TcpClient(data, say).talk();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (args[0].equalsIgnoreCase("give")) {
                    MySql.getPlayerUP(getPlayer(args[1]), Integer.parseInt(args[2]));
                    p.sendMessage("增减完成!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("lk")) {
                    try {
                        p.sendMessage("剩余喇叭:" + MySql.getPlayerLb(getPlayer(args[1])));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                p.sendMessage("权限不足:必须拥有:horn.sys");
            }
        }else {

        }

        return false;
    }
}
