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
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			 new StringTemplify(null, null, null, null);
		});
	}
	
	@Test
	void testResolveWithoutResourceType() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			resolver.templify(null, null);
		});
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
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		});
	}
	
	@Test
	void testResolvePlaceholderWithoutValue() throws FileTemplifyException {
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder p = new Placeholder();
		p.setKey("s");
		placeholders.add(p);
		StringTemplify resolver = new StringTemplify(placeholders, null , null, null);
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			resolver.templify("string", FileTemplifyResourceType.FILE_CONTENT);
		});
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
