package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.jis.options.Options;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Vector;

import org.jis.Main;
import org.jis.Messages;

/**
 * More complex tests, with 35% coverage
 * @author Simon Rätzer (2061421)
 */
@RunWith(MockitoJUnitRunner.class)
public class ComplexGeneratorTest {
	private Generator generator;
	private BufferedImage image;
	
	/**
	 * Temp-Folder to avoid clutterance in the target-dir
	 */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Mock
	Main m;

	/**
	 * Prepares the Test-Env
	 * @throws IOException if the refence image is missing
	 */
	@Before
	public void setUp() throws IOException  {
		generator = new Generator(m, 0);
		image = GeneratorTestHelpers.loadReferenceImage();
		
		m.mes = new Messages(Locale.ENGLISH);
	}

	/**
	 * Tests if the Methods exits when the input-image is unitialized => does not write any junk data
	 * @throws IOException if the generate-method could not write
	 */
	@Test(expected = NullPointerException.class)
	public void generateImageTest1() throws IOException {
		File img    = null;
		File dir    = folder.getRoot();
		
		String praefix = "pointless_";
		
		generator.generateImage(img, dir, false, image.getWidth(), image.getHeight(), praefix);
	}
	
	/**
	 * Checks, if the landscape-mode works
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest2() throws IOException {
		File img = folder.newFile("image.jpg");
		image = generator.rotateImage(image, Generator.ROTATE_90);
		ImageIO.write(image, "jpg", img);
		
		File dst    = folder.newFolder("out");
		
		String praefix = "pointless_";
		
		File ret = generator.generateImage(img, dst, false, image.getWidth(), image.getHeight(), praefix);
		assertEquals(true, ret.exists());
	}
	
	/**
	 * Checks all generation options (speed, quality and so on)
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest3() throws IOException {
		File img = folder.newFile("image.jpg");
		ImageIO.write(image, "jpg", img);
		File dst    = folder.newFolder("out");
		
		for (int i = 0; i < 4; i++) {
			Options.getInstance().setModus(i);
			String praefix = "pointless" + i + "_";
			
			File ret = generator.generateImage(img, dst, false, image.getWidth(), image.getHeight(), praefix);
			assertEquals(true, ret.exists());
		}
	}
	
	/**
	 * Checks the copyright function
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest4() throws IOException {
		File img = folder.newFile("image.jpg");
		ImageIO.write(image, "jpg", img);
		File dst    = folder.newFolder("out");

		
		String praefix = "pointless_";
			
		Options.getInstance().setCopyright(true);
		
		File ret = generator.generateImage(img, dst, false, image.getWidth(), image.getHeight(), praefix);
		assertEquals(true, ret.exists());
	}
	
	/**
	 * Checks the write-to-file-mode
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest5() throws IOException {
		File img = folder.newFile("image.jpg");
		ImageIO.write(image, "jpg", img);
		File dst    = folder.newFile("out.jpg");
		dst.delete();
		
		String praefix = "pointless_";
			
		Options.getInstance().setCopyright(true);
		
		generator.generateImage(img, dst, false, image.getWidth(), image.getHeight(), praefix);
		assertEquals(true, dst.exists());
	}

	/**
	 * Tests if it works without Antialising
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest6() throws IOException {
		File img    = new File(GeneratorTestHelpers.referencePath);
		File dir    = folder.getRoot();
		
		String praefix = "pointless_";
		
		Options.getInstance().setAntialiasing(false);
		
		File ret = generator.generateImage(img, dir, false, image.getWidth(), image.getHeight(), praefix);
		assertTrue(ret.exists());
	}
	
	/**
	 * Tests if it works without Copying Metadata
	 * @throws IOException if the generate-method could not write
	 */
	@Test
	public void generateImageTest7() throws IOException {
		File img    = new File(GeneratorTestHelpers.referencePath);
		File dir    = folder.getRoot();
		
		String praefix = "pointless_";
		
		Options.getInstance().setCopyMetadata(false);
		
		File ret = generator.generateImage(img, dir, false, image.getWidth(), image.getHeight(), praefix);
		assertTrue(ret.exists());
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
	/**
	 * Checks if the function creates a zip with valid inputs
	 * @throws IOException if the temp-dir could not be created
	 */
	@Test
	public void generateZipTest1() throws IOException {
		File temp = folder.newFile("temp.zip");
		File img = folder.newFile("image.jpg");
		ImageIO.write(image, "jpg", img);
		
		Vector<File> files = new Vector<File>();
		files.add(img);
		
		generator.createZip(temp, files);
		assertEquals(true, temp.exists());
	}
	
	/**
	 * Tests the resize method with 10 files
	 * @throws IOException if the temp-dir could not be created 
	 */
	@Test
	public void generateTextTest1() throws IOException {
		
		File input = folder.newFolder("input");
		File output = folder.newFolder("output");
		
		for (int i = 0; i < 10; i++) {
			File temp = new File(input, "picture" + i + ".jpg");
			ImageIO.write(image, "jpg", temp);
		}
		
		generator.generateText(input, output, image.getWidth() / 2, image.getHeight());
		
		assertEquals(10, output.listFiles().length);
	}
	
	/**
	 * Tests the resize method with a single file
	 * @throws IOException if the temp-dir could not be created
	 */
	@Test
	public void generateTextTest2() throws IOException {
		File input = folder.newFile("input.jpg");
		ImageIO.write(image, "jpg", input);
		File output = folder.newFile("output.jpg");
		
		generator.generateText(input, output, image.getWidth() / 2, image.getHeight());
		
		assertTrue(output.exists());
	}
}
