package category;

import contact.Contact;
import core.FileManager;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CategoryManager {
    //Instance variables
    private ArrayList<String> categories;
    private CategoryPanel categoryPanel;

    public CategoryManager(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
        load();
    }

    private void load() {
        Serializable input = FileManager.loadFile("categories.dat");
        categories = input != null ? (ArrayList<String>)input : new ArrayList<>();
        if (!categories.isEmpty()) sort();
        categories.forEach(categoryPanel.getCategoryModel()::addElement);
    }

    public void save() { FileManager.saveFile(categories, "categories.dat");}

    private void sort() {
        categories.sort(String::compareToIgnoreCase);
    }

    //Finds the index where a category should be inserted to maintain alphabetical order
    private int findInsertionPoint(String category) {
        int insertionPoint = Collections.binarySearch(categories, category);
        if (insertionPoint < 0) insertionPoint = -(insertionPoint + 1);
        return insertionPoint;
    }

    private void addCategory(String category) {
        int insertionPoint = findInsertionPoint(category);
        categories.add(insertionPoint, category);
        categoryPanel.getCategoryModel().addElement(category);
    }

    private void removeCategory(String category) {
        categories.remove(category);
        categoryPanel.getCategoryModel().removeElement(category);
    }

    //Adds and removes categories from JList based on search term
    public void searchCategory(String search) {
        DefaultListModel<String> model = categoryPanel.getCategoryModel();
        //Ensures that all contacts are in the JList
    }
}
