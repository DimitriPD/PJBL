package Model;

import java.util.*;

public class AssetModel {
    protected String assetId;
    protected String assetDescription;

    public AssetModel(String _assetId, String _assetDescription ) {
        this.setAssetId(_assetId);
        this.setAssetDescription(_assetDescription);
    }
    
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        if (assetId == null) {
            this.assetId = UUID.randomUUID().toString();
        } else {
            this.assetId = assetId;
        }
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }
}
