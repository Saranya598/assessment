package com.multichoice.test.core.entity;
import java.io.Serializable;

public class ResponseDto implements Serializable{

	private static final long serialVersionUID = 1611279449001695453L;
	private int statusCode;
	  private String message;

	  /**
	   * Default constructor.
	   *
	   */
	  public ResponseDto() {
	  }

	  /**
	   * Parameterized constructor.
	   *
	   */
	  public ResponseDto(int statusCode, String message) {
	    this.setStatusCode(statusCode);
	    this.setMessage(message);
	  }

	  /**
	   * Returns the status code of the request.
	   *
	   * @return the statusCode
	   */
	  public int getStatusCode() {
	    return statusCode;
	  }

	  /**
	   * Sets the status code of the request.
	   *
	   * @param badRequest the statusCode to set
	   */
	  public void setStatusCode(int badRequest) {
	    this.statusCode = badRequest;
	  }

	  /**
	   * Returns the message of the request.
	   *
	   * @return the message
	   */
	  public String getMessage() {
	    return message;
	  }

	  /**
	   * Sets the message of the request.
	   *
	   * @param message the message to set
	   */
	  public void setMessage(String message) {
	    this.message = message;
	  }

	  /*
	   * (non-Javadoc)
	   *
	   * @see java.lang.Object#toString()
	   */
	  @Override
	  public String toString() {
	    return "{" + "\"statusCode\":" + statusCode + "," + "\"message\":\"" + message + "\"" + "}";
	  }
}
