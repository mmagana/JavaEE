/**
 * 
 */
package cl.bancochile.ws.client.generic.consumer.impl;

import static cl.bancochile.ws.client.generic.util.XpathUtils.getRootElement;
import static cl.bancochile.ws.client.generic.util.XpathUtils.getTag;
import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;
import static javax.xml.xpath.XPathConstants.STRING;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.bancochile.ws.client.generic.consumer.WebServiceClient;
import cl.bancochile.ws.client.generic.exception.ClienteWebServiceException;
import cl.bancochile.ws.client.generic.util.FreemarkerUtil;
import cl.bancochile.ws.client.generic.util.HttpUtils;
import cl.bancochile.ws.client.generic.util.RespuestaPost;


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
public class WebServiceClientImpl<E> implements WebServiceClient<E> {

	private E elemento;
	private final Collection<Integer> modifiers = new HashSet<Integer>();
    
	private static final Logger LOGGER = Logger.getLogger(WebServiceClientImpl.class
			.getName());

	/**
	 * Método sendRequest permite enviar un mensaje soap hacia cualquier servicio web SOAP, a su vez es capaz de retornar el response del servicio, encapsulado según el objeto definido en el parámetro <pre>nodo</pre>
	 * 
	 * @param soapMsg - String que contiene el mensaje SOAP de request para enviar.
	 * @param urlServicio - URL que apunta al contrato wsdl del servicio que se desea consultar.
	 * @param nodo - Objeto que corresponde al tipo de dato que se necesita para el response.
	 * @return Reporta un List de objetos del mismo tipo de <pre>nodo</pre>, que corresponden al conjunto de valores de dicho tipo retornados por el servicio
	 * @throws ClienteWebServiceException
	 */
	public List<E> sendRequest(String soapMsg, String urlServicio, E nodo) throws ClienteWebServiceException{
		this.elemento = nodo;
		modifiers.add(Modifier.TRANSIENT);
        modifiers.add(Modifier.STATIC);
		

		RespuestaPost respuestaPost = doPost(urlServicio, soapMsg);
		try {
			Element element = getRootElement(respuestaPost.getResponse());
			List<E> unmarshalledObject = extraerObjeto(nodo.getClass().getSimpleName(), element);
			
			return unmarshalledObject;			
			
		} catch (XPathExpressionException e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		} catch (ParserConfigurationException e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		} catch (SAXException e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		} catch (JAXBException e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new ClienteWebServiceException(e);
		}

	}

	private List<E> extraerObjeto(String nombreNodo, Element element) throws JAXBException,
			Exception {
		List<E> lista = new ArrayList<E>(); 
		NodeList node = (NodeList) getTag(element, nombreNodo, true, NODESET);
		for(int x=0; x<node.getLength();x++){
			Class<?> cls = Class.forName(elemento.getClass().getName());
			E elementoNuevo = (E) cls.newInstance();
			Field[] campos = cls.getDeclaredFields();
			for(Field f: campos){
				if(isStrictComplexElement(f.getType())){
					System.out.println(f.getType().toString());
					Node nodeChild = node.item(x);
					Class<?> cls1 = Class.forName(f.getType().getName());
					Object hijo = cls1.newInstance();
					Field[] camposHijo = cls1.getDeclaredFields();
					for(Field fi : camposHijo){
						if (!modifiers.contains(fi.getModifiers())) {
			                fi.setAccessible(true);
			                fi.set(hijo, (String)getTag(nodeChild, fi.getName(), true, STRING));
						}
					}
					if (!modifiers.contains(f.getModifiers())) {
		                f.setAccessible(true);
		                f.set(elementoNuevo, hijo);
					}
				}else{
					Node nodeChild = node.item(x);
					if (!modifiers.contains(f.getModifiers())) {
		                f.setAccessible(true);
		                f.set(elementoNuevo, (String)getTag(nodeChild, f.getName(), true, STRING));
					}
				}				
			}
			lista.add(elementoNuevo);			
		}	
		return lista;
	}
	
//	private boolean recursivo(Field f, Node node){
//		
//		if(isSomeSimpleElement(f.getClass())){
//			if (!modifiers.contains(f.getModifiers())) {
//                f.setAccessible(true);
//                
//			}
//			return false;
//		}else{
//			return recursivo(f);
//		}
//	}

	private RespuestaPost doPost(final String url, final String soapMsg) {

		long start = System.nanoTime();
		RespuestaPost respuestaPost = HttpUtils.excutePost(url, soapMsg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Elapsed: " + (System.nanoTime() - start) / 1000000000);
		}

		return respuestaPost;
	}
	
	private boolean isCollectionOrArray(Class<?> type) {
        return Collection.class.isAssignableFrom(type) || type.isArray();
    }

    private boolean isMap(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
    
    private boolean isSomeSimpleElement(Class<?> type)
    {
        return (type.isPrimitive() || type.isSynthetic() || type.isAssignableFrom(String.class));
    }
    
    private boolean isStrictComplexElement(Class<?> type)
    {
    	return (!type.isPrimitive() && !type.isSynthetic() &&
        		!(type.isAssignableFrom(String.class) || type.isAssignableFrom(Number.class)));
    }

}
