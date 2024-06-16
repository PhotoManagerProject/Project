package Files.handler;

import Files.model.GeneralMetaData;
import Files.model.Photo;
import Files.model.PhotoDatabase;
import Files.constants.Constants;
import Files.constants.ErrorCode;
import Files.exception.PhotoManagerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *Αυτή η κλάση είναι υπεύθυνη για όλες τις λειτουργίες XML DB CRUD(CREATE,READ,UPDATE AND DELETE) που απαιτούνται
 */
public class PhotoXmlHandler {

    private Document document;
    private String filePath;
    private Map<String, Photo> photoMap = new HashMap<>();

    public PhotoXmlHandler(final String filePath) {
        this.filePath = filePath;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = dbFactory.newDocumentBuilder();
            document = docBuild.parse(new File(filePath));
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            throw new PhotoManagerException(ErrorCode.XML_PARSE_ERROR, "XML Parsing error occurred" + e.getMessage());
        } catch (IOException e) {
            throw new PhotoManagerException(ErrorCode.PHOTO_MANAGER_ERROR, "Read/write error occurred" + e.getMessage());
        } catch (Exception e) {
            throw new PhotoManagerException(ErrorCode.PHOTO_MANAGER_ERROR, "Unknown error occurred" + e.getMessage());
        }
    }

    //Αυτή η μέθοδος είναι για ανάγνωση όλων των φωτογραφιών από το XML PhotoDB
    public void readPhotosXmlToMap(final PhotoDatabase photoDb) {
        try {
            NodeList generalMetaDataList = document.getElementsByTagName(Constants.GENERAL_META_DATA);
            NodeList photoList = document.getElementsByTagName(Constants.PHOTO);

            readGeneralMetaDataList(photoDb, generalMetaDataList);
            readPhotos(photoList);

            photoDb.setPhotos(photoMap);
        } catch (Exception ex) {
            throw new PhotoManagerException(ErrorCode.PHOTO_READ_ERROR, "Error occurred while reading photos" + ex.getMessage());
        }
    }

    /**
     @return διαβάζει τις λεπτομέρειες της φωτογραφίας
     Αυτή η μέθοδος είναι για την ανάγνωση φωτογραφιών με δεδομένο πρωτότυπο αναγνωριστικό
     */
    public Photo readPhotoById(final String photoId, final PhotoDatabase photoDb) {
        return photoDb.getPhotos().get(photoId);
    }

    /**
     * @param photo   photoId για εισαγωγή στο xml photoDb
     * Χρησιμοποιείται για την εισαγωγή νέας φωτογραφίας στο συγκεκριμένο db του προφίλ
     */
    public void insertPhotoInDb(final PhotoDatabase photoDb, final Photo photo) {
        if (photo != null && !photo.getId().isEmpty()) {
            photoDb.getPhotos().put(photo.getId(), photo);
            savePhotoToXML(photo);
        } else {
            throw new PhotoManagerException(ErrorCode.PHOTO_INSERT_ERROR,
                    "For Insert photo, photo or photo id can not be null");
        }
    }

    /**
     * @param photoDb photoDB παράδειγμα συγκεκριμένου προφίλ
     * @param photo  photoId για ενημέρωση σε xml photoDb
     *
     *      Χρήση για ενημέρωση μιας φωτογραφίας σε db
     */
    public void updatePhotoInDb(final PhotoDatabase photoDb, final Photo photo) {
        if (photoDb.getPhotos().containsKey(photo.getId())) {
            System.out.println("Updating Photo id with modified data: " + photo.getId());
            photoDb.getPhotos().put(photo.getId(), photo);
            modifyXmlPhotoDetails(photo);
        }
    }

    /**
     * @param photoDb   photoDB παράδειγμα συγκεκριμένου προφίλ
     * @param deleteIds photoId λίστα που πρέπει να διαγραφεί τόσο από το χάρτη στη μνήμη όσο και από το xml photoDb
     *  Χρησιμοποιείται το για να διαγράψετε πολλές φωτογραφίες για τα δεδομένα id στο Photo DB
     */
    public void deletePhotosInDb(final PhotoDatabase photoDb, final List<String> deleteIds) {
        deleteIds.forEach(id -> {
                    deletePhotoFromMap(photoDb, id); //First Delete from HashMap
                    deletePhoto(id, document.getElementsByTagName(Constants.PHOTO)); //Secondly Delete given XML photo nodes
                    System.out.println("Photo has been deleted, id: " + id);
                    applyXmlChanges(); //Apply the deleted photo changes for XML DB
                }
        );
    }

    /**
     * @param photoDb               PhotoDb που σχετίζεται με το προφίλ
     * @param updateGeneralMetaData λεπτομέρειες  Metadata προς ενημέρωση
     *  Αυτή η μέθοδος ενημερώνει τα General MetaData τόσο στα δεδομένα χάρτη όσο και στο αρχείο XML
     */
    public void updateGeneralMetaDataInDb(final PhotoDatabase photoDb, final GeneralMetaData updateGeneralMetaData) {
        if (updateGeneralMetaData != null) {
            GeneralMetaData gmData = photoDb.getGeneralMetaData();
            gmData.setFilterCategory1(updateGeneralMetaData.getFilterCategory1());
            gmData.setFilterCategory2(updateGeneralMetaData.getFilterCategory2());
            gmData.setFilterCategory3(updateGeneralMetaData.getFilterCategory3());
            gmData.setFilterCategory4(updateGeneralMetaData.getFilterCategory4());
            gmData.setFilterCategory5(updateGeneralMetaData.getFilterCategory5());
            gmData.setFilterCategory6(updateGeneralMetaData.getFilterCategory6());
            gmData.setFilterCategory7(updateGeneralMetaData.getFilterCategory7());
            gmData.setFilterCategory8(updateGeneralMetaData.getFilterCategory8());

            modifyXmlGenMetaData(gmData);
        }
    }

    /**
     * @param photoDb Στιγμιότυπο photoDB ειδικά για το προφίλ
     * @param deleteId δώστε το αναγνωριστικό που θα διαγραφεί
     *Χρησιμοποιείται το για να διαγράψετε μια φωτογραφία για το id
     */
    private void deletePhotoFromMap(final PhotoDatabase photoDb, final String deleteId) {
        if (photoDb.getPhotos().containsKey(deleteId)) {
            photoDb.getPhotos().remove(deleteId);
        } else {
            throw new PhotoManagerException(ErrorCode.PHOTO_DELETE_ERROR, "No Photo id found to delete");
        }
    }

    /**
     *
     * @param id περάστε το αναγνωριστικό της φωτογραφίας για διαγραφή
     * @param photoList photo list nodes
     */
    private static void deletePhoto(String id, NodeList photoList) {
        for (int index = 0; index < photoList.getLength(); index++) {
            Element photoElement = (Element) photoList.item(index);
            if (photoElement.getElementsByTagName(Constants.ID).item(0).getTextContent().equals(id)) {
                photoElement.getParentNode().removeChild(photoElement);
                index--;
            }
        }
    }

    /**
     * @param photoList δημιουργεί Constants.Objects και αποθηκεύεται στο photoMap
     */
    private void readPhotos(NodeList photoList) {
        IntStream.range(0, photoList.getLength())//Iterate from 0 to photoList length
                .mapToObj(i -> (Element) photoList.item(i)) //map to XML Element photo object
                .map(photoElement -> new Photo(  //map to Photo model object
                        photoElement.getElementsByTagName(Constants.ID).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.PATH).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_1).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_2).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_3).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_4).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_5).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_6).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_7).item(0).getTextContent(),
                        photoElement.getElementsByTagName(Constants.META_DATA_8).item(0).getTextContent()
                )).forEach(photo -> photoMap.put(photo.getId(), photo)); //Insert photos in to in-memory map
    }

    /**
     * @param photoDb              προφίλ db
     * @param generalMetaDataList general meta-data προς ανάγνωση
     *  Επαναλαμβάνετε το general meta-data, διαβάζει κάθε πεδίο meta-data και αποθηκεύει στο java metaData
     */
    private static void readGeneralMetaDataList(PhotoDatabase photoDb, NodeList generalMetaDataList) {
        IntStream.range(0, generalMetaDataList.getLength()) //Iterate from 0 to genralMetaDataList length
                .mapToObj(i -> (Element) generalMetaDataList.item(i))   //map to XML Element generalMetaData object
                .map(generalMetaData -> new GeneralMetaData(    //map to general meta data model object
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_1).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_2).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_3).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_4).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_5).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_6).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_7).item(0).getTextContent(),
                        generalMetaData.getElementsByTagName(Constants.FILTER_CATEGORY_8).item(0).getTextContent()
                )).forEach(photoDb::setGeneralMetaData);//Insert General Meta data to photoDb
    }

    /**
     * @param photo περνάει φωτογραφία για αποθήκευση σε xml
     *              Αυτή η μέθοδος αποθηκεύει τις περασμένες τροποποιημένες λεπτομέρειες φωτογραφίας
     */
    private void modifyXmlPhotoDetails(Photo photo) {
        NodeList photoXmlNodes = document.getElementsByTagName(Constants.PHOTO);

        int index = 0;
        while (index < photoXmlNodes.getLength()) {
            Element photoElement = (Element) photoXmlNodes.item(index);
            if (!photo.getId().isEmpty() && photoElement.getElementsByTagName(Constants.ID).item(0).
                    getTextContent().equals(photo.getId())) {
                photoElement.getElementsByTagName(Constants.PATH).item(0)
                        .setTextContent(photo.getImagePath());
                photoElement.getElementsByTagName(Constants.META_DATA_1).item(0)
                        .setTextContent(photo.getMetaData1());
                photoElement.getElementsByTagName(Constants.META_DATA_2).item(0)
                        .setTextContent(photo.getMetaData2());
                photoElement.getElementsByTagName(Constants.META_DATA_3).item(0)
                        .setTextContent(photo.getMetaData3());
                photoElement.getElementsByTagName(Constants.META_DATA_4).item(0)
                        .setTextContent(photo.getMetaData4());
                photoElement.getElementsByTagName(Constants.META_DATA_5).item(0)
                        .setTextContent(photo.getMetaData5());
                photoElement.getElementsByTagName(Constants.META_DATA_6).item(0)
                        .setTextContent(photo.getMetaData6());
                photoElement.getElementsByTagName(Constants.META_DATA_7).item(0)
                        .setTextContent(photo.getMetaData7());
                photoElement.getElementsByTagName(Constants.META_DATA_8).item(0)
                        .setTextContent(photo.getMetaData8());
                applyXmlChanges();
                return;
            }
            index++;
        }
        throw new PhotoManagerException(ErrorCode.PHOTO_UPDATE_ERROR, "No matching Photo found to update");
    }

    /***
     *
     * @param photo φωτογραφία που θα εισαχθεί
     *  Αυτή η μέθοδος χρησιμοποιείται για την εισαγωγή νέων λεπτομερειών φωτογραφίας
     */
    private void savePhotoToXML(final Photo photo) {
        Element insertPhoto = document.createElement(Constants.PHOTO);
        Element photoId = document.createElement(Constants.ID);
        photoId.appendChild(document.createTextNode(photo.getId()));
        insertPhoto.appendChild(photoId);
        Element photoPath = document.createElement(Constants.PATH);
        photoPath.appendChild(document.createTextNode(photo.getImagePath()));
        insertPhoto.appendChild(photoPath);
        Element newMetaData1 = document.createElement(Constants.META_DATA_1);
        newMetaData1.appendChild(document.createTextNode(photo.getMetaData1()));
        insertPhoto.appendChild(newMetaData1);
        Element newMetaData2 = document.createElement(Constants.META_DATA_2);
        newMetaData2.appendChild(document.createTextNode(photo.getMetaData2()));
        insertPhoto.appendChild(newMetaData2);
        Element newMetaData3 = document.createElement(Constants.META_DATA_3);
        newMetaData3.appendChild(document.createTextNode(photo.getMetaData3()));
        insertPhoto.appendChild(newMetaData3);
        Element newMetaData4 = document.createElement(Constants.META_DATA_4);
        newMetaData4.appendChild(document.createTextNode(photo.getMetaData4()));
        insertPhoto.appendChild(newMetaData4);
        Element newMetaData5 = document.createElement(Constants.META_DATA_5);
        newMetaData5.appendChild(document.createTextNode(photo.getMetaData5()));
        insertPhoto.appendChild(newMetaData5);
        Element newMetaData6 = document.createElement(Constants.META_DATA_6);
        newMetaData6.appendChild(document.createTextNode(photo.getMetaData6()));
        insertPhoto.appendChild(newMetaData6);
        Element newMetaData7 = document.createElement(Constants.META_DATA_7);
        newMetaData7.appendChild(document.createTextNode(photo.getMetaData7()));
        insertPhoto.appendChild(newMetaData7);
        Element newMetaData8 = document.createElement(Constants.META_DATA_8);
        newMetaData8.appendChild(document.createTextNode(photo.getMetaData8()));
        insertPhoto.appendChild(newMetaData8);
        document.getDocumentElement().getElementsByTagName(Constants.PHOTOS).item(0).
                appendChild(insertPhoto);
        applyXmlChanges(); //save στο xml το τελικό αποτέλεσμα
    }

    /**
     * @param gmData General MetaData λεπτομέρειες προς ενημέρωση
     *             Αυτή η μέθοδος τροποποιεί τις υπάρχουσες γενικές κατηγορίες φίλτρων
     */
    private void modifyXmlGenMetaData(GeneralMetaData gmData) {
        if (gmData == null) {
            throw new PhotoManagerException(ErrorCode.GENERAL_METADATA_UPDATE_ERROR, "Empty MetaData found to update");
        }

        NodeList gmDataXmlNodes = document.getElementsByTagName(Constants.GENERAL_META_DATA);
        Element gmDataElement = (Element) gmDataXmlNodes.item(0);

        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_1).item(0)
                .setTextContent(gmData.getFilterCategory1());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_2).item(0)
                .setTextContent(gmData.getFilterCategory2());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_3).item(0)
                .setTextContent(gmData.getFilterCategory3());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_4).item(0)
                .setTextContent(gmData.getFilterCategory4());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_5).item(0)
                .setTextContent(gmData.getFilterCategory5());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_6).item(0)
                .setTextContent(gmData.getFilterCategory6());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_7).item(0)
                .setTextContent(gmData.getFilterCategory7());
        gmDataElement.getElementsByTagName(Constants.FILTER_CATEGORY_8).item(0)
                .setTextContent(gmData.getFilterCategory8());
        applyXmlChanges();
    }

    //  Εφαρμογή απαιτούμενων αλλαγών XML

    private void applyXmlChanges() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(new DOMSource(document), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}