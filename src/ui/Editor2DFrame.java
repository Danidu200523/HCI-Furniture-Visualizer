package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Editor2DFrame extends JFrame {

    private Room room;
    private CanvasPanel canvas;

    public Editor2DFrame(Room room) {

        this.room = room;

        setTitle("2D View");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- Left Toolbar --------
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8, 1, 10, 10));
        leftPanel.setPreferredSize(new Dimension(180, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton addChair = new JButton("Add Chair");
        JButton addTable = new JButton("Add Table");
        JButton rotate = new JButton("Rotate");
        JButton switch3D = new JButton("Switch to 3D");
        JButton save = new JButton("Save Design");
        JButton delete = new JButton("Delete");

        leftPanel.add(addChair);
        leftPanel.add(addTable);
        leftPanel.add(rotate);
        leftPanel.add(switch3D);
        leftPanel.add(save);
        leftPanel.add(delete);

        add(leftPanel, BorderLayout.WEST);

        // -------- Canvas --------
        canvas = new CanvasPanel(room);
        add(canvas, BorderLayout.CENTER);

        // -------- Button Actions --------
        addChair.addActionListener(e -> canvas.addChair());
        addTable.addActionListener(e -> canvas.addTable());
        rotate.addActionListener(e -> canvas.rotateSelected());
        delete.addActionListener(e -> canvas.deleteSelected());

        switch3D.addActionListener(e -> new View3DFrame(room, canvas.getItemsSnapshot()));

        setVisible(true);
    }
}
