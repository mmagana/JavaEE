package cl.bancochile.ws.client.generic.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Lorgio Trinidad, Marcelo Magaña
 *
 */
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
    
    /**
     * Obtiene el contenido del nodo como Object, buscado a partir de su nombre
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre del objeto a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @param returnType el tipo de objeto a buscar, una de las constantes definidas en <code>javax.xml.xpath.XPathConstants</code>
     * @return
     * @throws Exception 
     * @see javax.xml.xpath.XPathConstants
     */
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
    
    /**
     * Obtiene el nodo a partir de su nombre incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static Node getNode(final Node context, final String tag) throws Exception {
        return getNode(context, tag, false);
    }

    /**
     * Obtiene el nodo a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static Node getNode(final Node context, final String tag, boolean localName) throws Exception {
        return (Node) getTag(context, tag, localName, XPathConstants.NODE);
    }
    
    /**
     * Obtiene el listado de nodos a partir de su nombre incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de los nodos a buscar
     * @return
     * @throws Exception
     */
    public static NodeList getNodeSet(final Node context, final String tag) throws Exception {
        return (NodeList) getTag(context, tag, false, XPathConstants.NODESET);
    }
    
    /**
     * Obtiene el listado de nodos a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de los nodos a buscar
     * @param localName true para utilizar el nombre local de los nodos e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static NodeList getNodeSet(final Node context, final String tag, boolean localName) throws Exception {
        return (NodeList) getTag(context, tag, localName, XPathConstants.NODESET);
    }

    /**
     * Obtiene el contenido del nodo como booleano, buscado a partir de su
     * nombre incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static Boolean getTagBoolean(final Node context, final String tag) throws Exception {
        return getTagBoolean(context, tag, false);
    }

    /**
     * Obtiene el contenido del nodo como Boolean, buscado a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static Boolean getTagBoolean(final Node context, final String tag, boolean localName) throws Exception {
        //al intentar utilizar XPathConstants.BOOLEAN no se obtiene el contenido real del tag,
        //en su lugar solo se comprueba la existencia del nodo hijo
        //@see com.sun.org.apache.xpath.internal.objects.XNodeSet.bool();
        return Boolean.valueOf(getTagText(context, tag, localName));
    }

    /**
     * Obtiene el contenido del nodo como Date, buscado a partir de su nombre
     * incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static Date getTagDate(final Node context, final String tag) throws Exception {
        return getTagDate(context, tag, false);
    }

    /**
     * Obtiene el contenido del nodo como Date, buscado a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static Date getTagDate(final Node context, final String tag, boolean localName) throws Exception {
        String text = getTagText(context, tag, localName);
        if (text != null && !text.isEmpty()) {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            XMLGregorianCalendar xmlGregorianCalendar = factory.newXMLGregorianCalendar(text);
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }
        return null;
    }
    
    /**
     * Obtiene el contenido del nodo como Integer, buscado a partir de su nombre
     * incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static Integer getTagInteger(final Node context, final String tag) throws Exception {
        return getTagInteger(context, tag, false);
    }
    
    /**
     * Obtiene el contenido del nodo como Integer, buscado a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static Integer getTagInteger(final Node context, final String tag, boolean localName) throws Exception {
        String text = getTagText(context, tag, localName);
        if(text != null && !text.isEmpty()) {
            return Integer.parseInt(text);
        }
        return null;
    }
    
    /**
     * Obtiene el contenido del nodo como Long, buscado a partir de su nombre
     * incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static Long getTagLong(final Node context, final String tag) throws Exception {
        return getTagLong(context, tag, false);
    }
    
    /**
     * Obtiene el contenido del nodo como Long, buscado a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static Long getTagLong(final Node context, final String tag, boolean localName) throws Exception {
        String text = getTagText(context, tag, localName);
        if(text != null && !text.isEmpty()) {
            return Long.parseLong(text);
        }
        return null;
    }

    /**
     * Obtiene el contenido del nodo como String, buscado a partir de su nombre
     * incluyendo prefijos de namespace
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @return
     * @throws Exception
     */
    public static String getTagText(final Node context, final String tag) throws Exception {
        return getTagText(context, tag, false);
    }

    /**
     * Obtiene el contenido del nodo como String, buscado a partir de su nombre
     *
     * @param context el nodo contexto a partir del cual iniciar la busqueda
     * @param tag el nombre de nodo a buscar
     * @param localName true para utilizar el nombre local del nodo e ignorar
     * los prefijos de namespace, false para incluirlos
     * @return
     * @throws Exception
     */
    public static String getTagText(final Node context, final String tag, boolean localName) throws Exception {
        return (String) getTag(context, tag, localName, XPathConstants.STRING);
    }
}
