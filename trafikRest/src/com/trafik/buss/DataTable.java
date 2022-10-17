package com.trafik.buss;

public class DataTable {

	private String StatusCode;
	private String Message;
	private int ExecutionTime;
	private ResponseData ResponseData;

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getExecutionTime() {
		return ExecutionTime;
	}

	public void setExecutionTime(int executionTime) {
		ExecutionTime = executionTime;
	}

	public ResponseData getResponseData() {
		return ResponseData;
	}

	public void setResponseData(ResponseData responseData) {
		ResponseData = responseData;
	}
}
