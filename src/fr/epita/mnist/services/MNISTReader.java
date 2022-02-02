package fr.epita.mnist.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MNISTReader {
    public double[][] loadLines(File file, int maxLines) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int counter = 0;
        List<Object> matrixAsList = new ArrayList<>();

        while (scanner.hasNext() && counter < maxLines) {
            String line = scanner.nextLine();
            double[] lineAsDoubleArray = loadLine(line);
            counter++;
        }

        double[][] matrix = new double[counter][];
        for (int i = 0; i < counter; i++) {
            matrix[i] = ((double[]) matrixAsList.get(i));
        }

        return matrix;
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
