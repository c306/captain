package ${modelPackage}.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.masonry.dao.${entityName}Dao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;
import ${modelPackage}.entity.${entityName};


@Service
public class ${entityName}Service extends HibernateEntityManager<${entityName}> {
	@Autowired
	private ${entityName}Dao dao;

	


	/*
	@Override
	public TMasonryBrick saveOrUpdate(TMasonryBrick obj) {
		// TODO Auto-generated method stub
		return super.saveOrUpdate(obj);
	}
	*/
	
	/*
	@Override
	public Page pagedQuery(QueryParameters query) {
		// TODO Auto-generated method stub
		return super.pagedQuery(query);
	}
	*/
	
	/*
	@Override
	public List<TMasonryBrick> find(String name, Object value) {
		// TODO Auto-generated method stub
		return super.find(name, value);
	}
	*/


	

}
