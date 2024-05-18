package Model;
import java.util.List;
import java.util.UUID;

public class FacilityModel {
  private String facilityId;
  private String facilityTypeId;
  private String facilityTypeDescription;
  private boolean isActive;
  private String facilityName;
  private Integer capacity;
  private String note;
  private List<FacilityAssetModel> assets;

  public FacilityModel(String _facilityId, String _facilityTypeId, boolean _isActive, String _facilityName, Integer _capacity, String _note) {
        setFacilityId(_facilityId);
        setFacilityTypeId(_facilityTypeId);
        setActive(_isActive);
        setFacilityName(_facilityName);
        setCapacity(_capacity);
        setNote(_note);
    }

  public void setFacilityId(String _facilityId) {
    if (_facilityId == null) {
          this.facilityId = UUID.randomUUID().toString();
        } else {
          this.facilityId = _facilityId;
        }
  }

  public String getFacilityId() {
    return this.facilityId;
  }

  public String getFacilityTypeId() {
    return facilityTypeId;
  }

  public void setFacilityTypeId(String facilityTypeId) {
    this.facilityTypeId = facilityTypeId;
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

  public Integer getCapacity() {
      return capacity;
  }

  public void setCapacity(Integer _capacity) {
      this.capacity = _capacity;
  }

  public String getNote() {
      return note;
  }

  public void setNote(String _note) {
      this.note = _note;
  }
}