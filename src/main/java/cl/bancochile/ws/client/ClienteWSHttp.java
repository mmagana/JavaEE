package cl.bancochile.ws.client;

import static cl.bancochile.util.XpathUtils.getRootElement;
import static cl.bancochile.util.XpathUtils.getTag;
import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cl.bancochile.util.FreemarkerUtil;
import cl.bancochile.util.HttpUtils;
import cl.bancochile.util.RespuestaPost;
import cl.bancochille.ws.model.Cuenta;

public class ClienteWSHttp {
	
	private static final Logger LOGGER = Logger.getLogger(ClienteWSHttp.class.getName());
	private static final String URL_DATOS_FICHA = "http://200.14.147.24:8011/RenovacionInternet/CS000191_ObtenerDatosFichaChica?wsdl";
	private static final String URL_SALDO_CUENTA_CORRIENTE = "http://200.14.147.135:8011/RenovacionInternet/CS000162_ConsultarSaldoCuentas?wsdl";
	
	public static void main(String[] args){
		//sendRequestSaldoCuentaCorriente();
		sendRequestDatosFicha();
	}
	
	public static void sendRequestSaldoCuentaCorriente(){
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas.add(new Cuenta("CTD", "644467007"));
		cuentas.add(new Cuenta("CTD", "370014903"));
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("idApp", "CS000165CA2010005040953");
        dataModel.put("usuario", "LHC");
        dataModel.put("idTransaccionNegocio", "cpr191_0605001");
        dataModel.put("fechaHora", "2014-03-24T14:27:54.461Z");
        dataModel.put("canal", "INTERNET_P");
        dataModel.put("cuentas", cuentas);

        String soapMsg = FreemarkerUtil.getInstance().fillTemplate("consulta-saldo-cuenta-corriente.ftl", dataModel);
        
        System.out.println(soapMsg);

        //RespuestaPost respuestaPost = doPost(URL_SALDO_CUENTA_CORRIENTE, soapMsg);
	}

	public static void sendRequestDatosFicha(){
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("idApp", "CS000165CA2010005040953");
        dataModel.put("usuario", "LHC");
        dataModel.put("idTransaccionNegocio", "cpr191_0605001");
        dataModel.put("fechaHora", "2014-03-24T14:27:54.461Z");
        dataModel.put("canal", "INTERNET_P");
        dataModel.put("rutClient", "10836406-8");

        String soapMsg = FreemarkerUtil.getInstance().fillTemplate("obtener-datos-ficha.ftl", dataModel);
        
        System.out.println(soapMsg);

        RespuestaPost respuestaPost = doPost(URL_DATOS_FICHA, soapMsg);
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        
        try {
//			xPathExpression = xpath.compile("//*[local-name()='Cuerpo']");
			Element element = getRootElement(respuestaPost.getResponse());
			
			Node node = (Node)getTag(element, "Cuerpo", true, NODE);
			
			String rutCliente = (String)getTag(node, "oficinaEjecutivo", true, STRING);
			LOGGER.info(rutCliente);
		} catch (XPathExpressionException e) {
			LOGGER.error(e);
		} catch (Exception e) {
			LOGGER.error(e);
		}
        
	}
	
	private static RespuestaPost doPost(final String url, final String soapMsg) {

        long start = System.nanoTime();
        RespuestaPost respuestaPost = HttpUtils.excutePost(url, soapMsg);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Elapsed: " + (System.nanoTime() - start) / 1000000000);
        }

        return respuestaPost;
    }
}
