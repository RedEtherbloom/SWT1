package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Simon Rätzer(2061421)
 * Class, which runs general core-tests for e.g the generator class
 */
public class GeneratorTest {
	private Generator generator;
	private BufferedImage image;
	
	/**
	 * Inits required objects for testing
	 * @throws IOException when the picture.jpg is missing
	 */
	@Before 
	public void setUp() throws IOException {
		generator = new Generator(null, 0);
		image = GeneratorTestHelpers.loadReferenceImage();
	}
	
	/**
	 * Saves the modified image after 
	 * @throws IOException if a writing error occurs
	 */
	@After
	public void tearDown() throws IOException {
		if (image != null) {
			GeneratorTestHelpers.saveBufferedImage(image);
		}
	}
	
	/**
	 * Rotates an image by 0 degrees and checks if it stays the same
	 * @throws IOException if the Image does not exist
	 */
	@Test
	public void testRotateImage1() {
		BufferedImage temp = GeneratorTestHelpers.cloneImage(image);
		if (!GeneratorTestHelpers.compareImages(image, generator.rotateImage(temp, 0))) {
			fail("RotateImage1: The images are not equal");
		}
	}
	
	/**
	 * Rotates the image by 90 Degrees and checks if they are still the same
	 */
	@Test
	public void testRotate90Degree() {
		BufferedImage temp = GeneratorTestHelpers.cloneImage(image);
		image = generator.rotateImage(image, Generator.ROTATE_90);
		
		if (!GeneratorTestHelpers.sameImageAfter90(temp, image)) {
			fail("90 Degree-Rotate: Images differ!");
		}
	}
	
	/**
	 * Rotates the image by 180 Degrees and checks if they are still the same
	 */
	@Test
	public void testRotate180Degree() {
		BufferedImage temp = GeneratorTestHelpers.cloneImage(image);
		image = generator.rotateImage(image, Generator.ROTATE_180);
		
		if (!GeneratorTestHelpers.sameImageAfter180(temp, image)) {
			fail("180 Degree-Rotate: Images differ!");
		}
	}
	
	
	/**
	 * Rotates the image by 270 Degrees and checks if they are still the same
	 */
	@Test
	public void testRotate270Degree() {
		BufferedImage temp = GeneratorTestHelpers.cloneImage(image);
		image = generator.rotateImage(image, Generator.ROTATE_270);
		
		if (!GeneratorTestHelpers.sameImageAfter270(temp, image)) {
			fail("270 Degree-Rotate: Images differ!");
		}
	}
	
	/**
	 * Rotates a null-image to see if the code does work for that
	 */
	@Test
	public void testRotateImage2() {
		image = null;
		assertEquals(null, generator.rotateImage(null, 0));
	}
	
	/**
	 * Tries to rotate an image in a forbidden way. Should throw in {@link IllegalArgumentException}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRotateImageExcep1() {
		generator.rotateImage(image, 0.5);
	}
}	
