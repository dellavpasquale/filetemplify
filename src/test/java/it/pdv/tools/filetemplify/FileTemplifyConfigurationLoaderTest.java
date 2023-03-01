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
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(null);
		});
	}

	@Test
	void testLoadConfigurationEmpty() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("fileempty.yaml");
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(input);
		});
	}

	@Test
	void testLoadConfigurationNotValid() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("filenotvalid.yaml");
		Assertions.assertThrows(FileTemplifyException.class, () -> {
			FileTemplifyConfigurationLoader.loadConfiguration(input);
		});
	}
	
	@Test
	void testLoadConfigurationValid() throws FileTemplifyException, FileNotFoundException {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("filetemplifyconfig.yaml");
		FileTemplifyConfig config = FileTemplifyConfigurationLoader.loadConfiguration(input);
		assertNotNull(config);
		assertEquals(config.getDestinationPath(), "C:\\Users\\qpasdel\\eclipse-workspace\\generator-mitoms\\generators");
		assertNotNull(config.getExcludes());
		assertNotNull(config.getFolders());
		assertNotNull(config.getPlaceholders());
	}

}
