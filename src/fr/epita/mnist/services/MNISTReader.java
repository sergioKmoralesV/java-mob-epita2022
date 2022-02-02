package fr.epita.mnist.services;

import fr.epita.mnist.datamodel.MNISTImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MNISTReader {
    public static final int MAX_COL = 28;
    public static final int MAX_ROW = 28;

    public List<MNISTImage> readImagesfromFile(File file, int maxLines) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int counter = 0;
        List<MNISTImage> images = new ArrayList<>();

        scanner.nextLine(); // we skip the headers
        while (scanner.hasNext() && counter < maxLines) {
            String line = scanner.nextLine();
            double[] lineAsDoubleArray = loadLine(line); // read a line and getting the double array
            double[][] pixels = new double[MAX_ROW][MAX_COL];

            for (int i = 0; i < MAX_ROW; i++) {
                for (int j = 0; j < MAX_COL; j++) {
                    pixels[i][j] = lineAsDoubleArray[i*MAX_ROW + j + 1];
                }
            }
            images.add(new MNISTImage(lineAsDoubleArray[0], pixels));
            counter++;
        }

        scanner.close();
        return images;
    }

    private static double[] loadLine(String sample) {
        String[] entries = sample.split(",");

        double[] entriesAsDoubles = new double[entries.length];

        for (int i = 0; i < entriesAsDoubles.length; i++) {
            entriesAsDoubles[i] = Double.parseDouble(entries[i]);
        }

        return entriesAsDoubles;
    }
}
