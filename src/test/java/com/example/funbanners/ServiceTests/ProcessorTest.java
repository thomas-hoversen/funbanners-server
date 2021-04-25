package com.example.funbanners.ServiceTests;

import com.example.funbanners.service.Processor;
import com.example.funbanners.service.ToolKit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorTest {

    private Processor processor;
    private String p;
    private ToolKit toolkit;

    @Before
    public void setUp() throws Exception {
        processor = new Processor();
        p = "./src/test/java/com/example/funbanners/images/";
        toolkit = new ToolKit();
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
    @DisplayName("Create snow colored background for a 12 x 4 banner")
    public void twelveByFourSnowBannerBackground() throws Exception {
        Integer[] snow = {255,255,255};
        ToolKit toolkit = new ToolKit();
        BufferedImage bg = processor.buildBackground(12, 4);
        BufferedImage banner = toolkit.colorImage(bg, snow[0], snow[1], snow[2], 255);
        BufferedImage sO = ImageIO.read(new File (p + "twelveByFourSnowBannerBackground.png"));

        assertEquals(true, compareImages(banner, sO));

    }

    @Test
    @DisplayName("Transform a list of Paths into a list of BufferedImages")
    public void getImagesTest() throws Exception {
        Path q = Paths.get("./src/test/java/com/example/funbanners/testFilesImages");
        ArrayList<Path> input = new ArrayList<>();
        // paths
        Path path1 = q.resolve("bigOtter.png");
        Path path2 = q.resolve("originalOtter.png");
        Path path3 = q.resolve("rotatedOtter.png");
        Path path4 = q.resolve("shrunkenOtter.png");
        Path path5 = q.resolve("shrunkRotatedTintedRedOtter.png");
        Path path6 = q.resolve("shrunkTintedGreenOtter.png");
        Path path7 = q.resolve("smallRotatedOtter.png");
        Path path8 = q.resolve("tintedBlueOtter.png");

        input.add(path1);
        input.add(path2);
        input.add(path3);
        input.add(path4);
        input.add(path5);
        input.add(path6);
        input.add(path7);
        input.add(path8);

        ArrayList<BufferedImage> expectedOutput = new ArrayList<>();
        expectedOutput.add(ImageIO.read(new File(path1.toString())));
        expectedOutput.add(ImageIO.read(new File(path2.toString())));
        expectedOutput.add(ImageIO.read(new File(path3.toString())));
        expectedOutput.add(ImageIO.read(new File(path4.toString())));
        expectedOutput.add(ImageIO.read(new File(path5.toString())));
        expectedOutput.add(ImageIO.read(new File(path6.toString())));
        expectedOutput.add(ImageIO.read(new File(path7.toString())));
        expectedOutput.add(ImageIO.read(new File(path8.toString())));

        ArrayList<BufferedImage> actualOutput = processor.getImages(input, "");
        assertEquals(8, actualOutput.size());
        for (int i = 0; i < actualOutput.size();i++) {
            assertEquals(true, compareImages(expectedOutput.get(i), actualOutput.get(i)));
        }
        //assertEquals(expectedOutput, actualOutput); // compare images fetched with expected images
    }

    @Test
    @DisplayName("Get array of correct size for level 1 and 3")
    public void getGridArrayForDifferentLevels() throws Exception {
        Path q = Paths.get("./src/test/java/com/example/funbanners/testFilesImages");
        ArrayList<Path> input = new ArrayList<>();
        // paths
        Path path1 = q.resolve("bigOtter.png");
        Path path2 = q.resolve("originalOtter.png");
        Path path3 = q.resolve("rotatedOtter.png");
        Path path4 = q.resolve("shrunkenOtter.png");
        Path path5 = q.resolve("shrunkRotatedTintedRedOtter.png");
        Path path6 = q.resolve("shrunkTintedGreenOtter.png");
        Path path7 = q.resolve("smallRotatedOtter.png");
        Path path8 = q.resolve("tintedBlueOtter.png");
        ArrayList<BufferedImage> expectedOutput = new ArrayList<>();
        expectedOutput.add(ImageIO.read(new File(path1.toString())));
        expectedOutput.add(ImageIO.read(new File(path2.toString())));
        expectedOutput.add(ImageIO.read(new File(path3.toString())));
        expectedOutput.add(ImageIO.read(new File(path4.toString())));
        expectedOutput.add(ImageIO.read(new File(path5.toString())));
        expectedOutput.add(ImageIO.read(new File(path6.toString())));
        expectedOutput.add(ImageIO.read(new File(path7.toString())));
        expectedOutput.add(ImageIO.read(new File(path8.toString())));

        ArrayList<BufferedImage> level1 = processor.getGridArrayAndResize(expectedOutput, 40, 1);
        ArrayList<BufferedImage> level3 = processor.getGridArrayAndResize(expectedOutput, 80, 3);

        assertEquals(40, level1.size());
        assertEquals(80, level3.size());

    }

    @Test
    @DisplayName("Get expected images after tinting their color with random level 1")
    public void applyThemeLevelOneTest() throws Exception {
        Path q = Paths.get("./src/test/java/com/example/funbanners/testFilesImages");
        ArrayList<Path> input = new ArrayList<>();
        // paths
        Path path1 = q.resolve("bigOtter.png");
        Path path2 = q.resolve("originalOtter.png");
        Path path3 = q.resolve("rotatedOtter.png");
        ArrayList<BufferedImage> expectedOutput = new ArrayList<>();
        expectedOutput.add(ImageIO.read(new File(path1.toString())));
        expectedOutput.add(ImageIO.read(new File(path2.toString())));
        expectedOutput.add(ImageIO.read(new File(path3.toString())));
        ArrayList<BufferedImage> expectedOutput2 = new ArrayList<>();
        expectedOutput2.add(ImageIO.read(new File(path1.toString())));
        expectedOutput2.add(ImageIO.read(new File(path2.toString())));
        expectedOutput2.add(ImageIO.read(new File(path3.toString())));

        Integer[][] pride = {{233,51,123},{233,112,38},{242,247,71},{75,150,39},{81,174,232}};
        BufferedImage t = toolkit.colorImage(expectedOutput.get(0), pride[0][0], pride[0][1], pride[0][2], 100);
        expectedOutput.set(0, t);
        BufferedImage j = toolkit.colorImage(expectedOutput.get(1), pride[1][0], pride[1][1], pride[1][2], 100);
        expectedOutput.set(1, j);
        BufferedImage h = toolkit.colorImage(expectedOutput.get(2), pride[2][0], pride[2][1], pride[2][2], 100);
        expectedOutput.set(2, h);

        ArrayList<BufferedImage> realImage = processor.applyTheme(expectedOutput2, "pride", 1);

        assertEquals(true, compareImages(expectedOutput.get(0), realImage.get(0)));
        assertEquals(true, compareImages(expectedOutput.get(1), realImage.get(1)));
        assertEquals(true, compareImages(expectedOutput.get(2), realImage.get(2)));
    }


}
