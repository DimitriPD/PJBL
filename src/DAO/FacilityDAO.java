package DAO;

import DB.Mysql;
import java.sql.*;
import java.util.*;
import Model.FacilityAssetModel;
import Model.FacilityModel;
import Model.FacilityTypeModel;

public class FacilityDAO {
    private static Mysql MySqlDB = new Mysql();

    // READ
    public static List<FacilityModel> getAll() throws SQLException {
        String sql = "SELECT f.*, t.facilityTypeDescription " + //
                     "FROM tbFacilities f " + //
                     "INNER JOIN tbFacilityTypes t ON t.facilityTypeId = f.facilityTypeId;";

        ArrayList<Object> bindParams = null;

		ArrayList<FacilityModel> facilityList = new ArrayList<FacilityModel>();
		
		MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, bindParams);

        while (rs.next()) {
            String facilityId = rs.getString("facilityId");
            String facilityTypeIdFromDB = rs.getString("facilityTypeId");
            String facilityTypeDescription  = rs.getString("facilityTypeDescription");
            boolean isActive  = rs.getBoolean("isActive");
            String facilityName  = rs.getString("facilityName");
            Integer capacity  = rs.getInt("capacity");
            String note  = rs.getString("note");
            FacilityModel facility = new FacilityModel(facilityId, facilityTypeIdFromDB, isActive, facilityName, capacity, note);
            facility.setFacilityTypeDescription(facilityTypeDescription);
			facilityList.add(facility);
		}

		MySqlDB.disconnect();
		return facilityList;
    }

    public static FacilityModel getById(String id) throws SQLException {
        String sql = "SELECT f.*, t.facilityTypeDescription " + //
                     "FROM tbFacilities f " + //
                     "INNER JOIN tbFacilityTypes t ON t.facilityTypeId = f.facilityTypeId " +
                     "WHERE facilityId = ?";
		ArrayList<Object> bindParams = new ArrayList<>();
		bindParams.add(id);
		FacilityModel facility= null;
		
		MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, bindParams);
		
		while (rs.next()) {
            String facilityId = rs.getString("facilityId");
            String facilityTypeId = rs.getString("facilityTypeId");
            String facilityTypeDescription  = rs.getString("facilityTypeDescription");
            boolean isActive  = rs.getBoolean("isActive");
            String facilityName  = rs.getString("facilityName");
            Integer capacity  = rs.getInt("capacity");
            String note  = rs.getString("note");
            facility = new FacilityModel(facilityId, facilityTypeId, isActive, facilityName, capacity, note);
            facility.setFacilityTypeDescription(facilityTypeDescription);
		}
		
		MySqlDB.disconnect();
		return facility;
    }

    public static List<FacilityAssetModel> getAllFacilityAssets(String facilityId) throws SQLException {
        String sql = "SELECT fa.*, a.assetDescription " +
                     "FROM tbFacilityAssets fa " +
                     "INNER JOIN tbassets a ON a.assetId = fa.assetId " +
                     "WHERE fa.facilityId = ?;";
        ArrayList<Object> bindParams = new ArrayList<>();
        bindParams.add(facilityId);
        ArrayList<FacilityAssetModel> facilityAssetList = new ArrayList<FacilityAssetModel>();

        MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, bindParams);

        while (rs.next()) {
            String facilityIdFromDB = rs.getString("facilityId");
            String assetId  = rs.getString("assetId");
            int quantity  = rs.getInt("quantity");
            String assetDescription  = rs.getString("assetDescription");
            facilityAssetList.add(new FacilityAssetModel(facilityIdFromDB, assetId, quantity, assetDescription));
        }

        return facilityAssetList;
    }

    // CREATE
    public static void create(FacilityModel facility) {
		String sql = "insert into tbfacilities (" + //
                        "facilityId, " + //
                        "facilityTypeId, " + //
                        "isActive, " + //
                        "facilityName, " + //
                        "capacity, " + //
                        "note) " + //
                     "values (?, ?, ?, ?, ?, ?);";
		ArrayList<Object> bindParams = new ArrayList<>();
		bindParams.add(facility.getFacilityId());
		bindParams.add(facility.getFacilityTypeId());
		bindParams.add(facility.isActive() ? "1" : "0");
		bindParams.add(facility.getFacilityName());
		bindParams.add(facility.getCapacity());
		bindParams.add(facility.getNote());

		MySqlDB.connection();
		MySqlDB.execute(sql, bindParams);
		MySqlDB.disconnect();
    }

    public static void createFacilityAsset(FacilityAssetModel asset) {
        String sql = "INSERT INTO tbFacilityAssets ( " + //
                        "facilityId, " + //
                        "assetId,  " + //
                        "quantity)  " + //
                     " VALUES (?, ?, ?);";
        ArrayList<Object> bindParams = new ArrayList<>();
		bindParams.add(asset.getFacilityId());
		bindParams.add(asset.getAssetId());
		bindParams.add(asset.getQuantity());

        MySqlDB.connection();
		MySqlDB.execute(sql, bindParams);
		MySqlDB.disconnect();
    }

    // UPDATE
    public static void update(FacilityModel facility) {
        String sql = "UPDATE tbFacilities SET " +
                      "facilityTypeId = ?, " +
                      "isActive = ?, " +
                      "facilityName = ?, " +
                      "capacity = ?, " +
                      "note = ? " +
                     "WHERE facilityId = ?;";
    
        ArrayList<Object> bindParams = new ArrayList<>();
        bindParams.add(facility.getFacilityTypeId());
        bindParams.add(facility.isActive());
        bindParams.add(facility.getFacilityName());
        bindParams.add(facility.getCapacity());
        bindParams.add(facility.getNote());
        bindParams.add(facility.getFacilityId());
    
        MySqlDB.connection();
		MySqlDB.execute(sql, bindParams);
		MySqlDB.disconnect();
      }

    public static void updateFacilityAsset(FacilityAssetModel facilityAsset) {
        String sql = "UPDATE tbFacilityAssets SET " +
                     "quantity = ? " +
                     "WHERE facilityId = ? AND assetId = ?";

        ArrayList<Object> bindParams = new ArrayList<>();
        bindParams.add(facilityAsset.getQuantity());
        bindParams.add(facilityAsset.getFacilityId());
        bindParams.add(facilityAsset.getAssetId());
    
        MySqlDB.connection();
        MySqlDB.execute(sql, bindParams);
        MySqlDB.disconnect();
    }

    // DELETE
    public static void deleteFacilityAsset(String facilityId, String assetId) {
        String sql = "DELETE FROM tbFacilityAssets " +
                     "WHERE facilityId = ? AND assetId = ?";

        ArrayList<Object> bindParams = new ArrayList<>();
        bindParams.add(facilityId);
        bindParams.add(assetId);
    
        MySqlDB.connection();
        MySqlDB.execute(sql, bindParams);
        MySqlDB.disconnect();
    }

    public static List<FacilityTypeModel> getAllTypes() throws SQLException {
        String sql = "SELECT * FROM tbFacilityTypes order by facilityTypeDescription;";

        ArrayList<Object> bindParams = null;

		ArrayList<FacilityTypeModel> facilityTypesList = new ArrayList<FacilityTypeModel>();
		
		MySqlDB.connection();
		ResultSet rs = MySqlDB.executeResultSet(sql, bindParams);

        while (rs.next()) {
            String facilityTypeId = rs.getString("facilityTypeId");
            String facilityTypeDescription  = rs.getString("facilityTypeDescription");
            FacilityTypeModel type = new FacilityTypeModel(facilityTypeId, facilityTypeDescription);
			facilityTypesList.add(type);
		}

		MySqlDB.disconnect();
		return facilityTypesList;
    }
}