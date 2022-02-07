package fr.epita.mnist.services;

import fr.epita.mnist.datamodel.MNISTImage;

import java.util.List;

public class MNISTImageProcessor {

    public MNISTImage getCentroid(Double label, List<MNISTImage> images) {
        MNISTImage centroid = new MNISTImage(label, new double[28][28]);
        double[][] centroidPixels = centroid.getPixels();
        int totalImages = images.size();

        for(MNISTImage image : images) {
            double[][] currentPixels = image.getPixels();
            for(int i = 0; i < currentPixels.length; i++) {
                for(int j = 0; j < currentPixels[i].length; j++) {
                    centroidPixels[i][j] = centroidPixels[i][j] + currentPixels[i][j] / totalImages;
                }
            }
        }

        return centroid;
    }

}
