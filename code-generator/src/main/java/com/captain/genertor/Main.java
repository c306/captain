package com.captain.genertor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Main {
	
	public static final String BASE_DIR = new File("").getAbsolutePath().replaceAll("\\\\", "/").concat("/");
	public static final String TARGET_DIR = BASE_DIR.concat("target/");
	public static final String ENCODING = "utf-8";
	
	public static final Configuration cfg;
	static {
        try {
    		/* Create and adjust the configuration */
    		cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Main.BASE_DIR + "src/main/resources/ftls"));
			cfg.setDefaultEncoding(Main.ENCODING);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static synchronized Configuration getConfiguration() throws IOException {
		
        /*Create and adjust the configuration 
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(Main.BASE_DIR + "src/main/resources/ftls"));
        cfg.setDefaultEncoding(Main.ENCODING);
        cfg.setObjectWrapper(new DefaultObjectWrapper());*/
        
        return cfg;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map createRootMap(Class<?> entityClass) throws Exception {
        
		// e.g.  com.c306.core.springsecurity.users.entity.SecUser
		String modelPackage = entityClass.getName().substring(0, entityClass.getName().indexOf(".entity"));
		String modelName = entityClass.getName().substring(0, entityClass.getName().indexOf(".entity"));
		modelName.substring(modelName.lastIndexOf('.'));
		
		String entityName = entityClass.getName().substring(entityClass.getName().lastIndexOf('.')+1);
		
        /* Create a data-model */
        Map root = new HashMap();
        root.put("modelName", modelName);
        root.put("modelPackage", modelPackage);
        root.put("entityName", entityName);
        
        return root;
	}
	
	
	public static void generateAction(Map<String, String> root) throws Exception {
		generate(root, "action");
	}
	
	public static void generateService(Map<String, String> root) throws Exception {
		generate(root, "service");
	}
	
	public static void generateDao(Map<String, String> root) throws Exception {
		generate(root, "dao");
	}
	/**
	 * 
	 * @param root
	 * @param fileType list, edit
	 * @throws Exception
	 */
	public static void generateResource(Map<String, String> root) throws Exception {
		
        /* Get or create a template */
        Template temp = Main.cfg.getTemplate("action/action-i18n.ftl");
        
        System.out.println("create Resource ...");
        
        /* Merge data-model with template */
        File file = new File(Main.TARGET_DIR.concat(root.get("modelName")).concat("/action/").concat(root.get("entityName")).concat("Action.properties"));
        file.getParentFile().mkdirs();
        Writer out = new OutputStreamWriter(new FileOutputStream(file), Main.ENCODING);
        temp.process(root, out);
        out.flush();
        
        System.out.println("create " + file.getPath() + " success.");
        
        // copy zh_cn
        File destFile = new File(Main.TARGET_DIR.concat(root.get("modelName")).concat("/action/").concat(root.get("entityName")).concat("Action_zh_CN.properties"));
        FileUtils.copyFile(file, destFile);
        
	}
	
	/**
	 * 
	 * @param root
	 * @param fileType list, edit
	 * @throws Exception
	 */
	public static void generateJsp(Map<String, String> root, String fileType) throws Exception {
		
		// validate
		if( StringUtils.isBlank(root.get("entityPkName")) ) {
			throw new Exception("entityPkName is not correctly.");
		}
		if( StringUtils.isBlank(root.get("nameSpace")) ) {
			throw new Exception("nameSpace is not correctly.");
		}
		if( StringUtils.isBlank(root.get("actionName")) ) {
			throw new Exception("actionName is not correctly.");
		}
		
		String suffix;
		if (fileType.equals("list"))
			suffix = "list";
		else if (fileType.equals("edit"))
			suffix = "edit";
		else 
			throw new Exception("fileType is not correctly.");
		
        /* Get or create a template */
        Template temp = Main.cfg.getTemplate("jsp".concat("/").concat(fileType).concat(".ftl"));
        
        System.out.println("create " + fileType + " jsp ...");
        
        /* Merge data-model with template */
        File file = new File(Main.TARGET_DIR.concat(root.get("modelName")).concat("/jsp/").concat(suffix).concat("_").concat(root.get("entityName")+".jsp"));
        file.getParentFile().mkdirs();
        Writer out = new OutputStreamWriter(new FileOutputStream(file), Main.ENCODING);
        temp.process(root, out);
        out.flush();
        
        System.out.println("create " + file.getPath() + " success.");
        
	}
	
	/**
	 * 
	 * @param root
	 * 			map
	 * @param fileType
	 * 			dao, service, action
	 * @throws Exception
	 */
	public static void generate(Map<String, String> root, String fileType) throws Exception {
		
		String suffix;
		if (fileType.equals("action"))
			suffix = "Action";
		else if (fileType.equals("service"))
				suffix = "Service";
		else if (fileType.equals("dao"))
			suffix = "Dao";
		else 
			throw new Exception("fileType is not correctly.");
        
        /* Get or create a template */
        Template temp = Main.cfg.getTemplate(fileType.concat("/").concat(fileType).concat(".ftl"));
        
        System.out.println("create " + fileType + " ...");
        
        /* Merge data-model with template */
        File file = new File(Main.TARGET_DIR.concat(root.get("modelName")).concat("/").concat(fileType).concat("/").concat(root.get("entityName")).concat(suffix+".java"));
        file.getParentFile().mkdirs();
        Writer out = new OutputStreamWriter(new FileOutputStream(file), Main.ENCODING);
        temp.process(root, out);
        out.flush();
        
        System.out.println("create " + file.getPath() + " success.");
        
	}



}
