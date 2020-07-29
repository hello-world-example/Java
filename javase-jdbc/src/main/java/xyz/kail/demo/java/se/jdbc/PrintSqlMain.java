package xyz.kail.demo.java.se.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrintSqlMain {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///test", "root", "1723");
        PreparedStatement preparedStatement = conn.prepareStatement("select * from T_TEST_ where ID = ?");
        preparedStatement.setLong(1, 613290L);

        // com.mysql.cj.jdbc.PreparedStatement@4157f54e: select * from T_TEST_ where ID = 613290
        System.out.println(preparedStatement.toString());

//        if (preparedStatement instanceof com.mysql.cj.jdbc.PreparedStatement) {
//            com.mysql.cj.jdbc.PreparedStatement mysqlPreparedStatement = (com.mysql.cj.jdbc.PreparedStatement) preparedStatement;
//            String sql = mysqlPreparedStatement.asSql();
//            // select * from T_TEST_ where ID = 613290
//            System.out.println(sql);
//
//            String preparedSql = mysqlPreparedStatement.getPreparedSql();
//            // select * from T_TEST_ where ID = ?
//            System.out.println(preparedSql);
//
//        }


    }

}
