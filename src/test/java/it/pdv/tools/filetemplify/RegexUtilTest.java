package it.pdv.tools.filetemplify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.pdv.tools.filetemplify.util.RegexUtil;

class RegexUtilTest {
	
	@Test
	void testReplaceNull() {
		String result = RegexUtil.replace(null, null, null);
		assertNull(result);
	}
	
	@Test
	void testReplaceNoParameters() {
		String result = RegexUtil.replace("Astring", null, null);
		assertEquals("Astring", result);
	}
	
	@Test
	void testReplaceInvalidRegex() {
		Assertions.assertThrows(Exception.class, () -> {
			RegexUtil.replace("Astring", "(", "<%=$10%>");
		});
	}
	
	@Test
	void testReplace() {
		String result = RegexUtil.replace("Astring", "^(A)(?<key>.*)$", "<%=${key}%>");
		assertEquals("<%=string%>", result);
	}
	
	@Test
	void testMatchNull() {
		boolean result = RegexUtil.match(null, "");
		assertEquals(false, result);
	}
	
	@Test
	void testMatchWithoutRegex() {
		boolean result = RegexUtil.match("Astring", "");
		assertEquals(false, result);
	}
	
	@Test
	void testMatchFalse() {
		boolean result = RegexUtil.match("Astringxxx", "^.*\\.xxx$");
		assertEquals(false, result);
	}
	
	@Test
	void testMatchTrue() {
		boolean result = RegexUtil.match("Astring.xxx", "^.*\\.xxx$");
		assertEquals(true, result);
	}
	
	@Test
	void testWilcardToRegex() {
		String result = RegexUtil.wilcardToRegex("c*.java");
		assertEquals("^c.*\\.java$", result);
	}

}
