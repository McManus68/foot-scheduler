package com.mcmanus.fs.mail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

public class MailPreparator implements MimeMessagePreparator {

	@Autowired
	VelocityEngine velocityEngine;

	private String mail;
	private String subject;
	private String text;
	private String from;

	public MailPreparator(String mail, String subject, String text) {
		this(mail,  subject,  text, "support-bv@itkweb.com");
	}

	public MailPreparator(String mail, String subject, String text, String from) {
		this.mail = mail;
		this.subject = subject;
		this.text = text;
		this.from = from;
	}

	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject(subject);
		message.setTo(mail);
		message.setFrom(from);
		message.setText(text, true);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
