package it.pdv.tools.filetemplify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileTemplifyConfigurationLoaderTest {

	@Test
	void testLoadConfigurationNull() {
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(null);
		});
		assertEquals("Config file is not valid!", exception.getMessage());
	}

	@Test
	void testLoadConfigurationEmpty() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("fileempty.yaml");
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(input);
		});
		assertEquals("Config file is not valid!", exception.getMessage());
	}

	@Test
	void testLoadConfigurationNotValid() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("filenotvalid.yaml");
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(input);
		});
		assertEquals("Config file is not valid!", exception.getMessage());
	}
	
	@Test
	void testLoadConfigurationValid() throws FileTemplifyException, FileNotFoundException {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("filetemplifyconfig.yaml");
		FileTemplifyConfig config = FileTemplifyConfigurationLoader.loadConfiguration(input);
		assertNotNull(config);
		assertNotNull(config.getExcludes());
		assertNotNull(config.getFolders());
		assertNotNull(config.getPlaceholders());
	}

}
