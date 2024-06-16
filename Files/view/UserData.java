package Files.view;

import Files.handler.UserProfileHandler;
import Files.model.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UserData extends JFrame {
    private UserProfileHandler userProfileHandler;
    final String profileXmlFilePath = "src/SelectionScreenDatabase.xml";


    private JPanel UserDataPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton SaveButton;


    public UserData(boolean navigateFromSelectionScreenWindow, String userProfileId) {
        userProfileHandler = new UserProfileHandler(profileXmlFilePath);
        setContentPane(UserDataPanel);
        setTitle("Profile Data app");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Κλείνει μόνο την τρέχουσα σελίδα για να επισκεφθεί την κύρια σελίδα
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);

        UserProfile userProfile = userProfileHandler.readUserProfileById(userProfileId);
        loadUserProfileInfo(userProfile);
        setVisible(true);

        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //επιτρέπει την υποβολή μόνο εάν όλα τα πεδία δεν είναι άδεια
                if (!checkAllUserProfileInfoFieldsEmpty()) {
                    final UserProfile updatedUserProfile = new UserProfile(userProfile.getId(), userProfile.getUserName(),
                            userProfile.getPassWord(), textField1.getText(), textField2.getText(), textField3.getText(),
                            textField4.getText(), textField5.getText());

                    userProfileHandler.saveUserProfile(updatedUserProfile);
                    dispose();
                    if(navigateFromSelectionScreenWindow){
                        SelectionScreenProfile.createNewInstance();
                    }
                 }
            }
        });
    }

    public static void main(String[] args) {
    }


    private void loadUserProfileInfo(UserProfile userProfile) {
        if (Objects.nonNull(userProfile) && (!userProfile.getUserName().isEmpty() && !userProfile.getPassWord().
                isEmpty())) {
            textField1.setText(userProfile.getFirstName());
            textField2.setText(userProfile.getLastName());
            textField3.setText(userProfile.getPhoneNum());
            textField4.setText(userProfile.getCity());
            textField5.setText(userProfile.getSpeciality());
        }
    }

    private boolean checkAllUserProfileInfoFieldsEmpty() {
        return textField1.getText().trim().isEmpty() && textField2.getText().trim().isEmpty() &&
                textField3.getText().trim().isEmpty() && textField4.getText().trim().isEmpty()
                && textField5.getText().trim().isEmpty();
    }
}
