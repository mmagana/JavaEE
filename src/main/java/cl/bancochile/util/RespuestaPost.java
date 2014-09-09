package cl.bancochile.util;

import java.io.Serializable;

public class RespuestaPost implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private boolean success = Boolean.FALSE;
	private String response;
	
	public RespuestaPost(){
		
	}
	
	public RespuestaPost(boolean success, String response){
		this.success = success;
		this.response = response;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	

}
