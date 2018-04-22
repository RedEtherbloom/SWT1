package edu.kit.ipd.swt1;

import java.awt.image.BufferedImage;
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
}
