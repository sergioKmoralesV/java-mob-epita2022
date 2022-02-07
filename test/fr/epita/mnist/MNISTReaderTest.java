package fr.epita.mnist;

import fr.epita.mnist.datamodel.MNISTImage;
import fr.epita.mnist.services.MNISTReader;

import java.util.List;

public class MNISTReaderTest {
    public static void main(String[] args) throws Exception {
        MNISTReader reader = new MNISTReader();
        List<MNISTImage> images = reader.readImagesfromFile("./mnist_test.csv", 100);

        if (!(images.get(0).getLabel() == 7)) throw new Exception("verifying that the first element expectes is 7 and got: " + images.get(0).getLabel());

        System.out.println("Label " + images.get(0).getLabel());
        showMatrix(images.get(0));
        System.out.println(images.get(23));

    }

    public static void showMatrix(MNISTImage image) { //We extract this method to be part of the Image model and simplify the output
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
