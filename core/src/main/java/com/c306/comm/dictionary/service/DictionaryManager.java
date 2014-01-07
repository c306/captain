package com.c306.comm.dictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.comm.dictionary.dao.DictionaryDao;
import com.c306.comm.dictionary.entity.Dictionary;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;

@Service
public class DictionaryManager extends HibernateEntityManager<Dictionary> {
	
	@Autowired
	private DictionaryDao dao;

	public List<Dictionary> retrieveList(String key) {
		return dao.retrieveList(key);
	}

	public List<Dictionary> query(Dictionary domain) {
		return this.dao.query(domain);
	}

	@Override
	protected IEntityDao<Dictionary> getDao() {
		return this.dao;
	}

}
