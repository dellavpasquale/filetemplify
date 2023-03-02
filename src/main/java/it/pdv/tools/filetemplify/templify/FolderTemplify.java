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

	private String rootFolder;
	private String sourceFolder;
	private FileTemplifyFilter fileTemplifyFilter;
	private StringTemplify stringTemplify;

	public FolderTemplify(Folder folder, FileTemplifyFilter fileTemplifyFilter, StringTemplify stringTemplify) throws IOException, FileTemplifyException {
		if (folder == null || folder.getPath() == null || folder.getPath().trim().isEmpty()) {
			throw new FileTemplifyException("No folder path defined!", null);
		}
		if (folder == null || folder.getDestination() == null || folder.getDestination().trim().isEmpty()) {
			throw new FileTemplifyException("No destination folder path defined!", null);
		}
		if(folder.getDestination().startsWith(folder.getPath())) {
			throw new FileTemplifyException("Destination folder is a source subfolder!", null);
		}
		rootFolder = folder.getDestination();
		sourceFolder = folder.getPath();
		this.fileTemplifyFilter = fileTemplifyFilter;
		this.stringTemplify = stringTemplify;
	}

	public void generate() throws IOException, FileTemplifyException {
		initDestinationFolder(sourceFolder, rootFolder);
		process(rootFolder);
	}

	private void initDestinationFolder(String sourceFolder, String destinationFolder) throws IOException, FileTemplifyException {
		cleanDestination(destinationFolder);
		copyDirectory(sourceFolder, destinationFolder);
	}

	private void cleanDestination(String destinationFolder) throws IOException, FileTemplifyException {
		FileUtils.deleteDirectory(new File(destinationFolder));
		FileUtils.deleteDirectory(new File(stringTemplify.templify(destinationFolder, FileTemplifyResourceType.FOLDER_NAME)));
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
		StringBuffer newContent = new StringBuffer();
		BufferedReader reader = null;
		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String oldLine = line + System.lineSeparator();
				newContent.append(stringTemplify.templify(oldLine, FileTemplifyResourceType.FILE_CONTENT));
				line = reader.readLine();
			}
			writer = new FileWriter(file);

			writer.write(newContent.toString());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void renameFile(File file, FileTemplifyResourceType fileTemplifyResourceType) throws FileTemplifyException {
		file.renameTo(new File(
				file.getParentFile().getAbsolutePath() + File.separator + stringTemplify.templify(file.getName(), fileTemplifyResourceType)));
	}

}
