package DAO;

import DB.Mysql;
import java.sql.*;
import java.util.*;
import Model.AssetModel;

public class AssetDAO {
	private static Mysql MySqlDB = new Mysql();

	public static void create(AssetModel assetModel) {
		String sql = "insert into tbassets values (?, ?);";
		ArrayList<String> bindParams = new ArrayList<>();
		bindParams.add(assetModel.getAssetId());
		bindParams.add(assetModel.getAssetDescription());

		MySqlDB.connection();
		MySqlDB.execute(sql, bindParams);
		MySqlDB.disconnect();
	}

	public static List<AssetModel> getAll() throws SQLException {
		String sql = "select * from tbassets;";
		ArrayList<AssetModel> assetList = new ArrayList<AssetModel>();
		
		MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, null);
		
		while (rs.next()) {
			String assetDescription = rs.getString("assetDescription");
			String assetId = rs.getString("assetId");
			AssetModel assetModel = new AssetModel(assetId, assetDescription);
			assetList.add(assetModel);
		}

		MySqlDB.disconnect();
		return assetList;
	}

	public static AssetModel getById(String id) throws SQLException {
		String sql = "select * from tbassets where assetid = ?;";
		ArrayList<String> bindParams = new ArrayList<>();
		bindParams.add(id);
		AssetModel assetModel = null;
		
		MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, bindParams);
		
		while (rs.next()) {
			assetModel = new AssetModel(rs.getString("assetId"), rs.getString("assetDescription"));
		}
		
		MySqlDB.disconnect();
		return assetModel;
	}

	public static void update(AssetModel assetModel) {
		String sql = "update tbassets set assetDescription = ? where assetid = ?;";
		ArrayList<String> bindParams = new ArrayList<>();
		bindParams.add(assetModel.getAssetDescription());
		bindParams.add(assetModel.getAssetId());

		MySqlDB.connection();
		MySqlDB.execute(sql, bindParams);
		MySqlDB.disconnect();
	}
}