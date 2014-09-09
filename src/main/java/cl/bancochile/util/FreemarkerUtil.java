package cl.bancochile.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {

	private static final Logger LOGGER = Logger.getLogger(FreemarkerUtil.class.getName());
    private static FreemarkerUtil unico = new FreemarkerUtil();
    public static final String TEMPLATE_DIR = "/ws-template";
    private Configuration cfg;
    private boolean configLoaded = false;
    private String dir;

    private FreemarkerUtil() {
        dir = TEMPLATE_DIR;
        cfg = new Configuration();
        try {
            cfg.setClassForTemplateLoading(FreemarkerUtil.class,TEMPLATE_DIR);
            configLoaded = true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * @return
     */
    public static FreemarkerUtil getInstance() {
        return unico;
    }

    /**
     * @param dir
     */
    public void setTemplateDir(String dir) {
        this.dir = dir;
        try {
            File path = new File(dir);
            cfg.setDirectoryForTemplateLoading(path);
            configLoaded = true;
        } catch (Exception e) {
            configLoaded = false;
            LOGGER.error(e);
        }
    }

    /**
     * @return
     */
    public String getTemplateDir() {
        return dir;
    }

    /**
     * @param template
     * @param dataModel
     * @return
     */
    public String fillTemplate(String template, Map<String, Object> dataModel) {
        String response = "";
        if (configLoaded) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                Template t = cfg.getTemplate(template);
                t.process(dataModel, new PrintWriter(out));
                response = new String(out.toByteArray());
            } catch (Exception e) {
                LOGGER.error(e);
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        } else {
            LOGGER.error("Template no cargado: "+template);
        }
        return response;
    }
    
    public static String fillTemplate(String templateDir, String template, Map<String, Object> dataModel) {
        Configuration config = new Configuration();
        boolean configLoaded = false;
        try {
            config.setClassForTemplateLoading(FreemarkerUtil.class, templateDir);
            configLoaded = true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        
        String response = "";
        if (configLoaded) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                Template t = config.getTemplate(template);
                t.process(dataModel, new PrintWriter(out));
                response = new String(out.toByteArray());
            } catch (Exception e) {
                LOGGER.error(e);
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        } else {
            LOGGER.error("Template no cargado: "+templateDir+" / "+template);
        }
        return response;
    }

    /**
     * @param template
     * @param dataModel
     * @return
     */
    public String fillTemplate(String template, Object dataModel) {
        LOGGER.debug("fillTemplate configLoaded "+configLoaded);
        String response = "";
        if (configLoaded) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                Template t = cfg.getTemplate(template);
                t.process(dataModel, new PrintWriter(out));
                response = new String(out.toByteArray());
            } catch (Exception e) {
                LOGGER.error(e.getMessage() , e);
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage() , e);
                }
            }
        } else {
            LOGGER.error("Template no cargado: " + template);
        }
        return response;
    }
}
