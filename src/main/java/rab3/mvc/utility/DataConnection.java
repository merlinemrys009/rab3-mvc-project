package rab3.mvc.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnection {

    private static String DRIVER_NAME="com.mysql.jdbc.Driver";
    private static String DB_USER_NAME="root";
    private static String DB_PASSWORD="Mansehr@1981";
    private static String DB_URL="jdbc:mysql://localhost:3306/rab3";

    public static Connection getConnection() throws Exception {
        //Loading Driver
        Class.forName(DRIVER_NAME);
        //Creating connection
        Connection connection= DriverManager.getConnection(DB_URL,DB_USER_NAME,DB_PASSWORD);
        return connection;
    }
}
