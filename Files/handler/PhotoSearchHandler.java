package Files.handler;

import Files.model.Photo;
import Files.model.PhotoDatabase;
import Files.model.SearchFilterCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 *Αυτή η κλάση χειρίζεται την αναζήτηση φωτογραφιών με FilterCriteria
 */
public class PhotoSearchHandler {

    /**
     * @param photoDb    εισαγωγή στιγμιότυπου photoDb
     * @param sfCriteria κριτήρια φίλτρου αναζήτησης που ορίζονται από τον χρήστη
     */
    public Map<String, Photo> searchPhotos(PhotoDatabase photoDb, SearchFilterCriteria sfCriteria) {
        if (sfCriteria != null && !isAllEmptySearchFilterCriteria(sfCriteria)) { //τα κριτήρια αναζήτησης δεν πρέπει να είναι κενά και ο χρήστης πρέπει να ορίσει τουλάχιστον 1 κριτήρια αναζήτησης
            Map<String, Photo> photoResultMap = new HashMap<>(); //Αποτέλεσμα αναζήτησης φιλτραρισμένο με φωτογραφίες

            for (Map.Entry<String, Photo> photoMapEntry : photoDb.getPhotos().entrySet()) {
                if (isSearchCriteriaMatching(sfCriteria, photoMapEntry)) { //Ελέγχει τα κριτήρια αναζήτησης που ταιριάζουν isSearchCriteriaMatching
                    photoResultMap.put(photoMapEntry.getKey(), photoMapEntry.getValue());
                }
            }
            return photoResultMap;

        } else {
            return photoDb.getPhotos();
        }
    }

    /**
     * @param sfCriteria    Κριτήρια φίλτρου αναζήτησης
     * @param photoMapEntry Κριτήρια φίλτρου αναζήτησης
     * Ελέγχει κριτήρια αναζήτησης που ταιριάζουν με meta data φωτογραφίας
     */
    private boolean isSearchCriteriaMatching(SearchFilterCriteria sfCriteria, Map.Entry<String, Photo> photoMapEntry) {
        return (!sfCriteria.getFilter1().isEmpty() && sfCriteria.getFilter1().equalsIgnoreCase(
                photoMapEntry.getValue().getMetaData1())) ||
                (!sfCriteria.getFilter2().isEmpty() && sfCriteria.getFilter2().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData2())) ||
                (!sfCriteria.getFilter3().isEmpty() && sfCriteria.getFilter3().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData3())) ||
                (!sfCriteria.getFilter4().isEmpty() && sfCriteria.getFilter4().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData4())) ||
                (!sfCriteria.getFilter5().isEmpty() && sfCriteria.getFilter5().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData5())) ||
                (!sfCriteria.getFilter6().isEmpty() && sfCriteria.getFilter6().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData6())) ||
                (!sfCriteria.getFilter7().isEmpty() && sfCriteria.getFilter7().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData7())) ||
                (!sfCriteria.getFilter8().isEmpty() && sfCriteria.getFilter8().equalsIgnoreCase(
                        photoMapEntry.getValue().getMetaData8()));
    }

    //Ελέγχει εάν όλα τα κριτήρια αναζήτησης που παρέχονται είναι κενά
    private boolean isAllEmptySearchFilterCriteria(SearchFilterCriteria sfCriteria) {
        return sfCriteria.getFilter1().isEmpty() && sfCriteria.getFilter2().isEmpty() && sfCriteria.getFilter3().isEmpty() &&
                sfCriteria.getFilter4().isEmpty() && sfCriteria.getFilter5().isEmpty() && sfCriteria.getFilter6().isEmpty() &&
                sfCriteria.getFilter7().isEmpty() && sfCriteria.getFilter8().isEmpty();
    }
}
