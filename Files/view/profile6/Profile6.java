package Files.view.profile6;

import Files.constants.Constants;
import Files.handler.PhotoXmlHandler;
import Files.handler.UserProfileHandler;
import Files.model.GeneralMetaData;
import Files.model.PhotoDatabase;
import Files.model.SearchFilterCriteria;
import Files.utils.BackgroundUtils;
import Files.view.DataBaseManagement;
import Files.view.PhotoUpload;
import Files.view.SelectionScreenProfile;
import Files.view.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Profile6 extends JFrame {

    final String xmlFilePath = "src/Database6.xml";

    final String profileXmlFilePath = "src/SelectionScreenDatabase.xml";

    final static PhotoDatabase profile1PhotoDb = new PhotoDatabase();

    private JPanel GadgetPanel;
    private JButton UserDataButton;
    private JButton uploadPhotoButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    private JTextField textField6;

    private JTextField textField7;

    private JTextField textField8;

    private JButton SearchButton;
    private JButton BrowseallPhotosButton;
    private JButton BackgroundChangerButton;
    private JButton SignOutButton;
    private JButton FilterCatagoryChanger;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JButton deleteProfileButton;
    private JLabel Image1;
    private JLabel Image2;
    private JLabel Image3;
    private JLabel Image4;
    private JLabel Image5;
    private JLabel Image6;
    private JLabel Image7;
    private JLabel Image8;
    private JLabel Image9;
    private JLabel Image10;

    String pathofimage;
    String pathofimage2;
    String pathofimage3;
    String pathofimage4;
    String pathofimage5;
    String pathofimage6;
    String pathofimage7;
    String pathofimage8;
    String pathofimage9;
    String pathofimage10;

    public Profile6() {

        PhotoXmlHandler photoXmlHandler = new PhotoXmlHandler(xmlFilePath);
        photoXmlHandler.readPhotosXmlToMap(profile1PhotoDb);

        setContentPane(GadgetPanel);
        setTitle("Gadget GUI app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                BackgroundUtils.initBackground(GadgetPanel);
                GeneralMetaData generalMetaData = profile1PhotoDb.getGeneralMetaData();
                label1.setText(generalMetaData.getFilterCategory1());
                label2.setText(generalMetaData.getFilterCategory2());
                label3.setText(generalMetaData.getFilterCategory3());
                label4.setText(generalMetaData.getFilterCategory4());
                label7.setText(generalMetaData.getFilterCategory5());
                label8.setText(generalMetaData.getFilterCategory6());
                label9.setText(generalMetaData.getFilterCategory7());
                label10.setText(generalMetaData.getFilterCategory8());
            }
        });

        BackgroundChangerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackgroundUtils.applyRandomBackground(GadgetPanel);
            }
        });

        UserDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UserData userData = new UserData(false, Constants.USER_6);
                userData.setVisible(true);

            }
        });

        FilterCatagoryChanger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterEditor filterEditor = new FilterEditor(profile1PhotoDb, xmlFilePath, Profile6.this);
                filterEditor.setVisible(true);
            }
        });

        uploadPhotoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PhotoUpload photoUploadWindow = new PhotoUpload(profile1PhotoDb, xmlFilePath, Profile6.this);
                photoUploadWindow.setVisible(true);
            }
        });

        BrowseallPhotosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DataBaseManagement dbm = new DataBaseManagement(profile1PhotoDb, photoXmlHandler, null);
                dbm.setVisible(true);
            }
        });

        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchFilterCriteria sfCriteria = new SearchFilterCriteria(
                        textField1.getText(),
                        textField2.getText(),
                        textField3.getText(),
                        textField4.getText(),
                        textField5.getText(),
                        textField6.getText(),
                        textField7.getText(),
                        textField8.getText()
                );
                DataBaseManagement dbm = new DataBaseManagement(profile1PhotoDb, photoXmlHandler, sfCriteria);
                dbm.setVisible(true);
            }
        });

        SignOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                SwingUtilities.invokeLater(() -> {
                    SelectionScreenProfile selectionScreenProfile = new SelectionScreenProfile();
                    selectionScreenProfile.setVisible(true);
                });
            }
        });

        deleteProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserProfileHandler(profileXmlFilePath).deleteUserProfile(Constants.USER_1);
                dispose();
                 SelectionScreenProfile.createNewInstance();
            }
        });
    }

    public static void main(String[] args) {
        Profile6 profile2Fruit = new Profile6();
        profile2Fruit.setVisible(true);
    }
}
