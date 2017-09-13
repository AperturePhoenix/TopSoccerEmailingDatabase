package contact;

import core.CyclingSpinnerListModel;
import core.ListManager;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Lance Judan on 8/23/17
 */
public class CreateContactForm implements ActionListener {
    //Instance variables
    private ListManager<Contact> manager;

    //JComponents
    private JPanel contentPane;
    private JButton createButton;
    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JSpinner birthdaySpinner;
    private JSpinner genderSpinner;
    private JFormattedTextField mobileField;
    private JFormattedTextField homeField;
    private JTextArea addressArea;

    CreateContactForm(ListManager<Contact> manager) {
        //Initialization
        this.manager = manager;

        //Add listeners
        nameField.addActionListener(this);
        emailField.addActionListener(this);
        mobileField.addActionListener(this);
        homeField.addActionListener(this);
        createButton.addActionListener(this);

        //Create window
        frame = new JFrame("Create Contact");
        frame.setContentPane(contentPane);
        frame.setPreferredSize(new Dimension(300, 275));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        //Gender spinner
        CyclingSpinnerListModel genderModel = new CyclingSpinnerListModel(new String[]{"Male", "Female"});
        genderSpinner = new JSpinner(genderModel);

        //Birthday spinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
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

    //Adds the contact to the manager and closes the window
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        manager.add(new Contact(nameField.getText(), (String) genderSpinner.getValue(),
                (Date) birthdaySpinner.getValue(), emailField.getText(), mobileField.getText(),
                homeField.getText(), addressArea.getText()));
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
