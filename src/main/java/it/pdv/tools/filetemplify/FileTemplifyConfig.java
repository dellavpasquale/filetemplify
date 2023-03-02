package it.pdv.tools.filetemplify;

import java.util.List;

public class FileTemplifyConfig {

	private String fileNameTemplate;
	private String fileContentTemplate;
	private String folderNameTemplate;
    private List<Folder> folders;
    private List<Placeholder> placeholders;
    private List<String> excludes;

    public String getFileNameTemplate() {
		return fileNameTemplate;
	}

	public void setFileNameTemplate(String fileNameTemplate) {
		this.fileNameTemplate = fileNameTemplate;
	}

	public String getFileContentTemplate() {
		return fileContentTemplate;
	}

	public void setFileContentTemplate(String fileContentTemplate) {
		this.fileContentTemplate = fileContentTemplate;
	}

	public String getFolderNameTemplate() {
		return folderNameTemplate;
	}

	public void setFolderNameTemplate(String folderNameTemplate) {
		this.folderNameTemplate = folderNameTemplate;
	}

	public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }

	public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public static class Folder {
        private String path;
        private String destination;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }
    }
    
    public static class Placeholder {
    	private String key;
    	private String value;
    	
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}
	}
    
}