package com.captain.genertor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.c306.core.springsecurity.resources.entity.SecResc;

import freemarker.template.Template;

public class Startup {
	

	public static void main(String[] args) throws Exception {

        Map<String, String> root = new HashMap<String, String>(2);
        // struts action configure
        root.put("nameSpace", "acegi");// action nameSpace
        root.put("actionName", "resc");// action name
        root.put("entityPkName", "id");// entity primary key name
		// Entity Class
		Class<?> entityClass = SecResc.class;// Entity Class Must be set.

        

		// create files
		CreateDaoFile.create(entityClass);
		CreateServiceFile.create(entityClass);
		CreateActionFile.create(entityClass);
		
		// create jsp file
		CreateJspFile.create(entityClass, root);
		
		//
		capitaliseAllWords("aasdf asdf asdf  ASDFA Sa  dkkd");
        
	}
	
	
	

	  public static String capitaliseAllWords(String str)
	  {
	    if (str == null) {
	      return null;
	    }
	    int sz = str.length();
	    StringBuffer buffer = new StringBuffer(sz);
	    boolean space = true;
	    for (int i = 0; i < sz; ++i) {
	      char ch = str.charAt(i);
	      if (Character.isWhitespace(ch)) {
	        buffer.append(ch);
	        space = true;
	      }
	      else if (space) {
	        buffer.append(Character.toTitleCase(ch));
	        space = false;
	      }
	      else {
	        buffer.append(ch);
	      }
	    }
	    
	    System.out.println(buffer);
	    
	    return buffer.toString();
	  }
		
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void test(String[] args) throws Exception {

		
		System.out.println("current runtime dir: " + new File("").getAbsolutePath().replaceAll("\\\\", "/"));
		System.out.println("temp.io" + System.getProperty("temp.io"));
		

        /* ------------------------------------------------------------------- */    
        /* You usually do these for many times in the application life-cycle:  */    
                
        /* Get or create a template */
        Template temp = Main.cfg.getTemplate("test.ftl");

        /* Create a data-model */
        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(new FileOutputStream(Main.TARGET_DIR + "test.java"), Main.ENCODING);
        temp.process(root, out);
        out.flush();
        
	}
	
	
	
	

}
