package it.pdv.tools.filetemplify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.pdv.tools.filetemplify.FileTemplifyConfig.Folder;
import it.pdv.tools.filetemplify.FileTemplifyConfig.Placeholder;
import it.pdv.tools.filetemplify.templify.FileTemplify;

class FileTemplifyTest {

	@Test
	void testNullConfig() {
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			new FileTemplify(null);
		});
		assertEquals("No configuration defined!", exception.getMessage());
	}

	@Test
	void testConfigWithoutFolders() {
		FileTemplifyConfig config = new FileTemplifyConfig();
		
		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			new FileTemplify(config);
		});
		assertEquals("No folder defined!", exception.getMessage());
	}
	
	@Test
	void testConfigWithoutPlaceholders() throws FileTemplifyException {
		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setDestination("destinationPath");
		folders.add(folder);
		config.setFolders(folders);
		
		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		
		assertEquals("No placeholders defined!", exception.getMessage());
	}

	@Test
	void testConfigFolderWithoutPath() throws FileTemplifyException {
		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder placeholder = new Placeholder();
		placeholder.setKey("key");
		placeholders.add(placeholder);
		config.setPlaceholders(placeholders);
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setDestination("destinationPath");
		folders.add(folder);
		config.setFolders(folders);

		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		assertEquals("No folder path defined!", exception.getMessage());
	}

	@Test
	void testConfigFolderWithoutDestination() throws FileTemplifyException {
		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder placeholder = new Placeholder();
		placeholder.setKey("key");
		placeholders.add(placeholder);
		config.setPlaceholders(placeholders);
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setPath("sourcePath");
		folders.add(folder);
		config.setFolders(folders);

		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		assertEquals("No destination folder path defined!", exception.getMessage());
	}

	@Test
	void testConfigFolderDestinationSubPathOfSource() throws FileTemplifyException {
		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder placeholder = new Placeholder();
		placeholder.setKey("key");
		placeholders.add(placeholder);
		config.setPlaceholders(placeholders);
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setPath("sourcePath");
		folder.setDestination("sourcePath/subfolder");
		folders.add(folder);
		config.setFolders(folders);

		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		assertEquals("Destination folder is a source subfolder!", exception.getMessage());
	}

	@Test
	void testConfigSourcePathNotExist() throws FileTemplifyException {
		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder placeholder = new Placeholder();
		placeholder.setKey("key");
		placeholders.add(placeholder);
		config.setPlaceholders(placeholders);
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setPath("sourcePath");
		folder.setDestination("destinationPath");
		folders.add(folder);
		config.setFolders(folders);

		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		assertEquals("Folder path doesn't exist!: sourcePath", exception.getMessage());
	}

	@Test
	void testConfigSourcePathNotAFolder() throws IOException, FileTemplifyException {
		String pathname = "." + File.separatorChar + "file.txt";
		File file = new File(pathname);
		file.createNewFile();

		FileTemplifyConfig config = new FileTemplifyConfig();
		List<Placeholder> placeholders = new ArrayList<>();
		Placeholder placeholder = new Placeholder();
		placeholder.setKey("key");
		placeholders.add(placeholder);
		config.setPlaceholders(placeholders);
		List<Folder> folders = new ArrayList<>();
		Folder folder = new Folder();
		folder.setPath(pathname);
		folder.setDestination("destinationPath");
		folders.add(folder);
		config.setFolders(folders);

		FileTemplify fileTemplify = new FileTemplify(config);

		Exception exception = Assertions.assertThrows(FileTemplifyException.class, () -> {
			fileTemplify.templify();
		});
		assertEquals("Folder path doesn't exist!: " + pathname, exception.getMessage());
	}
	
	@Test
	void testTemplify() throws IOException, FileTemplifyException {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("filetemplifyconfig.yaml");
		FileTemplifyConfig config = FileTemplifyConfigurationLoader.loadConfiguration(input);
		
		FileTemplify fileTemplify = new FileTemplify(config);
		fileTemplify.templify();
		
		BufferedReader result = new BufferedReader(new FileReader(".\\src\\test\\resources\\target\\fmname\\Rfmname-configuration.properties"));
		assertNotNull(result);
		assertEquals("prop=<%=fmdomain%>", result.readLine());
		
		Assertions.assertThrows(FileNotFoundException.class, () -> {
			new FileInputStream(".\\src\\test\\resources\\target\\fmname\\Rfmname-exclude.txt");
		});
		
		result.close();
		FileUtils.deleteDirectory(new File(".\\src\\test\\resources\\target"));
	}

}
