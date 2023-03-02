package it.pdv.tools.filetemplify.templify;

import java.util.List;

import it.pdv.tools.filetemplify.FileTemplifyConfig.Placeholder;
import it.pdv.tools.filetemplify.FileTemplifyException;
import it.pdv.tools.filetemplify.util.RegexUtil;

public class StringTemplify {

	public enum FileTemplifyResourceType {
		FILE_NAME, FOLDER_NAME, FILE_CONTENT
	};

	private List<Placeholder> placeholders;
	private String fileNameTemplate;
	private String fileContentTemplate;
	private String folderNameTemplate;

	public StringTemplify(List<Placeholder> placeholders, String fileNameTemplate, String fileContentTemplate,
			String folderNameTemplate) throws FileTemplifyException {
		if (placeholders == null || placeholders.isEmpty()) {
			throw new FileTemplifyException("No placeholders defined!", null);
		}
		this.placeholders = placeholders;
		this.fileNameTemplate = fileNameTemplate;
		this.fileContentTemplate = fileContentTemplate;
		this.folderNameTemplate = folderNameTemplate;
	}

	public String templify(String s, FileTemplifyResourceType fileTemplifyResourceType) throws FileTemplifyException {
		if (fileTemplifyResourceType == null) {
			throw new FileTemplifyException("No FileTemplify resource type specified", null);
		}
		if (s != null && !s.trim().isEmpty()) {
			String result = new String(s);
			result = resolvePlaceholders(result, fileTemplifyResourceType);
			return result;
		} else {
			return null;
		}
	}

	private String resolvePlaceholders(String s, FileTemplifyResourceType fileTemplifyResourceType)
			throws FileTemplifyException {
		String result = new String(s);
		for (Placeholder placeHolder : placeholders) {
			if (placeHolder.getKey() == null || placeHolder.getKey().trim().isEmpty()) {
				throw new FileTemplifyException("Placeholder without key defined!", null);
			}
			String value = placeHolder.getValue();
			if (value == null || value.trim().isEmpty()) {
				value = placeHolder.getKey();
			}
			result = result.replaceAll(placeHolder.getKey(), getReplacementByTemplate(value, fileTemplifyResourceType));
		}

		return result;
	}

	private String getReplacementByTemplate(String value, FileTemplifyResourceType fileTemplifyResourceType) throws FileTemplifyException {
		String replace = null;
		switch (fileTemplifyResourceType) {
		case FILE_NAME:
			replace = fileNameTemplate;
			break;
		case FILE_CONTENT:
			replace = fileContentTemplate;
			break;
		case FOLDER_NAME:
			replace = folderNameTemplate;
			break;
		}
		return RegexUtil.replace(value, "^(?<key>.*)$", replace);
	}
}
