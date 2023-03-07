package it.pdv.tools.filetemplify.templify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import it.pdv.tools.filetemplify.FileTemplifyConfig.Folder;
import it.pdv.tools.filetemplify.FileTemplifyException;
import it.pdv.tools.filetemplify.FileTemplifyFilter;
import it.pdv.tools.filetemplify.templify.StringTemplify.FileTemplifyResourceType;

public class FolderTemplify {

	private String destinationFolder;
	private String sourceFolder;
	private FileTemplifyFilter fileTemplifyFilter;
	private StringTemplify stringTemplify;

	public FolderTemplify(Folder folder, FileTemplifyFilter fileTemplifyFilter, StringTemplify stringTemplify)
			throws FileTemplifyException {
		if (folder == null || folder.getPath() == null || folder.getPath().trim().isEmpty()) {
			throw new FileTemplifyException("No folder path defined!");
		}
		if (folder.getDestination() == null || folder.getDestination().trim().isEmpty()) {
			throw new FileTemplifyException("No destination folder path defined!");
		}
		if (folder.getDestination().startsWith(folder.getPath())) {
			throw new FileTemplifyException("Destination folder is a source subfolder!");
		}
		String folderName = getFolderName(folder.getPath());
		if (folderName == null) {
			throw new FileTemplifyException("Folder path doesn't exist!: " + folder.getPath());
		}
		this.sourceFolder = folder.getPath();
		this.destinationFolder = stringTemplify.templify(folder.getDestination() + File.separator + folderName,
				FileTemplifyResourceType.FOLDER_NAME);
		this.fileTemplifyFilter = fileTemplifyFilter;
		this.stringTemplify = stringTemplify;
	}

	private String getFolderName(String path) {
		String result = null;
		File folder = new File(path);
		if (folder.isDirectory()) {
			result = folder.getName();
		}
		return result;
	}

	public void generate() throws IOException, FileTemplifyException {
		initDestinationFolder(sourceFolder, destinationFolder);
		process(destinationFolder);
	}

	private void initDestinationFolder(String sourceFolder, String destinationFolder)
			throws IOException {
		cleanDestination(destinationFolder);
		copyDirectory(sourceFolder, destinationFolder);
	}

	private void cleanDestination(String destinationFolder) throws IOException {
		FileUtils.deleteDirectory(new File(destinationFolder));
	}

	public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
		File sourceDirectory = new File(sourceDirectoryLocation);
		File destinationDirectory = new File(destinationDirectoryLocation);
		FileUtils.copyDirectory(sourceDirectory, destinationDirectory, fileTemplifyFilter);
	}

	private void process(String fileName) throws IOException, FileTemplifyException {
		File file = new File(fileName);
		processFile(file);
	}

	private void processFile(File file) throws IOException, FileTemplifyException {
		if (file.isFile()) {
			replaceInFile(file);
			renameFile(file, FileTemplifyResourceType.FILE_NAME);
		} else if (file.isDirectory()) {
			for (File children : file.listFiles()) {
				processFile(children);
			}
			renameFile(file, FileTemplifyResourceType.FOLDER_NAME);
		}
	}

	private void replaceInFile(File file) throws IOException, FileTemplifyException {
		StringBuilder newContent = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file));
				FileWriter writer = new FileWriter(file);) {
			String line = reader.readLine();
			while (line != null) {
				String oldLine = line + System.lineSeparator();
				newContent.append(stringTemplify.templify(oldLine, FileTemplifyResourceType.FILE_CONTENT));
				line = reader.readLine();
			}

			writer.write(newContent.toString());
		} catch (Exception e) {
			throw new FileTemplifyException("Error occurs during replace file content!", e);
		}
	}

	private void renameFile(File file, FileTemplifyResourceType fileTemplifyResourceType) throws FileTemplifyException {
		boolean renamed = file.renameTo(new File(file.getParentFile().getAbsolutePath() + File.separator
				+ stringTemplify.templify(file.getName(), fileTemplifyResourceType)));
		if(!renamed) {
			throw new FileTemplifyException("Unable to rename file: " + file.getAbsolutePath());
		}
	}

}
