package category;

import javax.swing.*;

/**
 * Created by Lance Judan on 9/3/17
 */
public class CategoryPanel {
    //Instance variables
    private DefaultListModel<String> categoryModel;

    //JComponents
    private JPanel categoryPanel;
    private JList categoryList;
    private JButton addCategoryButton;
    private JList contactList;
    private JButton sendMessageButton;
    private JButton deleteCategoryButton;
    private JTextField searchField;

    public JPanel getPanel() {
        return categoryPanel;
    }

    public DefaultListModel<String> getCategoryModel() {
        return categoryModel;
    }
}
