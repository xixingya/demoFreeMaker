package com.example.demo;

import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.xhtmlrenderer.*;

public class XhtmlRender {
    private String inputFilename = "G:/index.html";
    private String outputFilename = "G:/html.png";
    private int widthImage = 1000;
    private int heightImage = 1300;
    public void convertToImage() throws IOException {
        System.out.println("Calling convertToImage inputFilename=" + inputFilename + " outputFilename=" + outputFilename);
        final File f = new File(inputFilename);
        final Java2DRenderer renderer = new Java2DRenderer(f, widthImage, heightImage);
        final BufferedImage img = renderer.getImage();
        final FSImageWriter imageWriter = new FSImageWriter();
        imageWriter.setWriteCompressionQuality(0.9f);
        imageWriter.write(img, outputFilename);
        System.out.println("Done with rendering");
    }
    public static void main(final String[] args) throws Exception {
        final XhtmlRender renderer = new XhtmlRender();
        if (args.length != 2) {
            renderer.inputFilename = "D:/index.html";
            renderer.outputFilename = "D:/html.png";
            System.out.println("Usage : XHTMLToImage INPUTFILE.xhtml OUTPUTFILE.png <width> <height>");
        } else {
            renderer.inputFilename = args[0];
            renderer.outputFilename = args[1];
        }
        if (args.length == 4) {
            renderer.widthImage = 1000 ;
            renderer.heightImage = 1300;
        }
        renderer.convertToImage();
    }

}
