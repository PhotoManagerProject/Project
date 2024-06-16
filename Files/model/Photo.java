package Files.model;

/**
 Κλάση μοντέλου για τη διατήρηση των λεπτομερειών φωτογραφιών
 */
public class Photo {

    private String  id;
    private String imagePath;

    private String metaData1;
    private String metaData2;
    private String metaData3;
    private String metaData4;
    private String metaData5;
    private String metaData6;
    private String metaData7;
    private String metaData8;

    public Photo() {
    }

    public Photo(String id, String imagePath, String metaData1, String metaData2, String metaData3, String metaData4,
                 String metaData5, String metaData6, String metaData7, String metaData8) {
        this.id = id;
        this.imagePath = imagePath;
        this.metaData1 = metaData1;
        this.metaData2 = metaData2;
        this.metaData3 = metaData3;
        this.metaData4 = metaData4;
        this.metaData5 = metaData5;
        this.metaData6 = metaData6;
        this.metaData7 = metaData7;
        this.metaData8 = metaData8;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMetaData1() {
        return metaData1;
    }

    public void setMetaData1(String metaData1) {
        this.metaData1 = metaData1;
    }

    public String getMetaData2() {
        return metaData2;
    }

    public void setMetaData2(String metaData2) {
        this.metaData2 = metaData2;
    }

    public String getMetaData3() {
        return metaData3;
    }

    public void setMetaData3(String metaData3) {
        this.metaData3 = metaData3;
    }

    public String getMetaData4() {
        return metaData4;
    }

    public void setMetaData4(String metaData4) {
        this.metaData4 = metaData4;
    }

    public String getMetaData5() {
        return metaData5;
    }

    public void setMetaData5(String metaData5) {
        this.metaData5 = metaData5;
    }

    public String getMetaData6() {
        return metaData6;
    }

    public void setMetaData6(String metaData6) {
        this.metaData6 = metaData6;
    }

    public String getMetaData7() {
        return metaData7;
    }

    public void setMetaData7(String metaData7) {
        this.metaData7 = metaData7;
    }

    public String getMetaData8() {
        return metaData8;
    }

    public void setMetaData8(String metaData8) {
        this.metaData8 = metaData8;
    }
}
