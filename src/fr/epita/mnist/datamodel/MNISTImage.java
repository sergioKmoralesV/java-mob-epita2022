package fr.epita.mnist.datamodel;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "MNISTImage - Label: " + label +
                "\nPixels: \n" + getStringMatrix();
    }

    public String getStringMatrix() {
        StringBuilder result = new StringBuilder();
        double[][] pixels = this.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] < 100) {
                    result.append("..");
                } else {
                    result.append("xx");
                }
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }

    public String getStringNumbersMatrix() {
        StringBuilder result = new StringBuilder();
        double[][] pixels = this.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                result.append(pixels[i][j] + "\t");
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }
}
