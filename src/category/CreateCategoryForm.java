package category;

import contact.Contact;
import core.ListManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CreateCategoryForm implements ActionListener{
    //Instance variables
    private CategoryPanel panel;
    private ListManager<Contact> contactManager;
    private ListManager<String> manager;
    private DefaultListModel<Contact> contactModel;

    //One-way flag
    private boolean isDone = false;

    //JComponents
    private JTextField categoryField;
    private JList<Contact> contactList;
    private JButton createButton;
    private JPanel contentPane;
    private JFrame frame;

    public CreateCategoryForm(String categoryName, CategoryPanel panel) {
        //Initialize
        categoryField.setText(categoryName);
        this.panel = panel;
        this.manager = panel.getCategoryManager();
        this.contactManager = panel.getContactManager();
        ArrayList<Contact> contactArrayList = contactManager.getArrayList();
        ArrayList<Integer> selectionIndices = new ArrayList<>();
        for (int i = 0; i < contactArrayList.size(); i++) {
            Contact contact = contactArrayList.get(i);
            contactModel.addElement(contact);
            if (categoryName != null && contact.isInCategory(categoryName)) selectionIndices.add(i);
        }
        if (!selectionIndices.isEmpty()) {
            int[] selection = new int[selectionIndices.size()];
            for (int i = 0; i < selection.length; i++)
                selection[i] = selectionIndices.get(i);
            contactList.setSelectedIndices(selection);
        }
        String title = "Create Category";
        if (categoryName != null) {
            title = "Edit " + categoryName;
            createButton.setText("Edit");
            categoryField.setEnabled(false);
        }

        //Add listeners
        createButton.addActionListener(this);

        //Create window
        frame = new JFrame(title);
        frame.setContentPane(contentPane);
        frame.setPreferredSize(new Dimension(300, 275));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Adds the category to the manager and closes the window
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!manager.getArrayList().contains(categoryField.getText())) manager.add(categoryField.getText());
        contactManager.getArrayList().forEach(contact -> contact.removeCategory(categoryField.getText()));
        contactList.getSelectedValuesList().forEach(contact -> contact.addCategory(categoryField.getText()));
        panel.setContactList();
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
                if (!isAdjusting) {
                    gestureStarted = false;
                }
            }
        });
    }
}
