package com.c306.code.order.service;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.c306.code.order.dao.TOrderInfoDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TOrderinfo;

@Service
public class TOrderInfoService extends HibernateEntityManager<TOrderinfo> {

	@Autowired
	private TOrderInfoDao dao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	/**
	 * insert info
	 * 
	 * @param entity
	 * @param type
	 */
	public void insertInfo(TOrderinfo entity, String type) {
		int isSend = 0;
		try {
			this.sendEmail(entity, type);
			isSend = 1;
		} catch (MessagingException me) {
			logger.error("Email send error," + me);
		}
		entity.setIssend(isSend);
		this.dao.save(entity);
	}

	/**
	 * 
	 * @param entity
	 * @param type
	 * @return
	 * @throws MessagingException
	 */
	private boolean sendEmail(TOrderinfo entity, String type)
			throws MessagingException {
		MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		String from = mailSender.getJavaMailProperties().getProperty(
				"send.from");
		String to = "";
		if (StringUtils.isBlank(type)) {
			to = mailSender.getJavaMailProperties().getProperty("send.product");
		} else {
			to = mailSender.getJavaMailProperties().getProperty("send.contact");
		}
		mimeMessage.setFrom(new InternetAddress(from));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(
				to.trim()));
		mimeMessage.setSubject(entity.getTitle());

		Multipart multipart = new MimeMultipart();
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(entity.getNote(), "TEXT/HTML; CHARSET=UTF-8");
		multipart.addBodyPart(contentPart);

		mimeMessage.setContent(multipart);
		mailSender.send(mimeMessage);
		logger.info("Email send success, Recipients : " + to);

		return true;
	}

	@Override
	protected IEntityDao<TOrderinfo> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
