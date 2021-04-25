package com.example.funbanners.ServiceTests;

import com.example.funbanners.model.DisplayImage;
import com.example.funbanners.service.ToolKit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolKitTest {

    //String p = filepath to directory to save
    //File a = new File(p + "/saveFileName.png");
    //ImageIO.write(tinted2, "png", a);


    private ToolKit toolkit;
    private BufferedImage originalOtter;
    private String p;

    @Before
    public void setUp() throws Exception {
        toolkit = new ToolKit();
        originalOtter = ImageIO.read(new File("./src/test/java/com/example/funbanners/images/originalOtter.png"));
        p = "./src/test/java/com/example/funbanners/images/";
    }

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
        //source: https://stackoverflow.com/questions/11006394/is-there-a-simple-way-to-compare-bufferedimage-instances/29886786
    }

    @Test
    @DisplayName("Should return strings of correct lengths")
    public void generateStrings() throws Exception {
        assertEquals(18, toolkit.getSaltString(18).length());
        assertEquals(5, toolkit.getSaltString(5).length());
        assertEquals(0, toolkit.getSaltString(0).length());
        assertEquals(0, toolkit.getSaltString(-1).length());
    }

    @Test
    @DisplayName("Shrink original image to 100 x 100")
    public void shrinkImage() throws Exception {

        BufferedImage shrunkenOtter = toolkit.resizeImage(originalOtter, 100, 100);

        BufferedImage sO = ImageIO.read(new File (p + "shrunkenOtter.png"));
        assertEquals(true, compareImages(shrunkenOtter, sO));

    }

    @Test
    @DisplayName("Inflate original image to 800 x 800")
    public void imageImage() throws Exception {

        BufferedImage inflatedOtter = toolkit.resizeImage(originalOtter, 800, 800);

        BufferedImage bO = ImageIO.read(new File (p + "bigOtter.png"));
        assertEquals(true, compareImages(inflatedOtter, bO));

    }

    @Test
    @DisplayName("Rotate image 90 degrees counter clockwise")
    public void rotateImage() throws Exception {

        BufferedImage rotatedOtter = toolkit.rotateImage(originalOtter, 270);

        BufferedImage rO = ImageIO.read(new File (p + "rotatedOtter.png"));
        assertEquals(true, compareImages(rotatedOtter, rO));

    }

    @Test
    @DisplayName("Rotate shrunken image 90 degrees clockwise")
    public void smallRotateImage() throws Exception {

        BufferedImage shrunkenOtter = ImageIO.read(new File("./src/test/java/com/example/funbanners/images/shrunkenOtter.png"));

        BufferedImage smallRotatedOtter = toolkit.rotateImage(shrunkenOtter, 90);

        BufferedImage srO = ImageIO.read(new File (p + "smallRotatedOtter.png"));
        assertEquals(true, compareImages(smallRotatedOtter, srO));

    }

    @Test
    @DisplayName("Tint original image blue")
    public void tintBlueImage() throws Exception {

        BufferedImage tintedBlueOtter = toolkit.colorImage(originalOtter, 5, 5, 255, 50);

        BufferedImage tbO = ImageIO.read(new File (p + "tintedBlueOtter.png"));
        assertEquals(true, compareImages(tintedBlueOtter, tbO));

    }

    @Test
    @DisplayName("Copy an image such that you have 2 matching images")
    public void copyImageTest() throws Exception {

        ArrayList<BufferedImage> list = new ArrayList<>();

        BufferedImage n = toolkit.copyImage(originalOtter);
        list.add(originalOtter);
        list.add(n);

        assertEquals(list.size(), 2);
        assertEquals(true, compareImages(originalOtter, n));

    }

    @Test
    @DisplayName("Copy an image after resizing it such that you have 2 matching images")
    public void resizeCopyImageTest() throws Exception {

        ArrayList<BufferedImage> list = new ArrayList<>();

        BufferedImage o = toolkit.resizeImage(originalOtter, 150,150);
        BufferedImage n = toolkit.copyImage(o);
        list.add(o);
        list.add(n);

        assertEquals(list.size(), 2);
        assertEquals(true, compareImages(o, n));

    }

    @Test
    @DisplayName("Copy an image after resizing and coloring it such that you have 2 matching images")
    public void resizeColorCopyImageTest() throws Exception {

        ArrayList<BufferedImage> list = new ArrayList<>();

        BufferedImage o = toolkit.resizeImage(originalOtter, 150,150);
        o = toolkit.colorImage(o, 235,127,121, 100);
        BufferedImage n = toolkit.copyImage(o);
        list.add(o);
        list.add(n);
        assertEquals(list.size(), 2);
        assertEquals(true, compareImages(o, n));

    }

//    @Test
//    @DisplayName("Shrink original image then tint it green")
//    public void shrinkTintGreenImage() throws Exception {
//        String p = "./src/test/java/com/example/funbanners/images/";
//        BufferedImage originalOtter = ImageIO.read(new File("./src/test/java/com/example/funbanners/images/originalOtter.png"));
//
//        BufferedImage shrunkenOtter = toolkit.resizeImage(originalOtter, 200, 200);
//        BufferedImage tintedGreenOtter = toolkit.colorImage(shrunkenOtter, 5, 255, 5, 50);
//
//        BufferedImage sgO = ImageIO.read(new File (p + "shrunkTintedGreenOtter.png"));
//        assertEquals(true, compareImages(tintedGreenOtter, sgO));
//    }
//
//    @Test
//    @DisplayName("Shrink original image to 150 x 150 then rotate it 90 degrees counter clockwise then tint it green")
//    public void shrinkRotateTintRedImage() throws Exception {
//        String p = "./src/test/java/com/example/funbanners/images/";
//        BufferedImage originalOtter = ImageIO.read(new File("./src/test/java/com/example/funbanners/images/originalOtter.png"));
//
//        BufferedImage shrunkenOtter = toolkit.resizeImage(originalOtter, 150, 150);
//        BufferedImage rotatedOtter = toolkit.rotateImage(shrunkenOtter, 270);
//        BufferedImage tintedRedOtter = toolkit.colorImage(rotatedOtter, 255, 0, 0, 50);
//
//        BufferedImage srrO = ImageIO.read(new File (p + "shrunkTintedGreenOtter.png"));
//
//        File a = new File(p + "saveFileName.png");
//        ImageIO.write(srrO, "png", a);
//
//        assertEquals(true, compareImages(tintedRedOtter, srrO));
//
//    }






}
