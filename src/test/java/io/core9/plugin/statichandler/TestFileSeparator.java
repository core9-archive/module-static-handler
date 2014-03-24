package io.core9.plugin.statichandler;

import java.io.IOException;
/*import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;*/

import org.junit.Ignore;


@Ignore
public class TestFileSeparator {

	
	public static void main(String[] args) throws IOException {
		
		
/*	    final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("");
	    
	    
	    Files.walkFileTree(Paths.get("d:/"), new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	            if (matcher.matches(file)) {
	                System.out.println(file);
	            }
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
	            return FileVisitResult.CONTINUE;
	        }
	    });*/
	    
	    
/*	    Files.walkFileTree(Paths.get("c:/tmp"), new SimpleFileVisitor<Path>() {
			
		    @Override 
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {
		    	//result.put(prefix + file.toAbsolutePath().toString().substring(directory.length() + 1), Files.newInputStream(file));
		    	String tmpFile = file.toString().replace("\\", "/");
		    	
		    	Path tmp2 = Paths.get(tmpFile);
		    	
				System.out.println(tmp2);
		    	return FileVisitResult.CONTINUE;
		    }
		});*/
	}
}
