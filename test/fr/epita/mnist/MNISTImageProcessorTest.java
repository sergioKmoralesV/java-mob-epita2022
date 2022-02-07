package fr.epita.mnist;

import fr.epita.mnist.datamodel.MNISTImage;
import fr.epita.mnist.services.MNISTClassifier;
import fr.epita.mnist.services.MNISTClassifierSD;
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
        // Clarification: I am using both files from MNIST
        List<MNISTImage> imagesTrain = reader.readImagesfromFile("./mnist_train.csv", 10000);
        List<MNISTImage> images = reader.readImagesfromFile("./mnist_test.csv", 10000);

        //We are processing the images and then separating by label
        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));
        System.out.println("We have " + imagesByLabel.size() + " labels. \n--- Testing the centroids with the following amount of images per label ----------------------------------------");

        //We compute the centroids for each group of images according to their label.
        MNISTClassifier classifier = new MNISTClassifier();
        classifier.trainCentroids(imagesTrain);

        System.out.println("--- Testing the distance ----------------------------------------");
        List<MNISTImage> listOfOnes = imagesByLabel.get(0.0);
        //Here we are testing the computation of the distance
        System.out.println(processor.getDistance(classifier.getCentroids().get(0.0), listOfOnes.get(0)));
        System.out.println(processor.getDistance(classifier.getCentroids().get(0.0), listOfOnes.get(1)));

        System.out.println("--- Confusion matrix (average centroid) ----------------------------------------");
        double[][] confusionMatrixAverage = calculateDistribution(classifier, images);
        showMatrix(confusionMatrixAverage);
        System.out.println("The accuracy of the model is: " + String.format("%.2f",calculateCertainty(confusionMatrixAverage, images.size()) * 100.00) + "%");

        System.out.println("\n--- Confusion matrix (standard deviation centroid) ----------------------------------------");
        MNISTClassifierSD classifierSD = new MNISTClassifierSD();
        classifierSD.trainCentroidsStandardDeviation(imagesTrain);
        double[][] confusionMatrixSD = calculateDistributionSD(classifierSD, images);
        showMatrix(confusionMatrixSD);
        System.out.println("The accuracy of the model is: " + String.format("%.2f",calculateCertainty(confusionMatrixSD, images.size()) * 100.00) + "%");

    }

    public static void showMatrix(double[][] matrix) { //We extract this method to be part of the Image model and simplify the output
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t\t");
            }
            System.out.println("");
        }
    }

    public static double[][] calculateDistribution(MNISTClassifier classifier, List<MNISTImage> images) {
        double[][] distribution = new double[10][10];

        for (MNISTImage image : images) {
            int iInDistribution = (int) image.getLabel();
            int jInDistribution = (int) classifier.predict(image);

            distribution[iInDistribution][jInDistribution] += 1.0;
        }

        return distribution;
    }

    public static double[][] calculateDistributionSD(MNISTClassifierSD classifier, List<MNISTImage> images) {
        double[][] distribution = new double[10][10];

        for (MNISTImage image : images) {
            int iInDistribution = (int) image.getLabel();
            int jInDistribution = (int) classifier.predict(image);

            distribution[iInDistribution][jInDistribution] += 1.0;
        }

        return distribution;
    }

    public static double calculateCertainty(double[][] confusionMatrix, double totalTested) {
        double correctSum = 0.0;

        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix.length; j++) {
                if (i == j) {
                    correctSum += confusionMatrix[i][j];
                }
            }
        }

        return correctSum / totalTested;
    }
}

