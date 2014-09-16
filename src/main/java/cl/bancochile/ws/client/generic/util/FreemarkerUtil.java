package cl.bancochile.ws.client.generic.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {

	private static final Logger LOGGER = Logger.getLogger(FreemarkerUtil.class
			.getName());
	//public static final String TEMPLATE_DIR = "/ws-template";
	private static FreemarkerUtil unico;
	private Configuration cfg;
	private boolean configLoaded = false;
	private String dir;

//	private FreemarkerUtil(Class cls, String ruta) {
//		dir = ruta;
//		cfg = new Configuration();
//		try {
//			cfg.setClassForTemplateLoading(cls, ruta);
//			configLoaded = true;
//		} catch (Exception e) {
//			LOGGER.error(e);
//		}
//	}
	
	private FreemarkerUtil() {
		cfg = new Configuration();
		try {
			configLoaded = true;
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * @return
	 */
	public static FreemarkerUtil getInstance() {
		if (unico == null) {
			unico = new FreemarkerUtil();
		}
		return unico;
	}

//	/**
//	 * @return
//	 */
//	public static FreemarkerUtil getInstance(Class cls, String ruta) {
//		if (unico == null) {
//			unico = new FreemarkerUtil(cls, ruta);
//		}
//		return unico;
//	}

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
	 * @deprecated desde versión 0.0.1
	 */
	@Deprecated()
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
			LOGGER.error("Template no cargado: " + template);
		}
		return response;
	}

	/**
	* @deprecated desde versión 0.0.1
	 */
	@Deprecated()
	public static String fillTemplate(String templateDir, String template,
			Map<String, Object> dataModel) {
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
			LOGGER.error("Template no cargado: " + templateDir + " / "
					+ template);
		}
		return response;
	}

	/**
	 * @param template
	 * @param dataModel
	 * @return
	* @deprecated desde versión 0.0.1
	 */
	@Deprecated()
	public String fillTemplate(String template, Object dataModel) {
		LOGGER.debug("fillTemplate configLoaded " + configLoaded);
		String response = "";
		if (configLoaded) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				Template t = cfg.getTemplate(template);
				t.process(dataModel, new PrintWriter(out));
				response = new String(out.toByteArray());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				try {
					out.close();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		} else {
			LOGGER.error("Template no cargado: " + template);
		}
		return response;
	}
	
	/**
	 * @param template
	 * @param dataModel
	 * @return
	 */
	public String fillTemplate(Template template, Map<String, Object> dataModel) {
		String response = "";
		if (configLoaded) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				//Template t = cfg.getTemplate(template);
				template.process(dataModel, new PrintWriter(out));
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
			LOGGER.error("Template no cargado: " + template.getName());
		}
		return response;
	}

	/**
	 * @return the cfg
	 */
	public Configuration getCfg() {
		return cfg;
	}

	/**
	 * @param cfg the cfg to set
	 */
	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

	/**
	 * @return the configLoaded
	 */
	public boolean isConfigLoaded() {
		return configLoaded;
	}

	/**
	 * @param configLoaded the configLoaded to set
	 */
	public void setConfigLoaded(boolean configLoaded) {
		this.configLoaded = configLoaded;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
}
