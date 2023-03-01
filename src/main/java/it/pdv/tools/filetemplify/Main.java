package it.pdv.tools.filetemplify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import it.pdv.tools.filetemplify.templify.FileTemplify;

public class Main {
	
	public static void main(String[] args) throws IOException, FileTemplifyException {
		String configFilePath = args[0];
		InputStream input = new FileInputStream(configFilePath);
		FileTemplifyConfig fileTemplifyConfig = FileTemplifyConfigurationLoader.loadConfiguration(input);
		FileTemplify fileTemplify = new FileTemplify(fileTemplifyConfig);
		fileTemplify.templify();
	}
	
}
