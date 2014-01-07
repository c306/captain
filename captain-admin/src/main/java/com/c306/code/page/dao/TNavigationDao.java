package com.c306.code.page.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.c306.code.page.entity.TBar;
import com.c306.code.page.utils.ActionUtils;
import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.hbm.TNavigation;

@Repository
public class TNavigationDao extends HibernateEntityDao<TNavigation> {

	/**
	 * bar info query
	 * 
	 * @param global
	 * @return
	 * @throws JSONException 
	 */
	public String queryBarInfo(String global) throws JSONException {
		DetachedCriteria dc = DetachedCriteria.forClass(TNavigation.class);
		dc.add(Restrictions.eq("status", 1));
		dc.addOrder(Order.asc("ordinal")).addOrder(Order.desc("updatetime"));

		List<TNavigation> list = super.findByDetachedCriteria(dc);
		List<TBar> rootList = new ArrayList<TBar>();
		for (TNavigation navtion : list) {
			if ("0".equals(navtion.getObjid())) {
				TBar bar = new TBar();
				bar.setName(ActionUtils.getLanStr(navtion.getName(),
						navtion.getEname(), global));
				bar.setId(navtion.getOid());
				bar.setObjid(navtion.getObjid());

				rootList.add(bar);
			}
		}
		TNavigation record = new TNavigation();
		record.getBarList().add(rootList);
		record = this.convertBar(record, rootList, list, global);

//		JSONArray itms = (JSONArray) JSONSerializer.toJSON(record.getBarList());
//		return itms.toString();
		return JSONUtil.serialize(record.getBarList(), null, null, false, false, true);
	}

	private TNavigation convertBar(TNavigation record, List<TBar> rootList,
			List<TNavigation> list, String global) {
		List<TBar> tList = new ArrayList<TBar>();
		for (TBar root : rootList) {
			for (TNavigation navtion : list) {
				if (navtion.getObjid().equals(root.getId())) {
					TBar bar = new TBar();
					bar.setName(ActionUtils.getLanStr(navtion.getName(),
							navtion.getEname(), global));
					bar.setId(navtion.getOid());
					bar.setObjid(navtion.getObjid());

					tList.add(bar);
				}
			}
		}
		if (tList.size() > 0) {
			record.getBarList().add(tList);
			this.convertBar(record, tList, list, global);
		}
		return record;
	}

}
