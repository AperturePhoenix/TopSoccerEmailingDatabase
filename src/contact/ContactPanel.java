package contact;

import core.CyclingSpinnerListModel;
import core.ListManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.text.MaskFormatter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Lance Judan on 9/3/17
 */
public class ContactPanel implements ListManager.ListPanel{
    //Instance variables
    private Contact currentContact;
    private ListManager<Contact> contactManager;
    private DefaultListModel<Contact> contactModel;
    private CyclingSpinnerListModel genderModel;
    private SpinnerDateModel dateModel;

    //JComponents
    private JPanel contactPanel;
    private JButton editContactButton;
    private JButton deleteContactButton;
    private JLabel nameLabel;
    private JTextField emailField;
    private JSpinner birthdaySpinner;
    private JSpinner genderSpinner;
    private JFormattedTextField mobileField;
    private JFormattedTextField homeField;
    private JTextArea addressArea;
    private JTextField searchField;
    private JButton createContactButton;
    private JList<Contact> contactList;

    private void createUIComponents() {
        //Contact list
        contactModel = new DefaultListModel<>();
        contactList = new JList<>(contactModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Gender spinner
        genderModel = new CyclingSpinnerListModel(new String[] {"Male", "Female"});
        genderSpinner = new JSpinner(genderModel);

        //Birthday spinner
        dateModel = new SpinnerDateModel();
        birthdaySpinner = new JSpinner(dateModel);
        birthdaySpinner.setEditor(new JSpinner.DateEditor(birthdaySpinner, "MM/dd/yyyy"));

        //Mobile and Home fields
        try {
            mobileField = new JFormattedTextField(new MaskFormatter("###-###-####"));
            homeField = new JFormattedTextField(new MaskFormatter("###-###-####"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ContactPanel() {
        //Initialization
        contactManager = new ListManager<Contact>(this, "contacts.dat", Contact::generateAge);
        ChangeListener changeListener = new ChangeListener();

        //Add listeners
        //TextFields
        emailField.addKeyListener(changeListener);
        mobileField.addKeyListener(changeListener);
        homeField.addKeyListener(changeListener);
        addressArea.addKeyListener(changeListener);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                contactManager.search((contact) -> !contact.toString().contains(searchField.getText()) && !contact.toString().contains(searchField.getText()));
            }
        });

        //Spinners
        genderSpinner.addChangeListener(changeListener);
        birthdaySpinner.addChangeListener(changeListener);

        //Buttons
        createContactButton.addActionListener(actionEvent -> new CreateContactForm(contactManager));
        editContactButton.addActionListener(actionEvent -> setContactInfo());
        deleteContactButton.addActionListener(actionEvent -> contactManager.remove(contactList.getSelectedValue()));

        //Lists
        contactList.addListSelectionListener(listSelectionEvent -> {
            currentContact = contactList.getSelectedValue();
            setContactDescriptions();
        });
    }

    private void setContactInfo() {
        currentContact.setGender((String)genderModel.getValue());
        currentContact.setBirthday((Date)dateModel.getValue());
        currentContact.setEmail(emailField.getText());
        currentContact.setMobile(mobileField.getText());
        currentContact.setHome(homeField.getText());
        currentContact.setAddress(addressArea.getText());
        editContactButton.setEnabled(false);
        setContactDescriptions();
    }

    private void setContactDescriptions() {
        try {
            nameLabel.setText(currentContact.toString() + " (" + currentContact.getAge() + ")");
            genderModel.setValue(currentContact.getGender());
            dateModel.setValue(currentContact.getBirthday());
            emailField.setText(currentContact.getEmail());
            if (!currentContact.getMobile().equals("   -   -    ")) mobileField.setText(currentContact.getMobile());
            else mobileField.setText(null);
            if (!currentContact.getHome().equals("   -   -    ")) homeField.setText(currentContact.getHome());
            else homeField.setText(null);
            addressArea.setText(currentContact.getAddress());

            //Enable components to be edited
            genderSpinner.setEnabled(true);
            birthdaySpinner.setEnabled(true);
            emailField.setEnabled(true);
            mobileField.setEnabled(true);
            homeField.setEnabled(true);
            addressArea.setEnabled(true);
            editContactButton.setEnabled(false);
            deleteContactButton.setEnabled(true);
        }
        catch (NullPointerException e) {
            nameLabel.setText("Name");
            emailField.setText(null);
            mobileField.setText(null);
            homeField.setText(null);
            addressArea.setText(null);

            //Disable components to be edited
            genderSpinner.setEnabled(false);
            birthdaySpinner.setEnabled(false);
            emailField.setEnabled(false);
            mobileField.setEnabled(false);
            homeField.setEnabled(false);
            addressArea.setEnabled(false);
            deleteContactButton.setEnabled(false);
        }
    }

    public ListManager<Contact> getContactManager() {
        return contactManager;
    }

    public void saveContacts() {
        contactManager.save();
    }

    public JPanel getPanel() {
        return contactPanel;
    }

    public DefaultListModel<Contact> getModel() {
        return contactModel;
    }

    //Generic listener for JFields and JSpinners that check whether saveContactsButton should be enabled
    private class ChangeListener extends KeyAdapter implements javax.swing.event.ChangeListener {

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            getTests();
            if (getTests())
                editContactButton.setEnabled(false);
            else editContactButton.setEnabled(true);
        }

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            getTests();
            if (getTests())
                editContactButton.setEnabled(false);
            else editContactButton.setEnabled(true);
        }

        //Tests whether the contact information has been edited
        private boolean getTests() {
            boolean genderTest = genderSpinner.getValue().equals(currentContact.getGender());
            boolean birthdayTest = dateModel.getValue().equals(currentContact.getBirthday());
            boolean emailTest = emailField.getText().equals(currentContact.getEmail());
            boolean mobileTest = mobileField.getText().equals(currentContact.getMobile());
            boolean homeTest = homeField.getText().equals(currentContact.getHome());
            boolean addressTest = addressArea.getText().equals(currentContact.getAddress());
            return genderTest && birthdayTest && emailTest && mobileTest && homeTest && addressTest;
        }
    }
}
