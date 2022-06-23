package http;

import kong.unirest.json.JSONObject;


public class Response {
	
	private int code = 0;
	private String message = "success";
	private Object data;
	
	public JSONObject generateResponse () {
		return new JSONObject(this);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	

}
