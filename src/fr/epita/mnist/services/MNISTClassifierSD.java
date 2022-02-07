package fr.epita.mnist.services;

import fr.epita.mnist.datamodel.MNISTImage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MNISTClassifierSD {
    Map<Double, MNISTImage> meanCentroids;
    Map<Double, MNISTImage> sdCentroids;

    public Map<Double, MNISTImage> getMeanCentroids() {
        return meanCentroids;
    }

    public void setMeanCentroids(Map<Double, MNISTImage> meanCentroids) {
        this.meanCentroids = meanCentroids;
    }

    public Map<Double, MNISTImage> getSdCentroids() {
        return sdCentroids;
    }

    public void setSdCentroids(Map<Double, MNISTImage> sdCentroids) {
        this.sdCentroids = sdCentroids;
    }

    public MNISTClassifierSD() {
        this.meanCentroids = new LinkedHashMap();
        this.sdCentroids = new LinkedHashMap();

    }

    public void trainCentroidsStandardDeviation (List<MNISTImage> images) {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        //We are processing the images and then separating by label
        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));

        for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
            Double label = entry.getKey();
            List<MNISTImage> imageList = entry.getValue();

            MNISTImage avCentroid = processor.getCentroid(label, imageList);
            this.meanCentroids.put(avCentroid.getLabel(), avCentroid);

            MNISTImage sdCentroid = processor.getCentroidStandardDeviation(label, imageList);
            this.sdCentroids.put(sdCentroid.getLabel(), sdCentroid);
//            System.out.println(centroid);
        }
    }

    public double predict(MNISTImage image) {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        double smallestDistance = processor.getDistanceWithSD(image, this.meanCentroids.get(0.0),  this.getSdCentroids().get(0.0));
        double predictedLabel = 0.0;

        for (double i = 1.0; i < this.meanCentroids.size(); i++) {
            double partialDistance = processor.getDistanceWithSD(image, this.meanCentroids.get(i), this.getSdCentroids().get(i));
            if(partialDistance < smallestDistance) {
                smallestDistance = partialDistance;
                predictedLabel = i;
            }
        }
        return predictedLabel;
    }
}