package com.example.funbanners.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Use this class to view images locally. Put object in whatever class main() you want to generate an image in.
public class DisplayImage {

    public DisplayImage(final String filename) throws Exception
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame editorFrame = new JFrame("Cute Otter");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                BufferedImage image = null;
                try
                {
                    image = ImageIO.read(new File(filename));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
                ImageIcon imageIcon = new ImageIcon(image);
                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }

    public DisplayImage(final BufferedImage picture) throws Exception
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame editorFrame = new JFrame("Cute Otter");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                BufferedImage image = picture;
                ImageIcon imageIcon = new ImageIcon(image);
                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }
}

