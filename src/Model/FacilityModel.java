package Model;
import java.util.List;

public class FacilityModel {
  private String facilityId;
  private String facilityTypeDescription;
  private boolean isActive;
  private String facilityName;
  private String capacity;
  private String note;
  private List<FacilityAssetModel> assets;

  public FacilityModel(String _facilityId, String _facilityTypeDescription, boolean _isActive, String _facilityName, String _capacity, String _note, List<FacilityAssetModel> _assets) {
        this.facilityId = _facilityId;
        this.facilityTypeDescription = _facilityTypeDescription;
        this.isActive = _isActive;
        this.facilityName = _facilityName;
        this.capacity = _capacity;
        this.note = _note;
        this.assets = _assets;
    }

  public void setFacilityId(String _facilityId) {
    this.facilityId = _facilityId;
  }

  public String getFacilityId() {
    return this.facilityId;
  }
  
  public void setAssets(List<FacilityAssetModel> _facilityAssets) {
    this.assets = _facilityAssets;
  }

  public List<FacilityAssetModel> getAssets() {
    return this.assets;
  }

  public String getFacilityTypeDescription() {
        return facilityTypeDescription;
    }

  public void setFacilityTypeDescription(String _facilityTypeDescription) {
      this.facilityTypeDescription = _facilityTypeDescription;
  }

  public boolean isActive() {
      return isActive;
  }

  public void setActive(boolean _active) {
      isActive = _active;
  }

  public String getFacilityName() {
      return facilityName;
  }

  public void setFacilityName(String _facilityName) {
      this.facilityName = _facilityName;
  }

  public String getCapacity() {
      return capacity;
  }

  public void setCapacity(String _capacity) {
      this.capacity = _capacity;
  }

  public String getNote() {
      return note;
  }

  public void setNote(String _note) {
      this.note = _note;
  }
}