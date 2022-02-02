package fr.epita.mnist.datamodel;

public class MNISTImage {
    double label;
    double[][] pixels = new double[28][28];

    public MNISTImage(double label, double[][] pixels) {
        this.label = label;
        this.pixels = pixels;
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(double label) {
        this.label = label;
    }

    public double[][] getPixels() {
        return pixels;
    }

    public void setPixels(double[][] pixels) {
        this.pixels = pixels;
    }
}
