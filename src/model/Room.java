package model;

public class Room {

    private String shape;
    private double width;
    private double height;
    private String color;

    private double extensionWidth;
    private double extensionHeight;

    public Room(String shape, double width, double height,
                String color, double extensionWidth, double extensionHeight) {

        this.shape = shape;
        this.width = width;
        this.height = height;
        this.color = color;
        this.extensionWidth = extensionWidth;
        this.extensionHeight = extensionHeight;
    }

    public String getShape() { return shape; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public String getColor() { return color; }
    public double getExtensionWidth() { return extensionWidth; }
    public double getExtensionHeight() { return extensionHeight; }
}
