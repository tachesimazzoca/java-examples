package com.github.tachesimazzoca.java.examples.javase;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageIOTest {
    @Test
    public void testTransparentGIF() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final int W = 64;
        final int H = 48;
        ImageIO.write(createARGBImage(W, H), "gif", baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ImageIO.setUseCache(false);
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        java.util.Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        assertTrue(readers.hasNext());
        ImageReader reader = readers.next();
        reader.setInput(iis);
        assertEquals("gif", reader.getFormatName());
        int n = reader.getNumImages(true);
        for (int i = 0; i < n; i++) {
            assertEquals(W, reader.getWidth(i));
            assertEquals(H, reader.getHeight(i));
        }
    }

    private static BufferedImage createARGBImage(int w, int h) {
        IndexColorModel cm = createIndexColorModel();
        BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED, cm);
        Graphics2D g = im.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, w, h);
        int w2 = w / 2;
        int h2 = h / 2;
        g.setColor(Color.RED);
        g.fillRect(0, 0, w2, h2);
        g.setColor(Color.GREEN);
        g.fillRect(w2, h2, w2, h2);
        g.setColor(Color.BLUE);
        g.fillRect(w2, 0, w2, h2);
        g.dispose();
        return im;
    }

    private static IndexColorModel createIndexColorModel() {
        BufferedImage ex = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
        IndexColorModel icm = (IndexColorModel) ex.getColorModel();
        int SIZE = 256;
        byte[] r = new byte[SIZE];
        byte[] g = new byte[SIZE];
        byte[] b = new byte[SIZE];
        byte[] a = new byte[SIZE];
        icm.getReds(r);
        icm.getGreens(g);
        icm.getBlues(b);
        java.util.Arrays.fill(a, (byte) 255);
        r[0] = g[0] = b[0] = a[0] = 0;
        return new IndexColorModel(8, SIZE, r, g, b, a);
    }
}
