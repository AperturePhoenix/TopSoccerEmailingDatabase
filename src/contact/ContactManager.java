package contact;

import core.FileManager;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Lance Judan on 8/23/17
 */
public class ContactManager {
    //Instance variables
    private ArrayList<Contact> contacts;
    private ContactPanel contactPanel;

    public ContactManager(ContactPanel contactPanel) {
        this.contactPanel = contactPanel;
        load();
    }

    private void load() {
        Serializable input = FileManager.loadFile("contacts.dat");
        contacts = input != null ? (ArrayList<Contact>)input : new ArrayList<>();
        if (!contacts.isEmpty()) sort();
        contacts.forEach(contact -> {
            contact.generateAge();
            contactPanel.getContactModel().addElement(contact);
        });
    }

    public void save() {
        FileManager.saveFile(contacts, "contacts.dat");
    }

    private void sort() {
        contacts.sort(Contact::compareTo);
    }

    //Finds the index where a contact should be inserted to maintain alphabetical order
    private int findInsertionPoint(Contact contact) {
        int insertionPoint = Collections.binarySearch(contacts, contact);
        if (insertionPoint < 0) insertionPoint = -(insertionPoint + 1);
        return insertionPoint;
    }

    public void addContact(Contact contact) {
        int insertionPoint = findInsertionPoint(contact);
        contacts.add(insertionPoint, contact);
        contactPanel.getContactModel().add(insertionPoint, contact); //Add contact from JList
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contactPanel.getContactModel().removeElement(contact); //Remove contact from JList
    }

    //Adds and removes contacts from JList based on search term
    //Only searches name and email
    public void searchContact(String search) {
        DefaultListModel<Contact> model = contactPanel.getContactModel();
        //Ensures that all contacts are in the JList
        contacts.forEach(contact -> {
            if (!model.contains(contact)) model.add(findInsertionPoint(contact), contact);
        });
        if (search == null || search.equals("") || search.equals(" ")) return; //Returns if search term is blank or empty
        //Creates an ArrayList of contacts to be removed from the JList
        ArrayList<Contact> removeContactList = (ArrayList<Contact>) contacts
                .stream()
                .filter(contact -> !contact.toString().toLowerCase().contains(search.toLowerCase()) && !contact.getEmail().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        removeContactList.forEach(contact -> {
            if (model.contains(contact)) model.removeElement(contact);
        });
    }
}
