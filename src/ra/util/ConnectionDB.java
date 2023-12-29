package ra.util;

import java.sql.*;

public class ConnectionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/project_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection openConnection() {
        Connection conn = null;
        try {
            //1. set Driver cho DriverManager
            Class.forName(DRIVER);
            //2. Khoi tao doi tuong Connection tu Driver Manager
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = openConnection();
        System.out.println("Is Duplicate "+CommonFunction.checkDuplicateProductId(conn,"P0000"));


    }
}
