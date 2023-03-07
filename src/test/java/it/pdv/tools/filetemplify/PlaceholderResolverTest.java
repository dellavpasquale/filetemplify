package it.pdv.tools.filetemplify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.pdv.tools.filetemplify.FileTemplifyConfig.Placeholder;
import it.pdv.tools.filetemplify.templify.StringTemplify;
import it.pdv.tools.filetemplify.templify.StringTemplify.FileTemplifyResourceType;

class PlaceholderResolverTest {
	
	@Test
	void testResolveWithoutPlaceholders() {
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			 new StringTemplify(null, null, null, null);
		});
		assertEquals("No placeholders defined!", exception.getMessage());
	}
	
	@Test
	void testResolveWithoutResourceType() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			resolver.templify(null, null);
		});
		assertEquals("No FileTemplify resource type specified", exception.getMessage());
	}

	@Test
	void testResolveNull() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		String result = resolver.templify(null, FileTemplifyResourceType.FILE_CONTENT);
		assertNull(result);
	}
	
	@Test
	void testResolveEmpty() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		String result = resolver.templify("", FileTemplifyResourceType.FILE_CONTENT);
		assertNull(result);
	}
	
	@Test
	void testResolvePlaceholderWithoutKey() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		});
		assertEquals("Placeholder without key defined!", exception.getMessage());
	}
	
	@Test
	void testResolveNoPlaceholder() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		p.setKey("a");
		p.setValue("v");
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		String result = resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		assertEquals("string", result);
	}
	
	@Test
	void testResolvePlaceholder() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		p.setKey("string");
		p.setValue("result");
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		String result = resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		assertEquals("result", result);
	}
	
	@Test
	void testResolvePlaceholderWithoutValue() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		p.setKey("string");
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		String result = resolver.templify("AstringA", FileTemplifyResourceType.FILE_CONTENT);
		assertEquals("AstringA", result);
	}
	
	@Test
	void testResolvePlaceholderWithTemplate() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		p.setKey("string");
		p.setValue("result");
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , "<%=${key}%>", null);
		String result = resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		assertEquals("<%=result%>", result);
	}
	
}
