package core;

import category.CategoryPanel;
import contact.ContactPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Lance Judan on 8/23/17
 */
public class TopSoccerForm {
    private static String TITLE = "Top Soccer Emailing Database";
    private static Dimension SIZE = new Dimension(600, 350);

    //Instance variables
    private ContactPanel contactPanel;
    private CategoryPanel categoryPanel;

    //JComponents
    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private void createUIComponents() {
        tabbedPane = new JTabbedPane();
    }

    public static void main(String[] args) {
        new TopSoccerForm();
    }

    private TopSoccerForm() {
        //Initialization
        contactPanel = new ContactPanel();
        categoryPanel = new CategoryPanel();

        //Add panels
        tabbedPane.add("Contacts", contactPanel.getPanel());
        tabbedPane.add("Categories", categoryPanel.getPanel());

        //Create window
        JFrame frame = new JFrame(TITLE);
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            //Saves everything before closing
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                contactPanel.saveContacts();
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        frame.setPreferredSize(SIZE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
