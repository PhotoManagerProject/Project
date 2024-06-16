package Files.handler;

import Files.constants.Constants;
import Files.model.Photo;
import Files.model.UserProfile;
import Files.constants.ErrorCode;
import Files.exception.PhotoManagerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserProfileHandler {

//αρχική φόρμα επιλογής προφίλ

    private Document document;
    private String filePath;
    private Map<String, Photo> photoMap = new HashMap<>();

    public UserProfileHandler(final String filePath) {
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

    public UserProfile readUserProfileById(final String userProfileId) {
        try {
            NodeList userProfileNode = document.getElementsByTagName(userProfileId);
            return readUserProfileDetails(userProfileId, userProfileNode);
        } catch (Exception ex) {
            throw new PhotoManagerException(ErrorCode.USER_PROFILE_READ_ERROR, "Error occurred while reading user profile" + ex.getMessage());
        }
    }


    /**
     * επαναφορά των πληροφοριών προφίλ χρήστη
     */
    public void deleteUserProfile(final String userProfileId) {
        if (userProfileId.isEmpty()) {
            throw new PhotoManagerException(ErrorCode.USER_PROFILE_DELETE_ERROR, "Provided empty details to delete");
        }

        NodeList userProfileXmlNodes = document.getElementsByTagName(userProfileId);

        if (userProfileXmlNodes.getLength() == 0) {
            throw new PhotoManagerException(ErrorCode.USER_PROFILE_DELETE_ERROR, "No User Profile found for delete");
        }

        Element userElement = (Element) userProfileXmlNodes.item(0);

        userElement.getElementsByTagName(Constants.USER_NAME).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.PASSWORD).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.FIRST_NAME).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.LAST_NAME).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.PHONE_NUM).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.CITY).item(0)
                .setTextContent(Constants.EMPTY);
        userElement.getElementsByTagName(Constants.SPECIALITY).item(0)
                .setTextContent(Constants.EMPTY);
        applyXmlChanges();
        System.out.println("User Profile: "+ userProfileId+ " has been deleted successfully!");
    }

    public void saveUserProfile(final UserProfile updatedUserProfile) {

        UserProfile profileToSave = null;
        if (updatedUserProfile != null && (updatedUserProfile.getUserName().isEmpty() ||
                updatedUserProfile.getPassWord().isEmpty())) {
            profileToSave = new UserProfile(
                    updatedUserProfile.getId(),
                    updatedUserProfile.getId(),
                    UUID.randomUUID().toString(),
                    updatedUserProfile.getFirstName(),
                    updatedUserProfile.getLastName(),
                    updatedUserProfile.getPhoneNum(),
                    updatedUserProfile.getCity(),
                    updatedUserProfile.getSpeciality());

        } else if (updatedUserProfile != null && !updatedUserProfile.getUserName().isEmpty() && !updatedUserProfile.getPassWord().isEmpty()) {
            profileToSave = new UserProfile(
                    updatedUserProfile.getId(),
                    updatedUserProfile.getUserName(),
                    updatedUserProfile.getPassWord(),
                    updatedUserProfile.getFirstName(),
                    updatedUserProfile.getLastName(),
                    updatedUserProfile.getPhoneNum(),
                    updatedUserProfile.getCity(),
                    updatedUserProfile.getSpeciality());

        }
        modifyXmlUserProfile(profileToSave);

    }

    /**
     * Κάνει την τροποποίηση XML για το προφίλ χρήστη
     */
    private void modifyXmlUserProfile(UserProfile userProfile) {
        if (userProfile == null) {
            throw new PhotoManagerException(ErrorCode.GENERAL_USER_PROFILE_UPDATE_ERROR, "Empty UserProfile data found to update");
        }

        NodeList userProfileXmlNodes = document.getElementsByTagName(userProfile.getId());
        Element userElement = (Element) userProfileXmlNodes.item(0);

        userElement.getElementsByTagName(Constants.USER_NAME).item(0)
                .setTextContent(userProfile.getUserName());
        userElement.getElementsByTagName(Constants.PASSWORD).item(0)
                .setTextContent(userProfile.getPassWord());
        userElement.getElementsByTagName(Constants.FIRST_NAME).item(0)
                .setTextContent(userProfile.getFirstName());
        userElement.getElementsByTagName(Constants.LAST_NAME).item(0)
                .setTextContent(userProfile.getLastName());
        userElement.getElementsByTagName(Constants.PHONE_NUM).item(0)
                .setTextContent(userProfile.getPhoneNum());
        userElement.getElementsByTagName(Constants.CITY).item(0)
                .setTextContent(userProfile.getCity());
        userElement.getElementsByTagName(Constants.SPECIALITY).item(0)
                .setTextContent(userProfile.getSpeciality());
        applyXmlChanges();
    }

    private static UserProfile readUserProfileDetails(String userProfileId, NodeList userProfiles) {
        Element userProfileElement = (Element) userProfiles.item(0);  //διάβασμα στοιχείου XML προφίλ χρήστη
        return new UserProfile(userProfileId,
                userProfileElement.getElementsByTagName(Constants.USER_NAME).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.PASSWORD).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.FIRST_NAME).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.LAST_NAME).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.PHONE_NUM).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.CITY).item(0).getTextContent().trim(),
                userProfileElement.getElementsByTagName(Constants.SPECIALITY).item(0).getTextContent().trim()
        );
    }

    /**
     * Εφαρμογή απαιτούμενων αλλαγών XML
     */
    private void applyXmlChanges() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(new DOMSource(document), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
