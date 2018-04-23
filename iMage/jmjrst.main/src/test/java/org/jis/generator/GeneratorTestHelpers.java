package org.jis.generator;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * @author Simon Rätzer(2061421)
 * Encapsulates required functions to keep the GeneralTests-Classs clean
 */
public final class GeneratorTestHelpers {
	
	/**
	 * Path to the Reference-Image used for testing
	 */
	public static final String referencePath = "src/test/resources/picture.jpg";
	/**
	 * Path to the folder, where output should be stored
	 */
	public static final String directory   = "target/dataTest";
	
	
	/**
	 * Just for checkstyle reasons
	 */
	private GeneratorTestHelpers() {
	}
	
	/**
	 * @return Returns new buffered image to the reference image
	 * @throws IOException if picture.jpg does not exist
	 */
	public static BufferedImage loadReferenceImage() throws IOException {
		return ImageIO.read(new File(referencePath));
	}
	
	/**
	 * Creates the reference-dir (if it does not exist)
	 */
	public static void createTestDir() {
		File dir = new File(directory);
		dir.mkdirs();
	}

	/**
	 * Writes a Buffered Image back to the test directory
	 * @param image Image to write
	 * @throws IOException if an writing exception occurs
	 */
	public static void saveBufferedImage(BufferedImage image) throws IOException {
		final String baseName    = "rotatedPicture_";
		final String format      = "jpg";
		String currentTime = new SimpleDateFormat("HHmmss_SSS").format(new Date());
		
		createTestDir();
		
		File file = new File(directory + "/" + baseName + currentTime + "." + format);
		ImageIO.write(image, format, file);
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
	 * @param b 90-Degree rotated Picture
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
				if (a.getRGB(i, j) != b.getRGB(height - 1 -  j, i)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @param a image to test with
	 * @param b 180-Degree rotated Picture
	 * @return true, if the height, width and pixels are the same after the rotation
	 */
	public static boolean sameImageAfter180(BufferedImage a, BufferedImage b) {
		
		int height = a.getHeight();
		int width  =  a.getWidth();
		
		if (height != b.getHeight() || width != b.getWidth()) {
			return false;
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (a.getRGB(i, j) != b.getRGB(width - i - 1, height - 1 - j)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @param a Normal buffered Image
	 * @param b 270-Degree rotated picture
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
				if (a.getRGB(i, j) != b.getRGB(j, width - 1 - i)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
