package fr.epita.mnist;

import fr.epita.mnist.datamodel.MNISTImage;
import fr.epita.mnist.services.MNISTImageProcessor;
import fr.epita.mnist.services.MNISTReader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MNISTImageProcessorTest {
    public static void main(String[] args) throws Exception {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        MNISTReader reader = new MNISTReader();
        List<MNISTImage> images = reader.readImagesfromFile("./mnist_test.csv", 100);

        //We are processing the images and then separating by label
        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));
        System.out.println("We have " + imagesByLabel.size() + " labels. \n--- Testing the centroids with the following amount of images per label ----------------------------------------");

        Map<Double, MNISTImage> centroids = new LinkedHashMap<>();
        //We compute the centroids for each group of images according to their label.
        for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
            Double label = entry.getKey();
            List<MNISTImage> imageList = entry.getValue();
            System.out.println(label + " count : " + imageList.size());
            MNISTImage centroid = processor.getCentroid(label, imageList);
            centroids.put(centroid.getLabel(), centroid);
            //System.out.println(centroid);
        }

        System.out.println("--- Testing the distance ----------------------------------------");
        List<MNISTImage> listOfOnes = imagesByLabel.get(0.0);
        //Here we are testing the computation of the distance
        System.out.println(processor.getDistance(centroids.get(0.0), listOfOnes.get(0)));
        System.out.println(processor.getDistance(centroids.get(0.0), listOfOnes.get(1)));

        System.out.println("--- Calculating the distribution ----------------------------------------");
        showMatrix(processor.calculateDistribution(centroids, images));

    }

    public static void showMatrix(double[][] matrix) { //We extract this method to be part of the Image model and simplify the output
        for(int i = 0; i < matrix.length; i++) {
            for(int j=0; j< matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t\t");
            }
            System.out.println("");
        }
    }
}