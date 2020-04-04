package biomage.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class FilterChooserGUI {

    private JList list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    DefaultListModel listModel;
    List<String> contents;
    File file = new File("filters.txt");

    public FilterChooserGUI(String message, JList listToDisplay) {

        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();

    }

    public FilterChooserGUI(String title, String message, JList listToDisplay) {
        this(message, listToDisplay);
        dialog.setTitle(title);
    }

    public FilterChooserGUI(String message) {
        try {
            contents = FileUtils.readLines(file, "UTF-8");
            listModel = new DefaultListModel();

            for (String line : contents) {
                listModel.addElement(line);
            }

            list = new JList(listModel);
            label = new JLabel(message);
            createAndDisplayOptionPane();

        } catch (IOException ex) {
            Logger.getLogger(FilterChooserGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createAndDisplayOptionPane() {
        setupButtons();

        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Select option");
       
    }

    private void setupButtons() {
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents() {
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }

    public void setOnClose(ActionListener event) {
        cancelEvent = event;
    }

    private void handleOkButtonClick(ActionEvent e) {
        if (okEvent != null) {
            okEvent.actionPerformed(e);
        }
        hide();
    }

    private void handleCancelButtonClick(ActionEvent e) {
        if (cancelEvent != null) {
            cancelEvent.actionPerformed(e);
        }
        hide();
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void hide() {
        dialog.setVisible(false);
    }

    public Object getSelectedItem() {
        return list.getSelectedValue();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                FilterChooserGUI dialog = new FilterChooserGUI("Please select an item in the list: ");
                dialog.setOnOk(e -> System.out.println("You chose: " + dialog.getSelectedItem()));
                dialog.show();
            }
        });
    }
}
