package Files.view;

import Files.handler.PhotoSearchHandler;
import Files.handler.PhotoXmlHandler;
import Files.model.Photo;
import Files.model.PhotoDatabase;
import Files.model.SearchFilterCriteria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class DataBaseManagement extends JFrame {
    private final static String NO_PHOTO_FOUND_IMAGE_LOCATION = "src/PhotoFolder/notFound.png";
    private ArrayList<String> photoIds;
    private JPanel DataBaseManagmentPanel;
    private JButton ScrapPhotoButton;
    private JButton EditPhotoDetailsButton;
    private JButton ShowMoreButton;
    private JButton closeButton;
    private JLabel labelPhoto1;
    private JLabel labelPhoto2;
    private JLabel labelPhoto3;
    private JLabel labelPhoto4;
    private JLabel labelPhoto5;
    private JLabel labelPhoto6;
    private JLabel labelPhoto7;
    private JLabel labelPhoto8;
    private JLabel labelPhoto9;
    private JLabel labelPhoto10;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private JCheckBox checkBox9;
    private JCheckBox checkBox10;

    private int PAGE_OFFSET = 0;
    private int PAGE_LIMIT = 10;

    private JLabel[] photoImageLabels = {labelPhoto1, labelPhoto2, labelPhoto3, labelPhoto4, labelPhoto5, labelPhoto6,
            labelPhoto7, labelPhoto8, labelPhoto9, labelPhoto10};
    private JCheckBox[] photoCheckboxes = {checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7,
            checkBox8, checkBox9, checkBox10};

    public DataBaseManagement(PhotoDatabase photoDb, PhotoXmlHandler photoXMLHandler, SearchFilterCriteria sfCriteria) {
        PhotoSearchHandler photoSearchHandler = new PhotoSearchHandler();
        Map<String, Photo> filteredPhotos;

        setContentPane(DataBaseManagmentPanel);
        setTitle("Photo Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);

        if (sfCriteria != null) {  //Δίνει αποτελέσματα αναζήτησης φωτογραφιών με βάση τα κριτήρια αναζήτησης
            filteredPhotos = photoSearchHandler.searchPhotos(photoDb, sfCriteria); //search photos βρίσκεται στο PhotoSearch Handler
        } else { //Εάν δεν έχουν καθοριστεί κριτήρια αναζήτησης, αναζητεί όλες τις φωτογραφίες
            filteredPhotos = photoDb.getPhotos();
        }

        if (filteredPhotos.isEmpty()) {
            labelPhoto1.setIcon(new ImageIcon(NO_PHOTO_FOUND_IMAGE_LOCATION)); //εάν δεν υπάρχουν φωτογραφίες εμφανίζει την φώτο σφάλματος
            for (JCheckBox photoCheckbox : photoCheckboxes) {
                photoCheckbox.setVisible(false);
            }
        }


        this.photoIds = new ArrayList<>(filteredPhotos.keySet()); //Παίρνει όλα τα αναγνωριστικά φωτογραφιών id

        //Εμφάνιση αποτελέσματος, για κάθε σελίδα εμφανίζουμε έως 10 αποτελέσματα
        for (int i = 0; i < (Math.min(photoIds.size(), PAGE_LIMIT)); i++) { //Εάν λάβουμε αποτελέσματα αναζήτησης μικρότερα από PAGE_LIMIT, θα πρέπει να επαναλάβουμε μέχρι το μήκος των αποτελεσμάτων αναζήτησης, διαφορετικά θα προκύψει ArrayIndexBound
            String photoId = photoIds.get(PAGE_OFFSET + i);
            photoImageLabels[i].setIcon(
                    new ImageIcon(filteredPhotos.get(photoId).getImagePath()) //Ορίστε την εικόνα στο label
            );
        }

        closeButton.addActionListener(new ActionListener() { //κλείνει το παράθυρο
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ShowMoreButton.addActionListener(new ActionListener() { //Εμφάνιση της σελίδας Επόμενες Φωτογραφίες με μέγιστο αριθμό 10 φωτογραφιών
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNextPage(photoDb, filteredPhotos);
            }
        });

        ScrapPhotoButton.addActionListener(new ActionListener() { //Διαγραφή επιλεγμένων φωτογραφιών, 1 ή περισσότερες φωτογραφίες μπορούν να διαγραφούν
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedImages(photoDb, filteredPhotos, photoXMLHandler);
            }
        });

        EditPhotoDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifySelectedImage(photoDb, photoXMLHandler);
            }
        });
    }

    //Μετάβαση στην επόμενη σελίδα
    private void displayNextPage(PhotoDatabase photoDb, Map<String, Photo> filteredPhotos) {
        if (PAGE_OFFSET + PAGE_LIMIT < photoIds.size()) {
            PAGE_OFFSET += PAGE_LIMIT;
            displyPhotosPage(filteredPhotos);
        }
    }

    private void displyPhotosPage(Map<String, Photo> filteredPhotos) {
        int lastIndex = Math.min(PAGE_OFFSET + PAGE_LIMIT,
                photoIds.size());

        for (int i = 0; i < PAGE_LIMIT; i++) {
            if (PAGE_OFFSET + i < lastIndex) {
                //επαναλαμβάνεται όσο υπάρχουν φωτογραφίες να εμφανιστούν
                String key = photoIds.get(PAGE_OFFSET + i);
                photoImageLabels[i].setIcon(
                        new ImageIcon(filteredPhotos.get(key).getImagePath())
                );
                photoCheckboxes[i].setVisible(true);
                photoCheckboxes[i].setSelected(false);
            } else {
                photoImageLabels[i].setIcon(null);
                photoCheckboxes[i].setVisible(false);
            }
        }
    }


    private void deleteSelectedImages(PhotoDatabase photoDb, Map<String, Photo> filteredPhotos,
                                      PhotoXmlHandler photoXMLHandler) {
        ArrayList<String> deleteIds = new ArrayList<>();
        for (int i = 0; i < PAGE_LIMIT; i++) {
            if (photoCheckboxes[i].isVisible() && photoCheckboxes[i].isSelected()) {
                String key = photoIds.get(PAGE_OFFSET + i);
                deleteIds.add(key);
            }
        }

        photoXMLHandler.deletePhotosInDb(photoDb, deleteIds);

        for (String key : deleteIds) {
            photoIds.remove(key);
        }

        if (PAGE_OFFSET >= photoIds.size()) {
            PAGE_OFFSET = Math.max(0, photoIds.size() - PAGE_LIMIT);
        }

        displyPhotosPage(filteredPhotos);
    }

    //επαναλαμβάνεται για όσες φώτο επιλέγει ο χρήστης και ενημερώνει τα MetaData τους
    private void modifySelectedImage(PhotoDatabase photoDb, PhotoXmlHandler photoXmlHandler) {
        for (int i = 0; i < PAGE_LIMIT; i++) {
            if (photoCheckboxes[i].isVisible() && photoCheckboxes[i].isSelected()) {
                String key = photoIds.get(PAGE_OFFSET + i);
                Photo photo = photoDb.getPhotos().get(key);

                //Ενημερώνει τα MetaData της πρώτης φωτογραφίας

                EditPhoto editPhoto = new EditPhoto(photo, photoDb, photoXmlHandler, DataBaseManagement.this);
                editPhoto.setVisible(true);
                return;
            }
        }
    }
}
