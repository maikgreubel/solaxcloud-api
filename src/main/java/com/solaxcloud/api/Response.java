package com.solaxcloud.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This class is a response wrapper object which represents the return json from
 * a solax cloud api call.
 * <p>
 * It embeds the data payload and provides a flag for positive or negative
 * result as well as an eventually occured exception.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = { "success", "exception", "result" })
public class Response {

	private String success;

	private String exception;

	private RealTimeStatus result;

	/**
	 * @param success the success to set
	 */
	public final void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * @return the exception
	 */
	public final String getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public final void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @return the result
	 */
	public final RealTimeStatus getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public final void setResult(RealTimeStatus result) {
		this.result = result;
	}

	/**
	 * Retrieve the success string as boolean value for convenience.
	 * 
	 * @return true in case of success, false otherwise
	 */
	public boolean isSuccess() {
		return Boolean.parseBoolean(success);
	}

}
