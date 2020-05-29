package biomage.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilterChooserGUI {

    private JList list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    DefaultListModel listModel;
    List<String> contents;
    final File folder = new File("./src/biomage/algorithm/filter/");

    public FilterChooserGUI(String message, JList listToDisplay) {

        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public FilterChooserGUI(String title, String message, JList listToDisplay) {
        this(message, listToDisplay);
        dialog.setTitle(title);
    }

    public void getFiles() {
        contents = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            contents.add(fileEntry.getName().toString().split("\\.", 2)[0]);

        }
    }

    public FilterChooserGUI(String message) {
        getFiles();
        listModel = new DefaultListModel();
        for (String line : contents) {
            listModel.addElement(line);
        }
        list = new JList(listModel);
        label = new JLabel(message);
        createAndDisplayOptionPane();

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

    public void close() {
        dialog.dispose();
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

}
