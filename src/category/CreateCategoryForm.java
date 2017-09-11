package category;

import contact.Contact;
import core.ListManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class CreateCategoryForm implements ActionListener{
    //Instance variables
    private ListManager<String> manager;
    private DefaultListModel<Contact> contactModel;

    //JComponents
    private JTextField categoryField;
    private JList<Contact> contactList;
    private JButton createButton;
    private JPanel contentPane;
    private JFrame frame;

    public CreateCategoryForm(ListManager<String> manager, ListManager<Contact> contactListManager) {
        this.manager = manager;
        contactListManager.getArrayList().forEach(contactModel::addElement);

        //Add listeners
        createButton.addActionListener(this);

        //Create window
        frame = new JFrame("Create Category");
        frame.setContentPane(contentPane);
        frame.setPreferredSize(new Dimension(300, 275));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Adds the category to the manager and closes the window
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        manager.add(categoryField.getText());
        contactList.getSelectedValuesList().forEach(contact -> contact.addCategory(categoryField.getText()));
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void createUIComponents() {
        contactModel = new DefaultListModel<>();
        contactList = new JList<>(contactModel);
        contactList.setSelectionModel(new DefaultListSelectionModel() {
            boolean gestureStarted = false;

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (!gestureStarted) {
                    if (isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index1);
                    } else {
                        super.addSelectionInterval(index0, index1);
                    }
                }
                gestureStarted = true;
            }

            @Override
            public void setValueIsAdjusting(boolean isAdjusting) {
                if (isAdjusting == false) {
                    gestureStarted = false;
                }
            }
        });
    }
}
