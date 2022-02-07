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

        Map<Double, MNISTImage> centroids = new LinkedHashMap<>();
        //We compute the centroids for each group of images according to their label.
        for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
            Double label = entry.getKey();
            List<MNISTImage> imageList = entry.getValue();
            System.out.println(label + " count : " + imageList.size());
            MNISTImage centroid = processor.getCentroid(label, imageList);
            centroids.put(centroid.getLabel(), centroid);
            System.out.println(centroid);
        }
    }

//        System.out.println(processor.computeDistance(listOfOnes.get(0), centroidFor1));
//        System.out.println(processor.computeDistance(listOfZeros.get(0), centroidFor1));
//
//
//        MNISTImageProcessor.displayImage(centroidFor1);
}