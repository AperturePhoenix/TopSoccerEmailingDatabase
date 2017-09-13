package category;

import contact.Contact;
import core.ListManager;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Lance Judan on 9/3/17
 */
public class CategoryPanel implements ListManager.ListPanel {
    //Instance variables
    private String currentCategory;
    private ListManager<String> categoryManager;
    private DefaultListModel<String> categoryModel;
    private DefaultListModel<Contact> contactModel;
    private ListManager<Contact> contactManager;

    //JComponents
    private JPanel categoryPanel;
    private JList<String> categoryList;
    private JButton createCategoryButton;
    private JList<Contact> contactList;
    private JButton editCategoryButton;
    private JButton deleteCategoryButton;
    private JTextField searchField;
    private JButton emailButton;

    public CategoryPanel(ListManager<Contact> contactManager) {
        //Initialization
        categoryManager = new ListManager<>(this, "categories.dat", null);
        this.contactManager = contactManager;

        //Add listeners
        //TextFields
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                categoryManager.search((category) -> !category.contains(searchField.getText()));
            }
        });

        //Buttons
        createCategoryButton.addActionListener(actionEvent -> new CreateCategoryForm(null, this));
        editCategoryButton.addActionListener(actionEvent -> setCategoryInfo());
        deleteCategoryButton.addActionListener(actionEvent -> {
            contactManager.getArrayList().forEach(contact -> contact.removeCategory(categoryList.getSelectedValue()));
            categoryManager.remove(categoryList.getSelectedValue());
        });
        emailButton.addActionListener(actionEvent -> {
            ArrayList<String> temp = (ArrayList<String>) categoryList.getSelectedValuesList();
            if (!temp.isEmpty()) new EmailDialog(temp, contactManager.getArrayList());
        });

        //Lists
        categoryList.addListSelectionListener(listSelectionEvent -> {
            currentCategory = categoryList.getSelectedValue();
            setContactList();
        });
    }

    private void createUIComponents() {
        //Category list
        categoryModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryModel);
        categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Contact list
        contactModel = new DefaultListModel<>();
        contactList = new JList<>(contactModel);
        contactList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void setCategoryInfo() {
        CreateCategoryForm categoryForm = new CreateCategoryForm(currentCategory, this);
    }

    public void setContactList() {
        contactModel.removeAllElements();
        ArrayList<Contact> addList = (ArrayList<Contact>) contactManager.getArrayList()
                .stream()
                .filter(contact -> contact.isInCategory(currentCategory))
                .collect(Collectors.toList());
        addList.forEach(contactModel::addElement);
    }

    public void saveCategories() {
        categoryManager.save();
    }

    public DefaultListModel<String> getModel() {
        return categoryModel;
    }

    public JPanel getPanel() {
        return categoryPanel;
    }

    public ListManager<Contact> getContactManager() {
        return contactManager;
    }

    public ListManager<String> getCategoryManager() {
        return categoryManager;
    }
}
