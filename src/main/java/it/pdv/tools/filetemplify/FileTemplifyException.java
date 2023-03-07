package it.pdv.tools.filetemplify;

public class FileTemplifyException extends Exception {

	private static final long serialVersionUID = -2103688511350215326L;

	public FileTemplifyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FileTemplifyException(String message) {
		super(message, null);
	}

}
