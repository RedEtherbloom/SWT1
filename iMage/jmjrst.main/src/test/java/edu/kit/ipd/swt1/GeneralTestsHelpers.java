package edu.kit.ipd.swt1;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Simon Rätzer(2061421)
 * Encapsulates required functions to keep the GeneralTests-Classs clean
 */
public final class GeneralTestsHelpers {
	
	/**
	 * Just for checkstyle reasons
	 */
	private GeneralTestsHelpers() {
	}
	
	/**
	 * @return Returns new buffered image to the reference image
	 * @throws IOException if picture.jpg does not exist
	 */
	public static BufferedImage loadReferenceImage() throws IOException {
		return ImageIO.read(new File("src/test/resources/picture.jpg"));
	}
	
	/**
	 * @param image Buffered image to clone
	 * @return Returns a deep copy of the image
	 */
	public static BufferedImage cloneImage(BufferedImage image) {
		 ColorModel color = image.getColorModel();
		 boolean premultiplied = color.isAlphaPremultiplied();
		 WritableRaster raster = image.copyData(null);
		 
		 return new BufferedImage(color, raster, premultiplied, null);
		}
	
	/**
	 * @param a First {@link BufferedImage}
	 * @param b Second {@link BufferedImage}
	 * @return Returns whether they are equal (point to the same Object, or match in pixels)
	 */
	public static boolean compareImages(BufferedImage a, BufferedImage b) {
		if (a.equals(b)) {
			return true;
		}
		
		int height = a.getHeight();
		int width  =  a.getWidth();
		
		if (height != b.getHeight() || width != b.getWidth()) {
			return false;
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (a.getRGB(i, j) != b.getRGB(i, j)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @param a Normal buffered Image
	 * @param b By 90 degrees rotated Image
	 * @return Returns if the they both have the same pixeladata
	 */
	public static boolean sameImageAfter90(BufferedImage a, BufferedImage b) {
		int height = a.getHeight();
		int width  = a.getWidth();
		
		if (height != b.getWidth() || width != b.getHeight()) {
			return false;
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (a.getRGB(i, j) != b.getRGB(height - j, i)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @param a Normal buffered Image
	 * @param b By 270 degrees rotated Image
	 * @return Returns if the they both have the same pixeladata
	 */
	public static boolean sameImageAfter270(BufferedImage a, BufferedImage b) {
		int height = a.getHeight();
		int width  = a.getWidth();
		
		if (height != b.getWidth() || width != b.getHeight()) {
			return false;
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (a.getRGB(i, j) != b.getRGB(j, width - i)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
