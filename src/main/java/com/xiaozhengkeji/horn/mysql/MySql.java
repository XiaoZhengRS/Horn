package com.xiaozhengkeji.horn.mysql;

import com.mysql.jdbc.PreparedStatement;
import com.xiaozhengkeji.horn.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySql {
    public static void go() {
        Bukkit.getLogger().info("§4>检测到第一次使用Mysql,开始为您创建表!");
        String sql  = "CREATE TABLE `lbb` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',\n" +
                "  `player` varchar(255) DEFAULT NULL COMMENT '玩家名',\n" +
                "  `lb` int(255) DEFAULT NULL COMMENT '剩余喇叭',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        //开始执行
        try {
            PreparedStatement ps = (PreparedStatement) Data.Conn.prepareStatement(sql);
            ps.execute();
            Bukkit.getLogger().info("数据库创建表成功!");
            Bukkit.getLogger().info("Mysql驱动模式完全开启!");
            Data.Config.set("Mysql", true);
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("数据库创建表失败!");
        }
    }

    //获取玩家剩余喇叭

    public static int getPlayerLb(Player p) throws Exception{
        String sql = "SELECT id,player,lb FROM lbb WHERE player = ?";
        PreparedStatement ps = (PreparedStatement) Data.Conn.prepareStatement(sql);
        ps.setString(1, p.getName());
        ResultSet rs = ps.executeQuery();
        //光标只像最后一行
        rs.last();
        //获取当前行
        int rowCount = rs.getRow();
        if (rowCount == 1) {
            int lb = rs.getInt("lb");
            return lb;
        }else {
            sql = "INSERT INTO lbb (id,player,lb) VALUES (null,?,0)";
            ps = (PreparedStatement) Data.Conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.executeUpdate();
            return 0;
        }
    }

    //增减玩家喇叭

    public static void getPlayerUP(Player p, int lb){
        try {
            int data = getPlayerLb(p);
            data = data + lb;
            String sql = "UPDATE lbb SET lb = " + data + " WHERE player = ?";
            PreparedStatement pss = (PreparedStatement) Data.Conn.prepareStatement(sql);
            pss.setString(1,p.getName());
            pss.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
