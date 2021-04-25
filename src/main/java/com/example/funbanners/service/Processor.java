package com.example.funbanners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

@Service
public class Processor {

    // stacking images: http://jens-na.github.io/2013/11/06/java-how-to-concat-buffered-images/
    // bufferedImage: https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html

    // image size 130 x 130 with 60 radius rounded edges
    // alpha range 20 - 50

    @Autowired
    CreateBinary bin;

    private ToolKit toolkit = new ToolKit();

    private final Integer[][] calm = {{241,241,241},{244,214,222},{237,157,146},{156,210,197},{51,136,119}};
    private final Integer[][] dark = {{59,31,77},{87,67,98},{200,195,209},{220,213,227},{183,170,199}};
    private final Integer[][] rosy = {{38,53,56},{229,71,92},{235,127,121},{241,201,165},{149,179,147}};
    private final Integer[][] royal = {{187,153,65},{18,34,55},{31,53,104},{56,113,175},{233,233,236}};
    private final Integer[][] pride = {{233,51,123},{233,112,38},{242,247,71},{75,150,39},{81,174,232}};
    //private final Integer[][][] randomTheme = {calm, dark, rosy, royal,pride};

    private final Integer[][] blm = {{92,91,91},{75,31,6},{159,96,0},{89,212,196},{149,29,30}};
    private final Integer[][] fall = {{77, 26, 18},{214, 56, 35},{246, 171, 82},{242, 197, 61},{151, 182, 116}};
    private final Integer[][] sunset = {{238,174,96},{250,144,98},{238,92,108},{205,73,147},{106,13,131}};
    //private final Integer[][] recycle = {{},{},{},{},{}};
    private final Integer[][] leaf = {{52, 93, 57},{247, 242, 242},{97, 181, 102},{149, 179, 145},{57, 55, 55}};
    private final Integer[][] iphone = {{250,246,243},{223,55,53},{221,244,216},{3,51,87},{183,174,228}};
    private final Integer[][] pride2 = {{166,206,227},{58, 120, 180},{178, 223, 138},{52, 160, 44},{243, 154, 153}};
    private final Integer[][] newRoyal = {{217, 186, 65},{0, 38, 90},{134, 160, 157},{125, 1, 26}};
    private final Integer[][] coleus = {{45, 6, 37},{243, 99, 209},{189, 239, 83},{213, 227, 172},{150, 223, 244}};
    private final Integer[][] avocado = {{242, 185, 204},{82, 54, 56},{187, 137, 24},{225, 220, 107},{161, 174, 37}};
    private final Integer[][] pumkin = {{249, 80, 6},{244, 208, 132},{150, 163, 79},{167, 171, 167}};
    private final Integer[][] lemon = {{151, 112, 54},{111, 116, 6},{234, 225, 0},{233, 231, 219}};

    private final Integer[][][] random = {pride, pride2, iphone, newRoyal, coleus, pumkin};

    private Integer[] black = {0,0,0};
    private static Integer[] snow = {255,255,255};


    public static void main(String[] args) throws Exception {}



    public byte[] makeNewPicture(String filepaths, ArrayList<Object> attributes) throws Exception {

        int width = (int) attributes.get(1);
        int length = (int) attributes.get(0);
        String theme = (String) attributes.get(2);
        int rLevel = (int) attributes.get(3);
        ArrayList<BufferedImage> pictures = byteToImages(filepaths);
        ArrayList<BufferedImage> pictures1 = getGridArrayAndResize(pictures, width*length, rLevel);

        ArrayList<BufferedImage> pictures2;
        if (!theme.equals("none")) {
            pictures2 = applyTheme(pictures1, theme, rLevel);
        } else {
            pictures2 = pictures1;
        }

        ArrayList<BufferedImage> pictures3 = roundCorners(pictures2, rLevel);
        ArrayList<BufferedImage> pictures4 = rotateImages(pictures3, rLevel);


        BufferedImage background = buildBackground(width, length);
        background = toolkit.colorImage(background, 0, 0, 0, 255);


        BufferedImage product = concatImages(pictures4, background, width, length);

        return bin.imageToBytes(product, "png");
    }

    public ArrayList<BufferedImage> byteToImages(String bytes) throws Exception {
        String[] pics = bytes.split(",");
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            byte[] b = bin.stringToByte(pics[i]);
            images.add(bin.toBufferedImage(b));
        }
        return images;
    }

    public BufferedImage concatImages(ArrayList<BufferedImage> images, BufferedImage background, Integer width, Integer height) {
        int i = 0;
        int x = 4;
        int y = 8;
        int k = 0;
        Graphics2D bg = background.createGraphics();
        while (i < images.size()) {
            //if (x == 2 + ((width*130)) + (4*width-1)) {
            if (k == width) {
                x = 4;
                y += 138;
                k = 0;
            }

            bg.drawImage(images.get(i), x + 4, y, null);
            x += 138;
            i++;
            k++;
        }
        bg.dispose();
        return background;
    }

    public BufferedImage buildBackground(Integer width, Integer height) {

        int tall = (height * 130) + ((height * 4) * 2 + 8);
        int wide = (width * 130) + ((width * 4) * 2 + 8);

        BufferedImage background = new BufferedImage(wide, tall, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = background.createGraphics();
        g2d.dispose();
        return background;

    }

    public ArrayList<BufferedImage> getImages(ArrayList<Path> paths, String s) throws Exception {

        ArrayList<BufferedImage> pictures = new ArrayList<>();
        BufferedImage image = null;
        for (int i = 0; i < paths.size(); i++) {
            image = null;
            try {
                image = ImageIO.read(new File(s + paths.get(i).toString()));
                pictures.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pictures;
    }

    public ArrayList<BufferedImage> getGridArrayAndResize(ArrayList<BufferedImage> images, Integer gSize, Integer level) throws Exception {
        ArrayList<BufferedImage> gridPictures = new ArrayList<>();
        Random random = new Random();

        if (level == 1) {
            int i = 0;
            int j = 0;
            while (i < gSize) {
                if (j == images.size()) {
                    j = 0;
                }
                gridPictures.add(toolkit.copyImage(images.get(j)));
                i++;
                j++;
            }
        } else {
            int i = 0;
            while (i < gSize) {
                int r = random.nextInt(images.size());
                gridPictures.add(toolkit.copyImage(images.get(r)));
                i++;
            }
        }

        for (int i = 0; i < gridPictures.size(); i++) {
            BufferedImage t = toolkit.resizeImage(gridPictures.get(i), 130, 130);
            gridPictures.set(i, t);
        }
        return gridPictures;
    }

    public ArrayList<BufferedImage> applyTheme(ArrayList<BufferedImage> images, String theme, Integer randomLevel) {

        Integer[][] palette;
        Random dice = new Random();

        if (theme.equals("random")) {
            palette = random[dice.nextInt(random.length - 1)];
        } else if (theme.equals("pride")) {
            palette = pride;
        } else if (theme.equals("pride2")) {
            palette = pride2;
        } else if (theme.equals("newRoyal")) {
            palette = newRoyal;
        } else if (theme.equals("iphone")) {
            palette = iphone;
        } else if (theme.equals("coleus")) {
            palette = coleus;
        } else {
            palette = pumkin;
        }

        if (randomLevel == 1) {
            int i = 0;
            int j = 0;
            while (i < images.size()) {
                if (j == palette.length) { j = 0; }
                BufferedImage t = toolkit.colorImage(images.get(i), palette[j][0], palette[j][1], palette[j][2], 100);
                images.set(i, t);
                i++;
                j++;
            }
        } else {
            int i = 0;
            int r;
            while (i < images.size()) {
                r = dice.nextInt(palette.length);
                BufferedImage t = toolkit.colorImage(images.get(i), palette[r][0], palette[r][1], palette[r][2], 100);
                images.set(i, t);
                i++;
            }
        }
        return images;
    }

    public ArrayList<BufferedImage> roundCorners(ArrayList<BufferedImage> images, Integer randomLevel) {

        Random random = new Random();

        if (randomLevel == 1) {
            for (int i = 0; i < images.size(); i++) {
                BufferedImage t = toolkit.makeRoundedCorner(images.get(i), 60);
                images.set(i, t);
            }
        } else {
            int r;
            for (int i = 0; i < images.size(); i++) {
                r = random.nextInt(3);
                BufferedImage t;
                if (r == 0) {
                    t = toolkit.makeRoundedCorner(images.get(i), 165);
                } else {
                    t = toolkit.makeRoundedCorner(images.get(i), 40);
                }
                images.set(i, t);
            }
        }
        return images;
    }

    public ArrayList<BufferedImage> rotateImages(ArrayList<BufferedImage> images, Integer randomLevel) throws Exception {

        Random random = new Random();
        Integer[] angles = {90,180,270};

        if (randomLevel == 3) {
            int r;
            int s;
            for (int i = 0; i < images.size(); i++) {
                r = random.nextInt(3);
                BufferedImage t;
                if (r == 0) {
                    s = random.nextInt(3);
                    t = toolkit.rotateImage(images.get(i), angles[s]);
                } else {
                    t = toolkit.rotateImage(images.get(i), 0);
                }
                images.set(i, t);
            }
        }
        return images;
    }
}
