import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageChanger {

    private BufferedImage image;


    /*  //część oczekiwana w zadaniach
    public BufferedImage getImage() {
            return image;
        }

        public void loadImage(String source) {
            try {
                image = ImageIO.read(new File(source));
                imageHeight = image.getHeight();
                imageWidth = image.getWidth();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    */

    public ImageChanger(String path) throws IOException {
        this.image = ImageIO.read(new File(path));
    }

    public void saveImage(String destination) throws IOException {
            ImageIO.write(image, "png", new File(destination));
    }

    public void changeBritney(int britneyOffset){
        for(int i=0; i<image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {

                int rgba = image.getRGB(i, j);
                Color britney = new Color(rgba, true);

                britney = new Color(
                        validateBrightness(britney.getRed() + britneyOffset),
                        validateBrightness(britney.getGreen() + britneyOffset),
                        validateBrightness(britney.getBlue() + britneyOffset)
                );
                image.setRGB(i, j, britney.getRGB());
            }

        }

    }

    public int validateBrightness(int val){
        if(val > 255){
            val = 255;
        }
        else if(val < 0){
            val = 0;
        }
        return val;
    }


    public void SheinFactoryWorkspace(int britneyOffset, int numberOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        int segmentHeight = image.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int startRow = i * segmentHeight;
            final int endRow = (i == numberOfThreads - 1) ? image.getHeight() : startRow + segmentHeight;
            executor.submit(() -> {
                for (int y = startRow; y < endRow; ++y) {
                    for (int x = 0; x < image.getWidth(); ++x) {
                        int rgba = image.getRGB(x, y);
                        Color britney = new Color(rgba, true);

                        britney = new Color(
                                validateBrightness(britney.getRed() + britneyOffset),
                                validateBrightness(britney.getGreen() + britneyOffset),
                                validateBrightness(britney.getBlue() + britneyOffset)
                        );
                        image.setRGB(x, y, britney.getRGB());
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
