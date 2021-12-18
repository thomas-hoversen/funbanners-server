package com.example.funbanners.Memory_Speed_tests;

import com.example.funbanners.service.Processor;
import com.example.funbanners.service.ToolKit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Memory_Used {

    private Processor processor;
    private String p;
    private ToolKit toolkit;

    @Before
    public void setUp() throws Exception {
        processor = new Processor();
        p = "./src/test/java/com/example/funbanners/images/";
        toolkit = new ToolKit();
    }

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }


    @Test
    @DisplayName("Measure memory used for a snow colored background for a 12 x 4 banner")
    public void twelveByFourSnowBannerBackground() throws Exception {
        Integer[] snow = {255,255,255};
        ToolKit toolkit = new ToolKit();
        long startTime = System.currentTimeMillis();
        BufferedImage bg = processor.buildBackground(12, 4);
        BufferedImage banner = toolkit.colorImage(bg, snow[0], snow[1], snow[2], 255);
        BufferedImage sO = ImageIO.read(new File(p + "twelveByFourSnowBannerBackground.png"));
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Milliseconds run: " + elapsedTime);
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: "
                + bytesToMegabytes(memory));

        //assertEquals(true, 1);

    }


}
