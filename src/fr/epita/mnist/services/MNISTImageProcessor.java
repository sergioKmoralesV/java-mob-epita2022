package fr.epita.mnist.services;

import fr.epita.mnist.datamodel.MNISTImage;

import java.util.List;

public class MNISTImageProcessor {
    public MNISTImage getCentroid(Double label, List<MNISTImage> images) {
        MNISTImage centroid = new MNISTImage(label, new double[28][28]);
        double[][] centroidPixels = centroid.getPixels();
        int totalImages = images.size();

        for (MNISTImage image : images) {
            double[][] currentPixels = image.getPixels();
            for (int i = 0; i < currentPixels.length; i++) {
                for (int j = 0; j < currentPixels[i].length; j++) {
                    centroidPixels[i][j] = centroidPixels[i][j] + currentPixels[i][j] / totalImages;
                }
            }
        }

        return centroid;
    }

    public double getDistance(MNISTImage a, MNISTImage b) {
        double[][] pixelsA = a.getPixels();
        double[][] pixelsB = b.getPixels();

        double distance = 0.0;

        for (int i = 0; i < MNISTReader.MAX_COL; i++) {
            for (int j = 0; j < MNISTReader.MAX_ROW; j++) {
                /*
                 * Here I used a different formula from what we did in the class:
                 *  class formula was: distance = distance + Math.sqrt(Math.pow(image2Pixels[i][j] - image1Pixels[i][j],2));
                 * This means we are adding sqrt((a[0][0] - b[0][0])^2) and that means we are only adding a[0][0] - b[0][0]
                 *
                 * Also because square root is not distributive in a sum. So sqrt(a+b) != to sqrt(a) + sqrt(b)
                 *
                 * The square root should be done at the end of the whole sum.
                 */

                distance += Math.pow(pixelsA[i][j] - pixelsB[i][j], 2);
            }
        }

        return Math.sqrt(distance);
    }
}
