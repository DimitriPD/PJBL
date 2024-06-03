package DAO;

import DB.DataBase;
import DB.Mysql;
import Exception.AssetDataAccessException;

import java.sql.*;
import java.util.*;
import Model.AssetModel;

public class AssetDAO {
    private static DataBase MySqlDB = new Mysql();

    public static List<AssetModel> getAll() throws AssetDataAccessException {
        String sql = "select * from tbassets;";
        ArrayList<AssetModel> assetList = new ArrayList<AssetModel>();
        
        try {
            MySqlDB.connection();
            ResultSet rs = MySqlDB.executeResultSet(sql, null);

            while (rs.next()) {
                String assetDescription = rs.getString("assetDescription");
                String assetId = rs.getString("assetId");
                AssetModel assetModel = new AssetModel(assetId, assetDescription);
                assetList.add(assetModel);
            }
        } 
				catch (SQLException e) {
					throw new AssetDataAccessException("Erro ao acessar os dados dos ativos", e);
        } 
				finally {
          MySqlDB.disconnect();
        }

        return assetList;
    }
}
