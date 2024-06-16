package Files.view;

import Files.view.profile1.Profile1_Gadget;
import Files.view.profile2.Profile2;
import Files.view.profile3.Profile3;
import Files.view.profile4.Profile4;
import Files.view.profile5.Profile5;
import Files.view.profile6.Profile6;
import Files.constants.Constants;
import Files.handler.UserProfileHandler;
import Files.model.UserProfile;
import Files.utils.BackgroundUtils;
import Files.utils.PhotoDateTimeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionScreenProfile extends JFrame {

    private static SelectionScreenProfile selectionScreenProfileInstance;

    private UserData userData;

    final String profileXmlFilePath = "src/SelectionScreenDatabase.xml";
    private static UserProfile selectedUserProfile = null;
    private final static int DELAY = 1000;
    private JButton SelectionProfileButton6;
    private JPanel SelectionScreenProfilePanel;
    private JButton SelectionProfileButton5;
    private JButton SelectionProfileButton2;
    private JButton SelectionProfileButton4;
    private JButton SelectionProfileButton3;
    private JButton userProfile1Button;
    private JButton ShowDate;
    private JButton clickMeButton;
    private JLabel showDateLabel;
    private JLabel showTimeLabel;

    public SelectionScreenProfile() {
        UserProfileHandler userProfileHandler = new UserProfileHandler(profileXmlFilePath);

        setContentPane(SelectionScreenProfilePanel);
        setTitle("Profiles GUI app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BackgroundUtils.applyRandomBackground(SelectionScreenProfilePanel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);

        selectionScreenProfileInstance = this; //ρύθμιση SelectionProfile για πρόσβαση

        setButtonTextForPage(userProfileHandler);

        setVisible(true);

        PhotoDateTimeUtils.displayDate(showDateLabel);
        Timer photoTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhotoDateTimeUtils.updateClock(showTimeLabel);
            }
        });
        photoTimer.start();

        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackgroundUtils.applyRandomBackground(SelectionScreenProfilePanel);
            }
        });

        //Επιλογή προφίλ Gadget πελάτη για DataBase1
        userProfile1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_1);
                userProfile1Button.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) {
                    userProfile1Button.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_1);
                } else {
                    dispose();
                    Profile1_Gadget profile1Gadget = new Profile1_Gadget();
                    profile1Gadget.setVisible(true);
                    userProfile1Button.setText(Constants.USER_1);
                }
            }
        });

        //Επιλογή προφίλ2 για DataBase2
        SelectionProfileButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_2);
                SelectionProfileButton2.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) { //If we don't see User Name and Password that means this profile is not created yet
                    SelectionProfileButton2.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_2);
                } else {
                    dispose();
                    Profile2 profile2Fruit = new Profile2();
                    profile2Fruit.setVisible(true);
                    SelectionProfileButton2.setText(Constants.USER_2);
                }
            }
        });

        //Επιλογή προφίλ3 για DataBase3
        SelectionProfileButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_3);
                SelectionProfileButton3.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) {
                    SelectionProfileButton3.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_3);
                } else {
                    dispose();
                    Profile3 profile3Vet = new Profile3();
                    profile3Vet.setVisible(true);
                    SelectionProfileButton3.setText(Constants.USER_3);
                }
            }
        });

        //Επιλογή προφίλ4 για DataBase4
        SelectionProfileButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_4);
                SelectionProfileButton4.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) {
                    SelectionProfileButton4.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_4);
                } else {
                    dispose();
                    Profile4 profile4Dress = new Profile4();
                    profile4Dress.setVisible(true);
                    SelectionProfileButton4.setText(Constants.USER_4);
                }
            }
        });

        //Επιλογή προφίλ5 για DataBase5
        SelectionProfileButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_5);
                SelectionProfileButton5.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) {
                    SelectionProfileButton5.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_5);
                } else {
                    dispose();
                    Profile5 profile5Drink = new Profile5();
                    profile5Drink.setVisible(true);
                    SelectionProfileButton5.setText(Constants.USER_5);
                }
            }
        });

        //Επιλογή προφίλ6 για DataBase6
        SelectionProfileButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserProfile = userProfileHandler.readUserProfileById(Constants.USER_6);
                SelectionProfileButton6.setText(selectedUserProfile.getUserName());
                System.out.println("selected profile" + selectedUserProfile.toString());
                if (selectedUserProfile.getUserName().isEmpty() || selectedUserProfile.getPassWord().isEmpty()) {
                    SelectionProfileButton6.setText(Constants.ADD_NEW_PROFILE);
                    userData = new UserData(true, Constants.USER_6);
                } else {
                    dispose();
                    Profile6 profile6Vehicle = new Profile6();
                    profile6Vehicle.setVisible(true);
                    SelectionProfileButton6.setText(Constants.USER_6);
                }
            }
        });

        System.out.println("selected user profile" + selectedUserProfile);
    }

    /**
     * @return επαναφέρει το τελευταίο επιλεγμένο προφίλ χρήστη
     */
    public static UserProfile getSelectedUserProfile() {
        return selectedUserProfile;
    }

    public static void main(String[] args) {
        // Δημιουργία ενός αντικειμένου ProfileEditor
        new SelectionScreenProfile();
    }


    /**
     * @return παράδειγμα SelectionScreenProfile
     * <p>
     * Αυτή η μέθοδος βοηθά στην πρόσβαση του παραθύρου SelectionScreenProfile από οποιοδήποτε άλλο παράθυρο
     */
    public static SelectionScreenProfile getInstance() {
        return selectionScreenProfileInstance;
    }

    /**
     * Αυτή η μέθοδος κλείνει το τρέχον παράθυρο SelectionScreenProfile και δημιουργείται νέο παράθυρο SelectionScreenProfile(καθώς δημιουργείται ενα νέο προφιλ)
     */
    public static void createNewInstance() {
        if (selectionScreenProfileInstance != null) {
            selectionScreenProfileInstance.dispose();
        }
        selectionScreenProfileInstance = new SelectionScreenProfile();
        selectionScreenProfileInstance.setVisible(true);
    }

   //κουμπία των προφίλ
    private void setButtonTextForPage(UserProfileHandler userProfileHandler) {
        UserProfile userProfile1 = userProfileHandler.readUserProfileById(Constants.USER_1);
        UserProfile userProfile2 = userProfileHandler.readUserProfileById(Constants.USER_2);
        UserProfile userProfile3 = userProfileHandler.readUserProfileById(Constants.USER_3);
        UserProfile userProfile4 = userProfileHandler.readUserProfileById(Constants.USER_4);
        UserProfile userProfile5 = userProfileHandler.readUserProfileById(Constants.USER_5);
        UserProfile userProfile6 = userProfileHandler.readUserProfileById(Constants.USER_6);

        //Αλλάζει το όνομα απο Add new profile σε UserN
        userProfile1Button.setText(isUserProfileExists(userProfile1) ? userProfile1.getUserName() : Constants.ADD_NEW_PROFILE);
        SelectionProfileButton2.setText(isUserProfileExists(userProfile2) ? userProfile2.getUserName() : Constants.ADD_NEW_PROFILE);
        SelectionProfileButton3.setText(isUserProfileExists(userProfile3) ? userProfile3.getUserName() : Constants.ADD_NEW_PROFILE);
        SelectionProfileButton4.setText(isUserProfileExists(userProfile4) ? userProfile4.getUserName() : Constants.ADD_NEW_PROFILE);
        SelectionProfileButton5.setText(isUserProfileExists(userProfile5) ? userProfile5.getUserName() : Constants.ADD_NEW_PROFILE);
        SelectionProfileButton6.setText(isUserProfileExists(userProfile6) ? userProfile6.getUserName() : Constants.ADD_NEW_PROFILE);
    }

    private boolean isUserProfileExists(UserProfile userProfile) {
        return !userProfile.getUserName().isEmpty() && !userProfile.getPassWord().isEmpty();
    }

}