package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View3DFrame extends JFrame {

    private final Room room;
    private final List<FurnitureItem> furnitureItems;

    public View3DFrame(Room room, List<FurnitureItem> furnitureItems) {
        this.room = room;
        this.furnitureItems = new ArrayList<>(furnitureItems);

        setTitle("3D View");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new Render3DPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private class Render3DPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(245, 245, 245));
            g2.fillRect(0, 0, getWidth(), getHeight());

            int baseX = 260;
            int baseY = 440;
            int roomW = Math.max(180, (int) room.getWidth() * 6);
            int roomD = Math.max(120, (int) room.getHeight() * 4);
            int wallH = 160;
            int perspective = 70;
            int room2DStartX = 250;
            int room2DStartY = 80;
            int room2DWidth = Math.max(1, (int) room.getWidth() * 5);
            int room2DHeight = Math.max(1, (int) room.getHeight() * 5);

            Polygon floor = new Polygon(
                    new int[]{baseX, baseX + roomW, baseX + roomW + perspective, baseX + perspective},
                    new int[]{baseY, baseY, baseY - roomD, baseY - roomD},
                    4
            );

            Polygon leftWall = new Polygon(
                    new int[]{baseX, baseX + perspective, baseX + perspective, baseX},
                    new int[]{baseY, baseY - roomD, baseY - roomD - wallH, baseY - wallH},
                    4
            );

            Polygon rightWall = new Polygon(
                    new int[]{baseX + roomW, baseX + roomW + perspective, baseX + roomW + perspective, baseX + roomW},
                    new int[]{baseY, baseY - roomD, baseY - roomD - wallH, baseY - wallH},
                    4
            );

            Color roomColor = mapRoomColor(room.getColor());

            g2.setColor(roomColor.darker());
            g2.fillPolygon(leftWall);
            g2.setColor(roomColor);
            g2.fillPolygon(rightWall);
            g2.setColor(roomColor.brighter());
            g2.fillPolygon(floor);

            g2.setColor(Color.DARK_GRAY);
            g2.drawPolygon(leftWall);
            g2.drawPolygon(rightWall);
            g2.drawPolygon(floor);

            drawFurniture3D(g2, baseX, baseY, roomW, roomD, perspective, room2DStartX, room2DStartY, room2DWidth, room2DHeight);

            if ("L-Shaped".equals(room.getShape())) {
                int extW = Math.max(60, (int) room.getExtensionWidth() * 5);
                int extD = Math.max(40, (int) room.getExtensionHeight() * 4);

                Polygon extFloor = new Polygon(
                        new int[]{baseX + roomW - extW, baseX + roomW, baseX + roomW + perspective, baseX + roomW - extW + perspective},
                        new int[]{baseY + extD, baseY + extD, baseY + extD - extD, baseY + extD - extD},
                        4
                );

                g2.setColor(roomColor.brighter());
                g2.fillPolygon(extFloor);
                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(extFloor);
            }

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("3D Room Preview", 30, 40);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.drawString("Shape: " + room.getShape(), 30, 70);
            g2.drawString("Width: " + room.getWidth() + " m", 30, 95);
            g2.drawString("Height: " + room.getHeight() + " m", 30, 120);
            if ("L-Shaped".equals(room.getShape())) {
                g2.drawString("Extension: " + room.getExtensionWidth() + "m x " + room.getExtensionHeight() + "m", 30, 145);
            }
        }

        private Color mapRoomColor(String colorName) {
            if (colorName == null) return new Color(210, 210, 210);
            switch (colorName) {
                case "White":
                    return new Color(240, 240, 240);
                case "Light Gray":
                    return new Color(200, 200, 200);
                case "Beige":
                    return new Color(220, 205, 175);
                case "Blue":
                    return new Color(155, 185, 220);
                default:
                    return new Color(210, 210, 210);
            }
        }

        private void drawFurniture3D(Graphics2D g2, int baseX, int baseY, int roomW, int roomD, int perspective,
                                     int room2DStartX, int room2DStartY, int room2DWidth, int room2DHeight) {
            for (FurnitureItem item : furnitureItems) {
                double nx = (item.getX() - room2DStartX) / (double) room2DWidth;
                double ny = (item.getY() - room2DStartY) / (double) room2DHeight;
                nx = Math.max(0, Math.min(1, nx));
                ny = Math.max(0, Math.min(1, ny));

                double nw = item.getWidth() / (double) room2DWidth;
                double nd = item.getHeight() / (double) room2DHeight;

                int x1 = (int) (baseX + nx * roomW + ny * perspective);
                int y1 = (int) (baseY - ny * roomD);
                int width = Math.max(8, (int) (nw * roomW));
                int depth = Math.max(6, (int) (nd * roomD));
                int depthSkew = (int) (depth * (perspective / (double) roomD));
                int blockH = 18;

                Polygon top = new Polygon(
                        new int[]{x1, x1 + width, x1 + width + depthSkew, x1 + depthSkew},
                        new int[]{y1, y1, y1 - depth, y1 - depth},
                        4
                );
                Polygon front = new Polygon(
                        new int[]{x1, x1 + width, x1 + width, x1},
                        new int[]{y1, y1, y1 + blockH, y1 + blockH},
                        4
                );
                Polygon side = new Polygon(
                        new int[]{x1 + width, x1 + width + depthSkew, x1 + width + depthSkew, x1 + width},
                        new int[]{y1, y1 - depth, y1 - depth + blockH, y1 + blockH},
                        4
                );

                Color base = item.getColor();
                g2.setColor(base.brighter());
                g2.fillPolygon(top);
                g2.setColor(base);
                g2.fillPolygon(front);
                g2.setColor(base.darker());
                g2.fillPolygon(side);

                g2.setColor(Color.DARK_GRAY);
                g2.drawPolygon(top);
                g2.drawPolygon(front);
                g2.drawPolygon(side);
            }
        }
    }
}
