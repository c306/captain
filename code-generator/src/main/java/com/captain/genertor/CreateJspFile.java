package com.captain.genertor;

import java.util.HashMap;
import java.util.Map;

import com.c306.core.springsecurity.users.entity.SecUser;

public class CreateJspFile {




	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void create(Class<?> entityClass, Map<String, String> map) throws Exception {
		
        /* Create a data-model */
        Map root = Main.createRootMap(entityClass);
        
        
        //
        root.put("fields", EntityUtils.listProperties(entityClass, null));
        root.putAll(map);
        
        // create file
        Main.generateJsp(root, "list");
        Main.generateJsp(root, "edit");
	}



	public static void main(String[] args) throws Exception {

		Class<?> entityClass = SecUser.class;

		CreateJspFile.create(entityClass, new HashMap<String, String>(0));
        
	}
	
}
