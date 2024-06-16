package Files.view;

import Files.constants.Constants;
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

public class EditPhoto extends JFrame {

    private static PhotoXmlHandler photoXmlHandler;
    private JPanel EditPhotoPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField1;
    private JButton browsePhotoButton;
    private JButton modifyPhoto;
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

    public EditPhoto(Photo photo, PhotoDatabase profilePhotoDb, PhotoXmlHandler photoXmlHandler, JFrame mainWindow) {
        this.photoXmlHandler =  photoXmlHandler;
        setContentPane(EditPhotoPanel);
        initBackground();
        setTitle("Edit Photo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Κλείνει μόνο την τρέχουσα σελίδα για να επισκεφθεί η κύρια σελίδα
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);


        //Όταν ανοίξει το παράθυρο, πρέπει να ορίσουμε General Meta-Data Lebels από τις αποθηκευμένες τιμές Xml
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                initBackground();
                uploadImagePath = photo.getImagePath();
                photoDisplayLabel.setIcon(new ImageIcon(uploadImagePath));
                GeneralMetaData generalMetaData = profilePhotoDb.getGeneralMetaData();
                labelFilter1.setText(generalMetaData.getFilterCategory1());
                labelFilter2.setText(generalMetaData.getFilterCategory2());
                labelFilter3.setText(generalMetaData.getFilterCategory3());
                labelFilter4.setText(generalMetaData.getFilterCategory4());
                labelFilter5.setText(generalMetaData.getFilterCategory5());
                labelFilter6.setText(generalMetaData.getFilterCategory6());
                labelFilter7.setText(generalMetaData.getFilterCategory7());
                labelFilter8.setText(generalMetaData.getFilterCategory8());


                textField1.setText(photo.getMetaData1());
                textField2.setText(photo.getMetaData2());
                textField3.setText(photo.getMetaData3());
                textField4.setText(photo.getMetaData4());
                textField5.setText(photo.getMetaData5());
                textField6.setText(photo.getMetaData6());
                textField7.setText(photo.getMetaData7());
                textField8.setText(photo.getMetaData8());
            }
        });

        browsePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                browsePhoto();
            }
        });

        modifyPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Photo modifiedPhoto = new Photo(photo.getId(), uploadImagePath, textField1.getText(), textField2.getText(),
                        textField3.getText(), textField4.getText(), textField5.getText(), textField6.getText(),
                        textField7.getText(), textField8.getText()
                );
                photoXmlHandler.updatePhotoInDb(profilePhotoDb, modifiedPhoto);
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

    private void initBackground() {
        EditPhotoPanel.setBackground(new Color(204, 255, 255));
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
