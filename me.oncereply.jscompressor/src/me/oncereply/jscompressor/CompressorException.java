package me.oncereply.jscompressor;

public class CompressorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompressorException() {

	}

	public CompressorException(String message) {
		super(message);
	}

	public CompressorException(Throwable e) {
		super(e);
	}

	public CompressorException(String message, Throwable e) {
		super(message, e);
	}
}
