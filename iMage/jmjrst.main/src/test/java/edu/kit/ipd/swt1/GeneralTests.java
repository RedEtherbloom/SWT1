package edu.kit.ipd.swt1;

import org.jis.generator.Generator;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

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
	
	@Test
	public void testRotateImage1() throws IOException {
		BufferedImage temp = GeneralTestsHelpers.loadReferenceImage();
		if (!GeneralTestsHelpers.compareImages(image, generator.rotateImage(temp, 0))) {
			fail("RotateImage1: The images are not equal");
		}
	}
	
	@Test
	public void testRotateImage2() {
		assertEquals(null, generator.rotateImage(null, 0));
	}
}	
