package it.pdv.tools.filetemplify;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import it.pdv.tools.filetemplify.util.RegexUtil;

public class FileTemplifyFilter implements FileFilter {

	private List<String> excludes = new ArrayList<>();

	public FileTemplifyFilter(List<String> list) {
		if (list != null) {
			for (String exclude : list) {
				excludes.add(RegexUtil.wilcardToRegex(exclude));
			}
		}
	}

	@Override
	public boolean accept(File pathname) {
		String fileName = pathname.getName();
		for (String exclude : excludes) {
			if (RegexUtil.match(fileName, exclude)) {
				return false;
			}
		}
		return true;
	}

}
