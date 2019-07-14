package com.xiaozhengkeji.horn;

import com.mysql.jdbc.Connection;
import com.xiaozhengkeji.horn.data.Data;
import com.xiaozhengkeji.horn.listener.RegListeners;
import com.xiaozhengkeji.horn.mysql.MySql;
import com.xiaozhengkeji.horn.tcp.TcpServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Horn extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Data.Config = getConfig();
        Bukkit.getLogger().info("§4>开始加载端口转发程序!");
        Data.MysqlClass = Data.Config.getString("MysqlConfigDefault");
        Data.MysqlUrl = Data.Config.getString("MysqlConfigLogin");
        Data.MysqlUserName = Data.Config.getString("MysqlUserName");
        Data.MysqlUserPassWord = Data.Config.getString("MysqlUserPassword");
        Data.Server = Data.Config.getIntegerList("ServerMass");
        Data.MyServer = Data.Config.getInt("ServerProt");
        Data.mes1 = Data.Config.getString("普通喇叭");
        Data.mes2 = Data.Config.getString("系统喇叭");
        Bukkit.getLogger().info("§4>MysqClass:" + Data.MysqlClass);
        Bukkit.getLogger().info("§4>MysqlUrl:" +  Data.MysqlUrl);
        Bukkit.getLogger().info("§4>MysqlUserName:" + Data.MysqlUserName);
        Bukkit.getLogger().info("§4>MysqlUserPassWord" + Data.MysqlUserPassWord);
        Bukkit.getLogger().info("§4>本服务器Tcp端口:" +  Data.MyServer);
        for (Integer strData:Data.Server) {
            Bukkit.getLogger().info("§4>需要转发的服务器:" + strData);
        }
        cmd.go();
        Bukkit.getLogger().info("§4>转发创建完成!");
        this.getCommand("lb").setExecutor(new cmd());
        this.getCommand("lbsys").setExecutor(new cmd());
        Bukkit.getLogger().info("检测到您使用了Mysql模式,开始为您测试连接");
        try {
            Class.forName(Data.MysqlClass);
            Bukkit.getLogger().info("§4>Mysql驱动加载完成!");
            try {
                Data.Conn = (Connection) DriverManager.getConnection(Data.MysqlUrl, Data.MysqlUserName, Data.MysqlUserPassWord);
                Bukkit.getLogger().info("§4>Mysql连接成功!");
                if (!(Data.Config.getBoolean("Mysql"))){
                    MySql.go();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getLogger().info("§4>Mysql连接失败!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("§4>Mysql驱动加载失败!");
        }

        getServer().getPluginManager().registerEvents(new RegListeners(), this);
        Bukkit.getLogger().info("§4>进服初始化完成!!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
