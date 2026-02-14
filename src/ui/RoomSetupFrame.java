package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomSetupFrame extends JFrame {

    private JComboBox<String> shapeBox;
    private JTextField widthField;
    private JTextField heightField;
    private JComboBox<String> colorBox;

    private JTextField extWidthField;
    private JTextField extHeightField;
    private JPanel extensionPanel;

    public RoomSetupFrame() {

        setTitle("Room Setup");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // -------- Room Shape --------
        mainPanel.add(new JLabel("Room Shape"));
        shapeBox = new JComboBox<>(new String[]{"Rectangle", "Square", "L-Shaped"});
        mainPanel.add(shapeBox);
        mainPanel.add(Box.createVerticalStrut(15));

        // -------- Width --------
        mainPanel.add(new JLabel("Room Width (meters)"));
        widthField = new JTextField();
        mainPanel.add(widthField);
        mainPanel.add(Box.createVerticalStrut(15));

        // -------- Height --------
        mainPanel.add(new JLabel("Room Height (meters)"));
        heightField = new JTextField();
        mainPanel.add(heightField);
        mainPanel.add(Box.createVerticalStrut(15));

        // -------- Room Color --------
        mainPanel.add(new JLabel("Room Color"));
        colorBox = new JComboBox<>(new String[]{"White", "Light Gray", "Beige", "Blue"});
        mainPanel.add(colorBox);
        mainPanel.add(Box.createVerticalStrut(20));

        // -------- Extension Panel (For L-Shape) --------
        extensionPanel = new JPanel();
        extensionPanel.setLayout(new BoxLayout(extensionPanel, BoxLayout.Y_AXIS));
        extensionPanel.setBorder(BorderFactory.createTitledBorder("Extension Dimensions"));

        extWidthField = new JTextField();
        extHeightField = new JTextField();

        extensionPanel.add(new JLabel("Extension Width (meters)"));
        extensionPanel.add(extWidthField);
        extensionPanel.add(Box.createVerticalStrut(10));
        extensionPanel.add(new JLabel("Extension Height (meters)"));
        extensionPanel.add(extHeightField);

        extensionPanel.setVisible(false); // Hidden by default
        mainPanel.add(extensionPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // -------- Continue Button --------
        JButton continueBtn = new JButton("Continue");
        mainPanel.add(continueBtn);

        add(mainPanel);

        // -------- Shape Selection Logic --------
        shapeBox.addActionListener(e -> {
            if (shapeBox.getSelectedItem().equals("L-Shaped")) {
                extensionPanel.setVisible(true);
            } else {
                extensionPanel.setVisible(false);
            }
            revalidate();
            repaint();
        });

        // -------- Continue Button Logic --------
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    double width = Double.parseDouble(widthField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    String shape = shapeBox.getSelectedItem().toString();
                    String color = colorBox.getSelectedItem().toString();

                    double extWidth = 0;
                    double extHeight = 0;

                    if (shape.equals("L-Shaped")) {
                        extWidth = Double.parseDouble(extWidthField.getText());
                        extHeight = Double.parseDouble(extHeightField.getText());
                    }

                    // Create Room Object
                    Room room = new Room(shape, width, height, color, extWidth, extHeight);

                    JOptionPane.showMessageDialog(null, "Room Created Successfully!");
                    new Editor2DFrame(room);

                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values!");
                }
            }
        });
UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));
UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 14));
UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));

        setVisible(true);
    }
}

