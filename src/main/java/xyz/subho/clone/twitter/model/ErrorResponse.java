package xyz.subho.clone.twitter.model;

import java.util.Date;

public class ErrorResponse {
	
	private Integer statusCode;
	private Date timestamp;
	private String message;
	private String description;
	
	/**
	 * @param statusCode
	 * @param timestamp
	 * @param message
	 * @param description
	 */
	public ErrorResponse(Integer statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

	/**
	 * @return the statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ErrorResponse [" + (statusCode != null ? "statusCode=" + statusCode + ", " : "")
				+ (timestamp != null ? "timestamp=" + timestamp + ", " : "")
				+ (message != null ? "message=" + message + ", " : "")
				+ (description != null ? "description=" + description : "") + "]";
	}

}
