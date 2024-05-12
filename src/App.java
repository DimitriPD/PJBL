import java.sql.SQLException;
import java.util.*;

import Controller.AssetController;
import Controller.FacilityController;
import Model.*;

public class App {
    public static void main(String[] args) throws SQLException  {
        // AssetModel assetModel = new AssetModel("c499ca7a-4fe1-4fc5-8399-26f31357f3b8", "Computador");
        // List<FacilityModel> facilityList =  FacilityDAO.getAll(null);
        // System.out.println(FacilityDAO.getById("d8ccc4e3-7636-4d35-a7ac-b18231ed8ee5").getAssets());
        // List<FacilityModel> lista = FacilityController.getAll(null);
        // for (FacilityModel f : lista) {
        //     System.out.println(f.getFacilityId());
        //     f.getAssets().forEach(a -> System.out.println(a.getAssetId()));;
        // }
        // System.out.println(FacilityController.getById("250677f9-3122-44bf-bde9-1b2f9f81b35c", true).getAssets());
        // FacilityController.create(new FacilityModel(null, "ceb542b3-26de-4c9c-bfd3-a57f06f6fc14", true, "Kant", null, null, null));
        FacilityController.update(new FacilityModel("250677f9-3122-44bf-bde9-1b2f9f81b35c", "ceb542b3-26de-4c9c-bfd3-a57f06f6fc14", true, "Kant", null, null, new ArrayList<FacilityAssetModel>()));
        // System.out.println(FacilityController.getById("250677f9-3122-44bf-bde9-1b2f9f81b35c", false).getFacilityId());
        // System.out.println(AssetController.getById("9f03a1a6-992c-46a1-8c8e-eafee56dab62").getAssetId());
    }
}
