package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.Mysql;
import Model.AssetModel;

public class AssetDAO {
		private static Connection conn = Mysql.getConnection();

		public static List<AssetModel> getAll() throws SQLException {
				ArrayList<AssetModel> assetList = new ArrayList<AssetModel>();
				String sql = "select * from tbassets";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					AssetModel assetModel = new AssetModel((String) rs.getString("assetId"), rs.getString("assetDescription"));
					assetList.add(assetModel);
				}
				return assetList;
		}
}