package xyz.kail.demo.java.se.jdbc;

import com.alibaba.druid.sql.SQLUtils;

import java.util.Arrays;
import java.util.Date;

public class DruidSqlUtilMain {

    public static void main(String[] args) {
String sql = SQLUtils.format(
        "select * from asd where id = ? and birthday = ?",
        com.alibaba.druid.util.JdbcConstants.MYSQL,
        Arrays.asList(1L, new Date())
);

/*
 * SELECT *
 * FROM asd
 * WHERE id = 1
 * 	AND birthday = '2019-05-02'
 */
System.out.println(sql);
    }

}
