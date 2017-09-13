package category;

import contact.Contact;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EmailDialog extends JDialog {
    //Instance variables
    private ArrayList<String> categories;
    private ArrayList<Contact> contacts;
    private Types currentType;

    //JComponents
    private JPanel contentPane;
    private JButton buttonOK;
    private JCheckBox ageCheckBox;
    private JTextField age1Field;
    private JTextField age2Field;
    private JButton emailButton;
    private JButton mobileButton;
    private JButton homeButton;
    private JButton addressButton;
    private JTextArea displayTextArea;
    private JLabel typesLabel;
    private JCheckBox allContactsCheckBox;

    public EmailDialog(ArrayList<String> categories, ArrayList<Contact> contacts) {
        //Initialization
        this.categories = categories;
        this.contacts = contacts;
        currentType = Types.EMAIL;
        setDisplayTextArea();

        //Add listeners
        //Buttons
        emailButton.addActionListener(actionEvent -> {
            currentType = Types.EMAIL;
            setDisplayTextArea();
        });
        mobileButton.addActionListener(actionEvent -> {
            currentType = Types.MOBILE;
            setDisplayTextArea();
        });
        homeButton.addActionListener(actionEvent -> {
            currentType = Types.HOME;
            setDisplayTextArea();
        });
        addressButton.addActionListener(actionEvent -> {
            currentType = Types.ADDRESS;
            setDisplayTextArea();
        });

        //Checkbox
        ageCheckBox.addActionListener(actionEvent -> {
            boolean setEnabled = false;
            if (ageCheckBox.isSelected()) setEnabled = true;
            age1Field.setEnabled(setEnabled);
            age2Field.setEnabled(setEnabled);
        });

        //Create window
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(500, 350));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String[] run() {
        return null;
    }

    private void setDisplayTextArea() {
        typesLabel.setText(currentType.toString());
        String displayTemp = "";
        for (Contact tempContact : contacts) {
            for (String tempCategory : categories) {
                if (tempContact.isInCategory(tempCategory) || allContactsCheckBox.isSelected()) {
                    if (ageCheckBox.isSelected()) {
                        if (isWithinAgeRange(tempContact))
                            displayTemp += getInfo(tempContact) + "\n";
                    } else displayTemp += getInfo(tempContact) + "\n";
                    break;
                }
            }
        }
        displayTextArea.setText(displayTemp);
    }

    private String getInfo(Contact contact) {
        switch (currentType) {
            case EMAIL:
                String email = contact.getEmail();
                if (!email.equals("")) return email;
                break;
            case MOBILE:
                String number = contact.getMobile();
                if (!number.equals("   -   -    ")) return number;
                break;
            case HOME:
                number = contact.getHome();
                if (!number.equals("   -   -    ")) return number;
                break;
            case ADDRESS:
                String address = contact.getAddress();
                if (!address.equals("")) return address + "\n";
                break;
        }
        return "";
    }

    private boolean isWithinAgeRange(Contact contact) {
        int age = contact.getAge();
        if (age1Field.getText().equals("") && age2Field.getText().equals("")) return false;
        if (!age1Field.getText().equals("") && !age2Field.getText().equals("")) {
            return age <= Integer.parseInt(age2Field.getText()) && age >= Integer.parseInt(age1Field.getText());
        }
        if (!age1Field.getText().equals("") && age2Field.getText().equals(""))
            return age >= Integer.parseInt(age1Field.getText());
        else return age <= Integer.parseInt(age2Field.getText());
    }

    private enum Types {
        EMAIL, HOME, MOBILE, ADDRESS
    }
}