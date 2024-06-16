package Files.model;

import java.util.Map;

/**
 Για να παρακολουθείται η βάση δεδομένων φωτογραφιών σχετικά με το προφίλ
 */
public class PhotoDatabase {
    private GeneralMetaData generalMetaData;
    private Map<String, Photo> photos;

    public PhotoDatabase(GeneralMetaData generalMetaData, Map<String, Photo> photos) {
        this.generalMetaData = generalMetaData;
        this.photos = photos;
    }

    public PhotoDatabase() {
    }
    public void setGeneralMetaData(GeneralMetaData generalMetaData) {
        this.generalMetaData = generalMetaData;
    }

    public void setPhotos(Map<String, Photo> photos) {
        this.photos = photos;
    }

    public GeneralMetaData getGeneralMetaData() {
        return generalMetaData;
    }

    public Map<String, Photo> getPhotos() {
        return photos;
    }
}
