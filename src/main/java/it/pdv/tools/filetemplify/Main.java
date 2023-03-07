package it.pdv.tools.filetemplify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import it.pdv.tools.filetemplify.templify.FileTemplify;

public class Main {

	public static void main(String[] args) throws IOException, FileTemplifyException {
		if (args == null || args.length != 1) {
			throw new FileTemplifyException("Config file is not valid!");
		}
		String configFilePath = args[0];
		
		InputStream input = getConfigurationFileStream(configFilePath);
		FileTemplifyConfig fileTemplifyConfig = FileTemplifyConfigurationLoader.loadConfiguration(input);
		FileTemplify fileTemplify = new FileTemplify(fileTemplifyConfig);
		fileTemplify.templify();
	}

	private static InputStream getConfigurationFileStream(String configFilePath) throws FileTemplifyException {
		try {
			return new FileInputStream(configFilePath);
		} catch (Exception e) {
			throw new FileTemplifyException("Config file is not valid!", e);
		}
	}

}
