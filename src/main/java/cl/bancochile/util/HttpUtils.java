package cl.bancochile.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpUtils {

	private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);

    public static final String SEP1 = ";";
    public static final String SEP2 = ",";

    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_CONTENT_TYPE = "Content-Type";
    public static final String REQUEST_CONNECTION = "Connection";
    public static final String REQUEST_CONTENT_LENGTH = "Content-Length";
    public static final String REQUEST_ACCEPT_ENCODING = "Accept-Encoding";

    public static final String CONTENT_TYPE_XML = "text/xml";
    public static final String CHARTSET_UTF8 = "charset=UTF-8";
    public static final String ACTION_NONE = "action=\"\"";
    public static final String KEEP_ALIVE = "Keep-Alive";
    public static final String ENCODING_GZIP = "gzip";
    public static final String ENCODING_DEFLATE = "deflate";

    private HttpUtils() {
    }

    public static RespuestaPost excutePost(final String targetURL, final String body) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("enter, \n- targetURL: %s \n- body: %s", targetURL, body));
        }

        RespuestaPost respuestaPost = new RespuestaPost(Boolean.FALSE, "Error desconocido");
        HttpURLConnection connection = null;

        try {

            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD_POST);
            connection.setRequestProperty(REQUEST_CONTENT_TYPE, CONTENT_TYPE_XML + SEP1 + CHARTSET_UTF8 + SEP1 + ACTION_NONE);
            connection.setRequestProperty(REQUEST_CONNECTION, KEEP_ALIVE);
            connection.setRequestProperty(REQUEST_CONTENT_LENGTH, "" + Integer.toString(body.getBytes().length));
            connection.setRequestProperty(REQUEST_ACCEPT_ENCODING, ENCODING_GZIP + SEP2 + ENCODING_DEFLATE);

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            connection.getErrorStream();

            int responseCode = connection.getResponseCode();
            switch (responseCode) {

                case 200:

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Connection OK, " + targetURL);
                    }

                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        responseBuilder.append(line);
                        responseBuilder.append('\r');
                    }

                    rd.close();

                    String response = responseBuilder.toString();

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("response: " + responseBuilder);
                    }

                    respuestaPost = new RespuestaPost(Boolean.TRUE, response);

                    break;

                default:

                    String errorMsg = "Error al ejecutar POST, Status Code " + responseCode;
                    LOGGER.error(errorMsg);
                    respuestaPost = new RespuestaPost(Boolean.FALSE, errorMsg);
            }

        } catch (MalformedURLException e) {
            LOGGER.error(e + "\n\t- targetURL : " + targetURL);
            respuestaPost = new RespuestaPost(Boolean.FALSE, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            LOGGER.error(e + "\n\t- targetURL : " + targetURL);
            respuestaPost = new RespuestaPost(Boolean.FALSE, "ProtocolException: " + e.getMessage());
        } catch (SocketException e) {
            LOGGER.error(e + "\n\t- targetURL : " + targetURL);
            respuestaPost = new RespuestaPost(Boolean.FALSE, "SocketException: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e + "\n\t- targetURL : " + targetURL);
            respuestaPost = new RespuestaPost(Boolean.FALSE, "IOException: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e + "\n\t- targetURL : " + targetURL);
            respuestaPost = new RespuestaPost(Boolean.FALSE, "IOException: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return respuestaPost;
    }
}
