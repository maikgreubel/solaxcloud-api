package com.solaxcloud.api;

/**
 * Package related exception class for all notable errors.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public class SolaxCloudException extends Exception {

	private static final long serialVersionUID = -3275761827050618578L;

	/**
	 * Create a new instance of {@link SolaxCloudException} and provide a message.
	 * 
	 * @param message the text to provide as exception message
	 */
	public SolaxCloudException(final String message) {
		super(message);
	}

	/**
	 * Create a new instance of {@link SolaxCloudException} and embed an existing
	 * exception.
	 * 
	 * @param t the existing exception to embed
	 */
	public SolaxCloudException(final Throwable t) {
		super(t);
	}
}
