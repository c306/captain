package com.c306.code.article.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.hbm.TArticleCategory;

@Repository
public class ArticleCategoryDao extends HibernateEntityDao<TArticleCategory> {

	public List<TArticleCategory> findParents(String oid) {
		try {
			List<TArticleCategory> list = new ArrayList<TArticleCategory>();
			TArticleCategory entity;
			HashSet<String> set = new HashSet<String>();

			do {
				entity = this.get(oid);
				if (entity != null) {
					list.add(entity);
					oid = entity.getPid();
					if(!set.add(oid))
						throw new RuntimeException("pid 出现循环引用。");
				}
			} while (entity != null && StringUtils.isNotBlank(entity.getPid()));

			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public String findNodePath(String oid) {
		StringBuilder sb = new StringBuilder("|");
		List<TArticleCategory> list = findParents(oid);
		for (int i = list.size() - 1; i >= 0; i--) {
			sb.append(String.format("%1$03d|",
					Long.valueOf(list.get(i).getOid())));
		}
		return sb.toString();
	}

}
