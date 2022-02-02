package fr.epita.mnist;

import fr.epita.mnist.datamodel.MNISTImage;
import fr.epita.mnist.services.MNISTReader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MNISTReaderTest {
    public static void main(String[] args) throws Exception {
        MNISTReader reader = new MNISTReader();
        List<MNISTImage> images = reader.readImagesfromFile(new File("./mnist_test.csv"), 100);

        Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));
        imagesByLabel.forEach((label, imageList) -> {
            System.out.println(label + " count : " + imageList.size());
        });

        if (!(images.get(0).getLabel() == 7)) throw new Exception("verifying that the first element expectes is 7 and got: " + images.get(0).getLabel());

        displayImage(images.get(0));
    }

    public static void displayImage(MNISTImage image) {
        double[][] pixels = image.getPixels();
        for(int i = 0; i < pixels.length; i++) {
            for(int j=0; j< pixels[i].length; j++) {
                if(pixels[i][j] < 100) {
                    System.out.print("..");
                } else {
                    System.out.print("xx");
                }
            }
            System.out.println("");
        }
    }
}
