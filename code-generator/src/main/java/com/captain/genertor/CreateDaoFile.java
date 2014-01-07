package com.captain.genertor;

import java.util.Map;

public class CreateDaoFile {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void create(Class<?> entityClass) throws Exception {
		
        /* Create a data-model */
        Map root = Main.createRootMap(entityClass);
        
        
        
        // create file
        Main.generateDao(root);
	}

}
