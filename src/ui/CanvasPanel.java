package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CanvasPanel extends JPanel {

    private Room room;
    private ArrayList<FurnitureItem> items = new ArrayList<>();
    private FurnitureItem selectedItem = null;
    private int dragOffsetX;
    private int dragOffsetY;

    public CanvasPanel(Room room) {
        this.room = room;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectItem(e.getX(), e.getY());
                if (selectedItem != null) {
                    dragOffsetX = e.getX() - selectedItem.getX();
                    dragOffsetY = e.getY() - selectedItem.getY();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedItem != null) {
                    selectedItem.setPosition(e.getX() - dragOffsetX, e.getY() - dragOffsetY);
                    repaint();
                }
            }
        });
    }

    public void addChair() {
        items.add(new FurnitureItem(100, 100, 40, 40, Color.RED));
        repaint();
    }

    public void addTable() {
        items.add(new FurnitureItem(200, 150, 80, 50, Color.BLUE));
        repaint();
    }

    public void rotateSelected() {
        if (selectedItem != null) {
            selectedItem.rotate();
            repaint();
        }
    }

    public void deleteSelected() {
        if (selectedItem != null) {
            items.remove(selectedItem);
            selectedItem = null;
            repaint();
        }
    }

    public ArrayList<FurnitureItem> getItemsSnapshot() {
        return new ArrayList<>(items);
    }

    private void selectItem(int x, int y) {
        for (FurnitureItem item : items) {
            if (item.contains(x, y)) {
                selectedItem = item;
                repaint();
                return;
            }
        }
        selectedItem = null;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawRoom(g2);

        for (FurnitureItem item : items) {
            item.draw(g2);
        }
    }

    private void drawRoom(Graphics2D g2) {

        g2.setColor(Color.LIGHT_GRAY);

        int startX = 250;
        int startY = 80;

        int width = (int) room.getWidth() * 5;
        int height = (int) room.getHeight() * 5;

        if (room.getShape().equals("Rectangle") || room.getShape().equals("Square")) {

            g2.drawRect(startX, startY, width, height);

        } else if (room.getShape().equals("L-Shaped")) {

            int extW = (int) room.getExtensionWidth() * 5;
            int extH = (int) room.getExtensionHeight() * 5;

            g2.drawRect(startX, startY, width, height);
            g2.drawRect(startX + width - extW, startY + height, extW, extH);
        }
    }
}

