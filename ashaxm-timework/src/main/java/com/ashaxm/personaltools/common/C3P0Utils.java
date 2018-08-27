package com.ashaxm.personaltools.common;

import java.sql.Connection;

import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
    // 定义全局变量
    private static ComboPooledDataSource cpds;
    // 静态代码块
    static {
        cpds = new ComboPooledDataSource();
    }
 
    // 获得数据源
    public static DataSource getDataSource() {
        return cpds;
    }
 
    // 获得连接
    public static Connection getConnection() throws Exception {
        return cpds.getConnection();
    }
}