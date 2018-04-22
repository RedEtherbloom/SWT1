package edu.kit.ipd.swt1;

import org.jis.generator.Generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Simon Rätzer(2061421)
 * Class, which runs general core-tests for e.g the generator class
 */
public class GeneralTests {
	private Generator generator;
	private BufferedImage image;
	
	/**
	 * Inits required objects for testing
	 * @throws IOException when the picture.jpg is missing
	 */
	@Before 
	public void setUp() throws IOException {
		generator = new Generator(null, 0);
		image = GeneralTestsHelpers.loadReferenceImage();
	}
	
	/**
	 * Rotates an image by 0 degrees and checks if it stays the same
	 * @throws IOException if the Image does not exist
	 */
	@Test
	public void testRotateImage1() throws IOException {
		BufferedImage temp = GeneralTestsHelpers.loadReferenceImage();
		if (!GeneralTestsHelpers.compareImages(image, generator.rotateImage(temp, 0))) {
			fail("RotateImage1: The images are not equal");
		}
	}
	
	/**
	 * Rotates a null-image to see if the code does work for that
	 */
	@Test
	public void testRotateImage2() {
		assertEquals(null, generator.rotateImage(null, 0));
	}
}	
