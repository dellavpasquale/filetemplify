package it.pdv.tools.filetemplify.templify;

import java.io.IOException;

import it.pdv.tools.filetemplify.FileTemplifyConfig;
import it.pdv.tools.filetemplify.FileTemplifyConfig.Folder;
import it.pdv.tools.filetemplify.FileTemplifyException;
import it.pdv.tools.filetemplify.FileTemplifyFilter;

public class FileTemplify {
	
	private FileTemplifyConfig fileTemplifyConfig;

	public FileTemplify(FileTemplifyConfig fileTemplifyConfig) throws FileTemplifyException {
		if (fileTemplifyConfig == null) {
			throw new FileTemplifyException("No configuration defined!");
		}
		if (fileTemplifyConfig.getFolders() == null || fileTemplifyConfig.getFolders().isEmpty()) {
			throw new FileTemplifyException("No folder defined!");
		}
		this.fileTemplifyConfig = fileTemplifyConfig;
	}
	
	public void templify() throws IOException, FileTemplifyException {
		FileTemplifyFilter fileTemplifyFilter = new FileTemplifyFilter(fileTemplifyConfig.getExcludes());
		StringTemplify stringTemplify = new StringTemplify(fileTemplifyConfig.getPlaceholders(), fileTemplifyConfig.getFileNameTemplate(), fileTemplifyConfig.getFileContentTemplate(), fileTemplifyConfig.getFolderNameTemplate());
		
		for (Folder folder : fileTemplifyConfig.getFolders()) {
			FolderTemplify fileTemplify = new FolderTemplify(folder, fileTemplifyFilter, stringTemplify);
			fileTemplify.generate();
		}
	}

}
