package Files.model;

/**
 * Αντιπροσωπεύει τα general meta-data σε XML
 */
public class GeneralMetaData {

    private String filterCategory1;
    private String filterCategory2;
    private String filterCategory3;
    private String filterCategory4;
    private String filterCategory5;
    private String filterCategory6;
    private String filterCategory7;
    private String filterCategory8;

    public GeneralMetaData(String filterCategory1, String filterCategory2, String filterCategory3,
                           String filterCategory4, String filterCategory5, String filterCategory6,
                           String filterCategory7, String filterCategory8) {
        this.filterCategory1 = filterCategory1;
        this.filterCategory2 = filterCategory2;
        this.filterCategory3 = filterCategory3;
        this.filterCategory4 = filterCategory4;
        this.filterCategory5 = filterCategory5;
        this.filterCategory6 = filterCategory6;
        this.filterCategory7 = filterCategory7;
        this.filterCategory8 = filterCategory8;
    }

    public String getFilterCategory1() {
        return filterCategory1;
    }

    public void setFilterCategory1(String filterCategory1) {
        this.filterCategory1 = filterCategory1;
    }

    public String getFilterCategory2() {
        return filterCategory2;
    }

    public void setFilterCategory2(String filterCategory2) {
        this.filterCategory2 = filterCategory2;
    }

    public String getFilterCategory3() {
        return filterCategory3;
    }

    public void setFilterCategory3(String filterCategory3) {
        this.filterCategory3 = filterCategory3;
    }

    public String getFilterCategory4() {
        return filterCategory4;
    }

    public void setFilterCategory4(String filterCategory4) {
        this.filterCategory4 = filterCategory4;
    }

    public String getFilterCategory5() {
        return filterCategory5;
    }

    public void setFilterCategory5(String filterCategory5) {
        this.filterCategory5 = filterCategory5;
    }

    public String getFilterCategory6() {
        return filterCategory6;
    }

    public void setFilterCategory6(String filterCategory6) {
        this.filterCategory6 = filterCategory6;
    }

    public String getFilterCategory7() {
        return filterCategory7;
    }

    public void setFilterCategory7(String filterCategory7) {
        this.filterCategory7 = filterCategory7;
    }

    public String getFilterCategory8() {
        return filterCategory8;
    }

    public void setFilterCategory8(String filterCategory8) {
        this.filterCategory8 = filterCategory8;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("GeneralMetaData{")
                .append("filterCategory1='").append(filterCategory1).append('\'')
                .append(", filterCategory2='").append(filterCategory2).append('\'')
                .append(", filterCategory3='").append(filterCategory3).append('\'')
                .append(", filterCategory4='").append(filterCategory4).append('\'')
                .append(", filterCategory5='").append(filterCategory5).append('\'')
                .append(", filterCategory6='").append(filterCategory6).append('\'')
                .append(", filterCategory7='").append(filterCategory7).append('\'')
                .append(", filterCategory8='").append(filterCategory8).append('\'')
                .append('}').toString();
    }
}