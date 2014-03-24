package io.core9.plugin.statichandler;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class FileScannerTest {

	@Test
	public void testIfJarExists() {
		try {
			URI jarLocation = new URI(this.getClass().getResource("/test.jar")
					.getPath());
			assertNotNull(jarLocation);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testExtraction() {
		try {
			URI location = new URI(this.getClass().getResource("/test.jar").getPath());
			Map<String, InputStream> result = FileScanner.getFilesWithRelativeNames("", location, "static");
			assertTrue(result.containsKey("/test.txt"));
			assertTrue(result.containsKey("/folder/test.txt"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testJarExtraction() {
		try {
			URI location = new URI(this.getClass().getResource("/test.jar")
					.getPath());
			URI jarLocation = new URI("jar:file:" + location.toString());
			Map<String, InputStream> result = FileScanner
					.getFilesWithRelativeNamesFromJar("", jarLocation, "static");
			assertTrue(result.containsKey("/test.txt"));
			assertTrue(result.containsKey("/folder/test.txt"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testFolderExtraction() {
		try {
			URI location = new URI(this.getClass().getResource("/static").getPath());
			Map<String, InputStream> result = FileScanner.getFilesWithRelativeNames("", location, "");
			assertTrue(result.containsKey("/folder/test.txt"));
			assertTrue(result.containsKey("/test.txt"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testPrefixedNameFolderExtraction() {
		try {
			URI location = new URI(this.getClass().getResource("/static").getPath());
			Map<String, InputStream> result = FileScanner.getFilesWithRelativeNames("/blaat", location, "");
			assertTrue(result.containsKey("/blaat/folder/test.txt"));
			assertTrue(result.containsKey("/blaat/test.txt"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testPrefixedNameJarExtraction() {
		try {
			URI location = new URI(this.getClass().getResource("/test.jar")
					.getPath());
			URI jarLocation = new URI("jar:file:" + location.toString());
			Map<String, InputStream> result = FileScanner
					.getFilesWithRelativeNamesFromJar("/blaat", jarLocation, "static");
			assertTrue(result.containsKey("/blaat/test.txt"));
			assertTrue(result.containsKey("/blaat/folder/test.txt"));
			assertFalse(result.containsKey("/test.txt"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail();
		}
	}
}
