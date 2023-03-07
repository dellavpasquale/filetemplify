package it.pdv.tools.filetemplify;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class FileTemplifyConfigurationLoader {

	public static FileTemplifyConfig loadConfiguration(InputStream inputStream) throws FileTemplifyException {
		FileTemplifyConfig config = null;
		try {
			Yaml yaml = new Yaml();
			config = yaml.loadAs(inputStream, FileTemplifyConfig.class);
		} catch (Exception e) {
			throw new FileTemplifyException("Config file is not valid!", e);
		}
		if(config == null) {
			throw new FileTemplifyException("Config file is not valid!");
		}
		return config;
	}
}
