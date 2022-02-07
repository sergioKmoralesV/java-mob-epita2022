package fr.epita.mnist.services;

import fr.epita.mnist.datamodel.MNISTImage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MNISTClassifier {
    Map<Double, MNISTImage> centroids;

    public MNISTClassifier() {
        this.centroids = new LinkedHashMap();
    }

    public Map<Double, MNISTImage> getCentroids() {
        return centroids;
    }

    public void setCentroids(Map<Double, MNISTImage> centroids) {
        this.centroids = centroids;
    }

    public void trainCentroids (List<MNISTImage> images) {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        //We are processing the images and then separating by label
        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));

        for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
            Double label = entry.getKey();
            List<MNISTImage> imageList = entry.getValue();

            MNISTImage centroid = processor.getCentroid(label, imageList);
            this.centroids.put(centroid.getLabel(), centroid);
//            System.out.println(centroid);
        }
    }

    public void trainCentroidsStandardDeviation (List<MNISTImage> images) {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        //We are processing the images and then separating by label
        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));

        for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
            Double label = entry.getKey();
            List<MNISTImage> imageList = entry.getValue();

            MNISTImage centroid = processor.getCentroidStandardDeviation(label, imageList);
            this.centroids.put(centroid.getLabel(), centroid);
//            System.out.println(centroid);
        }
    }

    public double predict(MNISTImage image) {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        double smallestDistance = processor.getDistance(image, this.centroids.get(0.0));
        double predictedLabel = 0.0;

        for (double i = 1.0; i < this.centroids.size(); i++) {
            double partialDistance = processor.getDistance(image, this.centroids.get(i));
            if(partialDistance < smallestDistance) {
                smallestDistance = partialDistance;
                predictedLabel = i;
            }
        }
        return predictedLabel;
    }
}
