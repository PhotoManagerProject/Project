package Files.view.profile2;

import Files.view.profile1.Profile1_Gadget;
import Files.handler.PhotoXmlHandler;
import Files.model.GeneralMetaData;
import Files.model.PhotoDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterEditor extends JFrame {

    private static PhotoXmlHandler photoXmlHandler;
    private JPanel filterEditorPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField1;
    private JButton saveButton;
    private JButton exitButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;

    public FilterEditor(PhotoDatabase profilePhotoDb, String xmlFilePath, JFrame profileWindow) {
        setContentPane(filterEditorPanel);
        setTitle("Filter Editor");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);

        photoXmlHandler = new PhotoXmlHandler(xmlFilePath);

        textField1.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory1());
        textField2.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory2());
        textField3.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory3());
        textField4.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory4());
        textField5.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory5());
        textField6.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory6());
        textField7.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory7());
        textField8.setText(profilePhotoDb.getGeneralMetaData().getFilterCategory8());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralMetaData latestMetaData = new GeneralMetaData(
                        textField1.getText(),
                        textField2.getText(),
                        textField3.getText(),
                        textField4.getText(),
                        textField5.getText(),
                        textField6.getText(),
                        textField7.getText(),
                        textField8.getText()
                        );
                photoXmlHandler.updateGeneralMetaDataInDb(profilePhotoDb, latestMetaData);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitAndRedirectToMainWindow(profileWindow);
            }
        });

    }

    private void exitAndRedirectToMainWindow(JFrame profileWindow) {
        this.dispose();
        profileWindow.dispose();
        Profile1_Gadget profile1GadgetWindow = new Profile1_Gadget();
        profile1GadgetWindow.setVisible(true);
    }
}
