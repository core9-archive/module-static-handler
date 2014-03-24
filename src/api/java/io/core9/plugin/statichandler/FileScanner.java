package io.core9.plugin.statichandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class FileScanner {
	
	/**
	 * Traverses a directory and adds all files to the result
	 * 
	 * @param folder
	 * @return
	 */
	public static List<File> traverseDirectory(File file) {
		List<File> result = new ArrayList<File>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File child : files) {
				result.addAll(traverseDirectory(child));
			}
		} else {
			result.add(file);
		}
		return result;
	}
	
	/**
	 * Return all files with a relative filename as key
	 * 
	 * @param prefix - prefix for all filenames
	 * @param directory - directory to scan for files
	 * @return
	 */
	public static Map<String, InputStream> getFilesWithRelativeNames(String prefix, URI location, String directory) {
		if(location.toString().endsWith(".jar")) {
			try {
				String scheme = "jar:";
				if(location.getScheme() == null) {
					scheme = "jar:file:";
				}
				URI jarLocation = new URI(scheme + location.toString());
				return getFilesWithRelativeNamesFromJar(prefix, jarLocation, directory);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return new HashMap<String, InputStream>();
		} else {
			return getFilesWithRelativeNamesFromDirectory(prefix, location, directory);
		}
	}
	
	/**
	 * Get the files from a given directory
	 * @param prefix
	 * @param location
	 * @param directory
	 * @return
	 */
	public static Map<String, InputStream> getFilesWithRelativeNamesFromDirectory(String prefix, URI location, String directory) {
		Map<String,InputStream> result = new HashMap<String, InputStream>();
		if(!directory.equals("")) {
			location = location.resolve(directory);
		}
		File folder = new File(location.toString());
		
		for(File file : traverseDirectory(folder)) {
			try {
				result.put(prefix + file.getAbsolutePath().substring(folder.getAbsolutePath().length()), new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Get the files in a directory from a given jar file 
	 * @param prefix
	 * @param jarLocation
	 * @param directory
	 * @return
	 */
	public static Map<String, InputStream> getFilesWithRelativeNamesFromJar(final String prefix, URI jarLocation, final String directory) {
		final Map<String,InputStream> result = new HashMap<String, InputStream>();
		Map<String, String> env = new HashMap<>(); 
        env.put("create", "true");
		try {
			System.out.println(jarLocation.toString());
			FileSystem jar;
			//TODO FILESYSTEM NEVER CLOSED
			try {
				jar = FileSystems.newFileSystem(jarLocation, env);
			} catch (FileSystemAlreadyExistsException e) {
				jar = FileSystems.getFileSystem(jarLocation);
			}
			Path start = jar.getPath(directory);
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
				
			    @Override 
			    public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {
			    	try {
						result.put(prefix + file.toAbsolutePath().toString().substring(directory.length() + 1), Files.newInputStream(file));
					} catch (IOException e) {
						e.printStackTrace();
					}
			    	return FileVisitResult.CONTINUE;
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
