package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class FurnitureItem {

    private int x, y, width, height;
    private double angle = 0;
    private Color color;

    public FurnitureItem(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void rotate() {
        angle += Math.toRadians(15);
    }

    public boolean contains(int mx, int my) {
        return new Rectangle(x, y, width, height).contains(mx, my);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw(Graphics2D g2) {

        AffineTransform old = g2.getTransform();

        g2.rotate(angle, x + width / 2, y + height / 2);
        g2.setColor(color);
        g2.fillRect(x, y, width, height);

        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);

        g2.setTransform(old);
    }
}

