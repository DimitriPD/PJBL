package DB;
import java.sql.*;
import java.util.ArrayList;

public class Mysql extends DataBase {
    private static String url = "jdbc:mysql://localhost:3306/pucespacos";
    private static String user = "root";
    private static String password = "PUC@1234";
    private Connection connection;

    public Mysql() {
        connection = this.connection();
    }

    @Override
    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to MySQL database");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from MySQL database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute(String sql, ArrayList<String> bindParams) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            System.out.println(ps);
            if (bindParams != null) {
                for (int i = 0; i < bindParams.size(); i++) {
                    ps.setString(i + 1, bindParams.get(i));
                }
            }

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeResultSet(String sql, ArrayList<String> bindParams) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (bindParams != null) {
                for (int i = 0; i < bindParams.size(); i++) {
                    ps.setString(i + 1, bindParams.get(i));
                }
            }

            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
