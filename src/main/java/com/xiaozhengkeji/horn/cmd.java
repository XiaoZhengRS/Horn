package com.xiaozhengkeji.horn;

import com.xiaozhengkeji.horn.data.Data;
import com.xiaozhengkeji.horn.mysql.MySql;
import com.xiaozhengkeji.horn.tcp.TcpClient;
import com.xiaozhengkeji.horn.tcp.TcpServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(">>请务必以玩家身份打开!");
            return true;
        }
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("lb")) {
            if (args[0].equalsIgnoreCase("mundee")){
                MySql.getPlayerUP(p, Integer.parseInt(args[1]));
                return false;
            }
            try {
                if (MySql.getPlayerLb(p) > 0) {
                    String say = "";
                    for (int i = 0; i < args.length; i++) {
                        say = say + args[i];
                    }
                    say = Tool.isStringMess(p,Data.mes1,say);

                    MySql.getPlayerUP(p, -1);
                    Tool.messALL(say);
                    for (int data : Data.Server) {
                        try {
                            new TcpClient(data, say).talk();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    p.sendMessage("喇叭数量不足");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        if (label.equalsIgnoreCase("lbsys")) {
            if (p.hasPermission("horn.sys")) {
                if (args[0].equalsIgnoreCase("lb")) {
                    try {
                        String say = "";
                        for (int i = 1; i < args.length; i++) {
                            say = say + args[i];
                        }
                        say = Tool.isStringMess(p,Data.mes2,say);
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
            }
            if (args[0].equalsIgnoreCase("give")) {
                MySql.getPlayerUP(Bukkit.getPlayer(args[1]), Integer.parseInt(args[2]));
                p.sendMessage("增减完成!");
                return true;
            }
        } else {
            p.sendMessage("权限不足:必须拥有:horn.sys");
        }


        return false;
    }

    public static void go() {
        new BukkitRunnable() {
            @Override
            public void run() {
                TcpServer tcpServer = new TcpServer();
                try {
                    tcpServer.talk();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Horn.getProvidingPlugin(Horn.class));
    }

}
