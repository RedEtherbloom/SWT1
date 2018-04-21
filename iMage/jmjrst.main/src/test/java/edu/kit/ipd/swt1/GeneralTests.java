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
	 */
	@Before 
	public void setUp() throws IOException{
		generator = new Generator(null, 0);
		image = ImageIO.read(new File("src/test/resources/picture.jpg"));
	}
}	
