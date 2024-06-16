package Files.model;

/**
 * Αυτή η κλάση αντιπροσωπεύει τα κριτήρια αναζήτησης που ορίζονται από τον χρήστη
 */
public class SearchFilterCriteria {

    private String filter1;
    private String filter2;
    private String filter3;
    private String filter4;
    private String filter5;
    private String filter6;
    private String filter7;
    private String filter8;

    public SearchFilterCriteria(String filter1, String filter2, String filter3, String filter4, String filter5,
                                String filter6, String filter7, String filter8) {
        this.filter1 = filter1;
        this.filter2 = filter2;
        this.filter3 = filter3;
        this.filter4 = filter4;
        this.filter5 = filter5;
        this.filter6 = filter6;
        this.filter7 = filter7;
        this.filter8 = filter8;
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public String getFilter3() {
        return filter3;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }

    public String getFilter4() {
        return filter4;
    }

    public void setFilter4(String filter4) {
        this.filter4 = filter4;
    }

    public String getFilter5() {
        return filter5;
    }

    public void setFilter5(String filter5) {
        this.filter5 = filter5;
    }

    public String getFilter6() {
        return filter6;
    }

    public void setFilter6(String filter6) {
        this.filter6 = filter6;
    }

    public String getFilter7() {
        return filter7;
    }

    public void setFilter7(String filter7) {
        this.filter7 = filter7;
    }

    public String getFilter8() {
        return filter8;
    }

    public void setFilter8(String filter8) {
        this.filter8 = filter8;
    }
}
