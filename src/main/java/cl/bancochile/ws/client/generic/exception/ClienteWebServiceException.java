/**
 * 
 */
package cl.bancochile.ws.client.generic.exception;

/**
 * @author Marcelo Magaña
 *
 */
public class ClienteWebServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ClienteWebServiceException() {
		
	}

	/**
	 * @param message
	 */
	public ClienteWebServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ClienteWebServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ClienteWebServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ClienteWebServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
