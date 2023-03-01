package it.pdv.tools.filetemplify;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FileTemplifyFilterTest {

	@Test
	void testWithoutExcludes() {
		FileTemplifyFilter filter = new FileTemplifyFilter(null);
		File file = new File("C:\\");
		boolean result = filter.accept(file);
		assertEquals(true, result);
	}
	
	@Test
	void testExcludesPdf() {
		List<String> excludes = new ArrayList<>();
		excludes.add("*.pdf");
		FileTemplifyFilter filter = new FileTemplifyFilter(excludes);
		File file = new File("C:\\test.pdf");
		boolean result = filter.accept(file);
		assertEquals(false, result);
		
		file = new File("C:\\test.csv");
		result = filter.accept(file);
		assertEquals(true, result);
	}
	
	@Test
	void testExcludesFilesStartingWithDot() {
		List<String> excludes = new ArrayList<>();
		excludes.add(".*");
		FileTemplifyFilter filter = new FileTemplifyFilter(excludes);
		File file = new File("C:\\.test.pdf");
		boolean result = filter.accept(file);
		assertEquals(false, result);
		
		file = new File("C:\\test.csv");
		result = filter.accept(file);
		assertEquals(true, result);
	}

}
