/**
 * 
 */
package cl.bancochile.ws.client.generic.consumer;

import java.util.List;
import java.util.Map;

import cl.bancochile.ws.client.generic.exception.ClienteWebServiceException;

/**
 * 
 * Clase <pre>WebServiceClientImpl</pre> sirve como cliente Web Service
 * genérico, respondiendo las solicitudes entrantes, y capaz de apuntar a cualquier servicio web,
 * Esta clase es la implementación de la Interfaz WebServiceClient
 * {@link WebServiceClient}
 * 
 * @author Marcelo Magaña Silva
 *
 * @param <E> - Corresponde al objeto que se requiere como Response.
 */
public interface WebServiceClient<E> {
	/**
	 * Método sendRequest permite enviar un mensaje soap hacia cualquier servicio web SOAP, a su vez es capaz de retornar el response del servicio, encapsulado según el objeto definido en el parámetro <pre>nodo</pre>
	 * 
	 * @param soapMsg - String que contiene el mensaje SOAP de request para enviar.
	 * @param urlServicio - URL que apunta al contrato wsdl del servicio que se desea consultar.
	 * @param nodo - Objeto que corresponde al tipo de dato que se necesita para el response.
	 * @return Reporta un List de objetos del mismo tipo de <pre>nodo</pre>, que corresponden al conjunto de valores de dicho tipo retornados por el servicio
	 * @throws ClienteWebServiceException
	 */
	List<E> sendRequest(String soapMsg, String urlServicio, E nodo) throws ClienteWebServiceException;
}
