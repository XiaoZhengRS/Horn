package com.xiaozhengkeji.horn.data;

import com.mysql.jdbc.Connection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.security.auth.login.Configuration;
import java.util.List;

public class Data {
    public static FileConfiguration Config;
    public static String MysqlClass;
    public static String MysqlUrl;
    public static String MysqlUserName;
    public static String MysqlUserPassWord;
    public static Connection Conn;
    public static List<Integer> Server;
    public static int MyServer;
    public static String mes1;
    public static String mes2;
}
