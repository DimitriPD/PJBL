import java.sql.SQLException;

import Controller.AssetController;
import DAO.AssetDAO;
import DB.Mysql;

public class App {
    public static void main(String[] args) throws SQLException  {
        Screen screen = new Screen("janela");
        Mysql mysql = new Mysql();
        AssetController.getAll();
    }
}
