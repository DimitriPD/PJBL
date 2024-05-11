package DB;

import java.sql.Connection;

public abstract class DataBase {
    abstract Connection getConnection();
}
