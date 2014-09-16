package cl.bancochile.ws.client.generic.model;

import java.io.Serializable;

public class Cuenta implements Serializable {

	private String codigoProducto;
	private String numeroProducto;
	
	public Cuenta(String codigoProducto, String numeroProducto){
		this.codigoProducto = codigoProducto;
		this.numeroProducto = numeroProducto;
	}
	
	public Cuenta(){
		this.codigoProducto = codigoProducto;
		this.numeroProducto = numeroProducto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNumeroProducto() {
		return numeroProducto;
	}

	public void setNumeroProducto(String numeroProducto) {
		this.numeroProducto = numeroProducto;
	}
	
	
}
