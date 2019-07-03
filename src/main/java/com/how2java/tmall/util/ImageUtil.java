package com.how2java.tmall.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 16:11
 **/

public class ImageUtil {
  private static BufferedImage change2jpg(File f) {
    try {
      Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
      PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
      pg.grabPixels();
      int width = pg.getWidth(), height = pg.getHeight();
      final int[] RGB_MASKS = { 0xFF0000, 0xFF00, 0xFF };
      final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
      DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
      WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
      return new BufferedImage(RGB_OPAQUE, raster, false, null);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void resizeImage(File srcFile, int width, int height, File destFile) {
    try {
      if(!destFile.getParentFile().exists()) {
        destFile.getParentFile().mkdirs();
      }
      Image i = ImageIO.read(srcFile);
      i = resizeImage(i, width, height);
      ImageIO.write((RenderedImage) i, "jpg", destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Image resizeImage(Image srcImage, int width, int height) {
    try {
      BufferedImage buffImg;
      buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
      return buffImg;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static File imageFileProcess(File imageFolder, MultipartFile image, int id) throws IOException {
    if (!imageFolder.getParentFile().exists()) {
      imageFolder.getParentFile().mkdirs();
    }
    File file = new File(imageFolder, id + ".jpg");
    image.transferTo(file);
    BufferedImage bufferedImage = change2jpg(file);
    ImageIO.write(bufferedImage, ".jpg", file);
    return file;
  }
}
