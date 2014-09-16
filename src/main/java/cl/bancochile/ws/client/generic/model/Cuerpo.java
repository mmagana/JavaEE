package cl.bancochile.ws.client.generic.model;

public class Cuerpo {

	 private String codigoRetorno;
	 
	 private String rutCliente;
	 
	 private String nombre;
	 
	 
	/**
	 * @return the codigoRetorno
	 */
	public String getCodigoRetorno() {
		return codigoRetorno;
	}
	/**
	 * @param codigoRetorno the codigoRetorno to set
	 */
	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
	/**
	 * @return the rutCliente
	 */
	public String getRutCliente() {
		return rutCliente;
	}
	/**
	 * @param rutCliente the rutCliente to set
	 */
	public void setRutCliente(String rutCliente) {
		this.rutCliente = rutCliente;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoRetorno == null) ? 0 : codigoRetorno.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((rutCliente == null) ? 0 : rutCliente.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuerpo other = (Cuerpo) obj;
		if (codigoRetorno == null) {
			if (other.codigoRetorno != null)
				return false;
		} else if (!codigoRetorno.equals(other.codigoRetorno))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (rutCliente == null) {
			if (other.rutCliente != null)
				return false;
		} else if (!rutCliente.equals(other.rutCliente))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cuerpo [codigoRetorno=" + codigoRetorno + ", rutCliente="
				+ rutCliente + ", nombre=" + nombre + "]";
	}
	
	
	
	
	
	/**
	 * <opob:Cuerpo xmlns:opob="http://osb.bancochile.cl/ESB/RenovacionInternet/ObtenerDatosFichaChica/OpObtenerResponse">
               <opob:codigoRetorno/>
               <opob:>15661898-5</opob:rutCliente>
               <opob:nombre>Sin Informacion</opob:nombre>
               <opob:apellidoPaterno/>
               <opob:apellidoMaterno/>
               <opob:oficinaEjecutivo/>
               <opob:codigoEjecutivo/>
               <opob:nombreEjecutivo/>
               <opob:apellidoPaternoEjecutivo/>
               <opob:apellidoMaternoEjecutivo/>
               <opob:emailEjecutivo/>
               <opob:codigoPaisEjecutivo/>
               <opob:codigoCiudadEjecutivo/>
               <opob:telefono/>
               <opob:segmento/>
               <opob:marca/>
               <opob:banca/>
               <opob:clasificacionRiesgo/>
               <opob:actividadEconomica/>
               <opob:categoria/>
               <opob:codigoSegmento/>
               <opob:codigoMarca/>
               <opob:codigoBanca/>
               <opob:codigoActividadEconomica/>
               <opob:nombreOficina/>
               <opob:tipoCliente/>
               <opob:sexo/>
               <opob:direccion/>
               <opob:numeroDireccion/>
               <opob:complementoDireccion/>
               <opob:comuna/>
               <opob:ciudad/>
               <opob:region/>
               <opob:pais/>
               <opob:tipoDespacho/>
               <opob:cui/>
               <opob:codigoCategoria/>
               <opob:rowIdDireccion/>
               <opob:CodigoPaisFonoPartCliente/>
               <opob:CodigoCiudadPartCliente/>
               <opob:FonoParticularCliente/>
               <opob:CodigoPaisCelularCliente/>
               <opob:CodigoCiudadCelularCliente/>
               <opob:CelularParticularCliente/>
               <opob:EmailParticularCliente/>
               <opob:CodigoPaisFonoComCliente/>
               <opob:CodigoCiudadFonoComCliente/>
               <opob:FonoComercialCliente/>
               <opob:EmailComercialCliente/>
               <opob:EmailAlternativoEjecutivo/>
            </opob:Cuerpo>

	 */
}
