package category;

import core.ListManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class CreateCategoryForm implements ActionListener{
    //Instance variables
    private ListManager<String> manager;

    //JComponents
    private JTextField categoryField;
    private JList contactList;
    private JButton createButton;
    private JPanel contentPane;
    private JFrame frame;

    public CreateCategoryForm(ListManager<String> manager) {
        this.manager = manager;

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
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
