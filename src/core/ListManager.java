package core;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Lance Judan on 9/6/17
 */
public class ListManager<T extends Comparable<? super T>>{
    //Instance variables
    private ArrayList<T> arrayList;
    private ListPanel panel;
    private String fileName;

    public ListManager(ListPanel panel, String fileName, Consumer<T> loadConsumer) {
        this.panel = panel;
        this.fileName = fileName;
        load(loadConsumer);
    }

    private void load(Consumer<T> loadConsumer) {
        Serializable input = FileManager.loadFile(fileName);
        arrayList = input != null ? (ArrayList<T>)input : new ArrayList<>();
        if (!arrayList.isEmpty()) arrayList.sort(T::compareTo);
        arrayList.forEach(object -> {
            if (loadConsumer != null) loadConsumer.accept(object);
            panel.getModel().addElement(object);
        });
    }

    public void save() {
        FileManager.saveFile(arrayList, fileName);
    }

    //Finds the index where object should be inserted to maintain alphabetical order
    private int findInsertionPoint(T object) {
        int insertionPoint = Collections.binarySearch(arrayList, object);
        if (insertionPoint < 0) insertionPoint = -(insertionPoint + 1);
        return insertionPoint;
    }

    public void add(T object) {
        int insertionPoint = findInsertionPoint(object);
        arrayList.add(insertionPoint, object);
        panel.getModel().add(insertionPoint, object); //Add object to JList
    }

    public void remove(T object) {
        arrayList.remove(object);
        panel.getModel().removeElement(object); //Remove object from JList
    }

    //Adds and removes objects from JList based on search term
    public void search(Predicate<T> searchPredicate) {
        DefaultListModel<T> model = panel.getModel();
        //Ensures that all objects are in JList
        if (searchPredicate == null) return;
        //Creates an ArrayList of contacts to be removed from JList
        ArrayList<T> removalList = (ArrayList<T>) arrayList
                .stream()
                .filter(searchPredicate)
                .collect(Collectors.toList());
        removalList.forEach(object -> {
            if (model.contains(object)) model.removeElement(object);
        });
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public interface ListPanel {
        DefaultListModel getModel();
    }
}