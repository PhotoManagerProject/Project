package Files.view;

import Files.constants.Constants;
import Files.constants.ErrorCode;
import Files.exception.PhotoManagerException;
import Files.handler.PhotoXmlHandler;
import Files.model.GeneralMetaData;
import Files.model.Photo;
import Files.model.PhotoDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.UUID;

public class PhotoUpload extends JFrame {

    private static PhotoXmlHandler photoXmlHandler;
    private JPanel PhotoUploadPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField1;
    private JButton browsePhotoButton;
    private JButton uploadButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JLabel photoDisplayLabel;
    private JLabel labelFilter1;
    private JLabel labelFilter2;
    private JLabel labelFilter3;
    private JLabel labelFilter4;
    private JLabel labelFilter5;
    private JLabel labelFilter6;
    private JLabel labelFilter7;
    private JLabel labelFilter8;
    private String uploadImagePath = "";

    public PhotoUpload(PhotoDatabase profilePhotoDb, String xmlFilePath, JFrame mainWindow) {
        setContentPane(PhotoUploadPanel);
        initBackground();
        setTitle("Photo Upload");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Κλείνει μόνο την τρέχουσα σελίδα για να επισκεφθεί την κύρια σελίδα
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);

        photoXmlHandler = new PhotoXmlHandler(xmlFilePath);

//Όταν ανοίξει το παράθυρο, πρέπει να ορίσουμε τα General Meta-Data Labels από τις αποθηκευμένες τιμές Xml
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                initBackground();
                GeneralMetaData generalMetaData = profilePhotoDb.getGeneralMetaData();
                labelFilter1.setText(generalMetaData.getFilterCategory1());
                labelFilter2.setText(generalMetaData.getFilterCategory2());
                labelFilter3.setText(generalMetaData.getFilterCategory3());
                labelFilter4.setText(generalMetaData.getFilterCategory4());
                labelFilter5.setText(generalMetaData.getFilterCategory5());
                labelFilter6.setText(generalMetaData.getFilterCategory6());
                labelFilter7.setText(generalMetaData.getFilterCategory7());
                labelFilter8.setText(generalMetaData.getFilterCategory8());
            }
        });

        browsePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browsePhoto();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPhoto(profilePhotoDb);
            }
        }
        );
    }

    private void browsePhoto() {
        JFileChooser fileChooser = new JFileChooser("src/PhotoFolder");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String filePath = "src/PhotoFolder/" + fileName;
            uploadImagePath = filePath;
            System.out.println("User has selected an upload image: " + uploadImagePath);
            photoDisplayLabel.setIcon(new ImageIcon(uploadImagePath));
        }
    }

    private void uploadPhoto(PhotoDatabase profilePhotoDb) {
        if (uploadImagePath.isEmpty()) {
            throw new PhotoManagerException(ErrorCode.PHOTO_UPLOAD_ERROR,
                    "Error occurred, upload file can not be empty");
        }
        if (!checkSingleMetaDataPresent()) {
            throw new PhotoManagerException(ErrorCode.PHOTO_UPLOAD_ERROR,
                    "Error occurred, at least one meta data must be provided");
        }

        String photoId = UUID.randomUUID().toString();
        Photo photo = new Photo(photoId, uploadImagePath,
                textField1.getText(),
                textField2.getText(),
                textField3.getText(),
                textField4.getText(),
                textField5.getText(),
                textField6.getText(),
                textField7.getText(),
                textField8.getText()
        );

        photoXmlHandler.insertPhotoInDb(profilePhotoDb, photo);
        resetPhotoMetaDataFilters();
        System.out.println("Successfully uploaded photo, id: " + photoId);
    }

    private void initBackground() {
        PhotoUploadPanel.setBackground(new Color(204, 255, 255));
    }


    private void resetPhotoMetaDataFilters() {
        textField1.setText(Constants.EMPTY);
        textField2.setText(Constants.EMPTY);
        textField3.setText(Constants.EMPTY);
        textField4.setText(Constants.EMPTY);
        textField5.setText(Constants.EMPTY);
        textField6.setText(Constants.EMPTY);
        textField7.setText(Constants.EMPTY);
        textField8.setText(Constants.EMPTY);
    }

    private boolean checkSingleMetaDataPresent() {
        return !textField1.getText().isEmpty() ||
                !textField2.getText().isEmpty() ||
                !textField3.getText().isEmpty() ||
                !textField4.getText().isEmpty() ||
                !textField5.getText().isEmpty() ||
                !textField6.getText().isEmpty() ||
                !textField7.getText().isEmpty() ||
                !textField8.getText().isEmpty();
    }
}
