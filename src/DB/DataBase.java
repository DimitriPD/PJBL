package DB;

import java.sql.*;
import java.util.ArrayList;

public abstract class DataBase {
    public abstract Connection connection();
    public abstract void disconnect();
    public abstract void execute(String sql, ArrayList<String> bindParams);
    public abstract ResultSet executeResultSet(String sql, ArrayList<String> bindParams );
}
