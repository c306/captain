package com.c306.code.article.sn;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.TableHiLoGenerator;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import com.c306.hbm.TArticle;

public class ArticleSerialNumberGenerator extends TableHiLoGenerator {


	@Override
	public void configure(Type type, Properties params, Dialect d) {
		super.configure(new LongType(), params, d);
	}
	
	
	@Override
	public synchronized Serializable generate(final SessionImplementor session, Object obj) {
		
		TArticle entity = (TArticle)obj;
		
		return String.format("%1$tY%1$tm%1$td%2$04d", entity.getCreatetime(), super.generate(session, null));
	}
}
