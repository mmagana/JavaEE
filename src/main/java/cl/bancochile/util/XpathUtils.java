package cl.bancochile.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XpathUtils {

private static final Logger LOGGER = Logger.getLogger(XpathUtils.class);
    
    /**
     * Obtiene el nodo raiz del documento xml
     *
     * @param respuestaPost
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Element getRootElement(final String respuestaPost) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(respuestaPost.getBytes())).getDocumentElement();
    }
    
    public static Object getTag(final Node context, final String tag, boolean localName, QName returnType) throws Exception {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            //Busca el tag A PARTIR del nodo de contexto (.)
            String path;
            if (localName) {
                path = ".//*[local-name()='" + tag + "']";
            } else {
                path = ".//" + tag;
            }
            if(returnType != XPathConstants.NODE && returnType != XPathConstants.NODESET) {
                path += "/text()";
            }
            XPathExpression xPathExpression = xpath.compile(path);
            Object val = xPathExpression.evaluate(context, returnType);
            if (val != null) {
                return val;
            }
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        throw new Exception(String.format("Valor recuperado para el tag: %s es null", tag));
    }
}
