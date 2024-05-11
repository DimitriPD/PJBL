package DB;
import java.sql.*;

public class Mysql {
    private static String url = "jdbc:mysql://localhost:3306/pucespacos";
    private static String user = "root";
    private static String password = "PUC@1234";

    private static Connection conn;
    public static Statement stmt;

    public static Connection getConnection() {
        try {
            if (conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            } 
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
