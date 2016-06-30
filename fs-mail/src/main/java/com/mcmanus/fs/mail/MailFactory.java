package com.mcmanus.fs.mail;

import com.mcmanus.fs.model.jpa.Player;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailFactory {

	//@Autowired
	//private VelocityEngine velocityEngine;

	//@Autowired
	//private JavaMailSender mailSender;

	public void sendNewAccount(Player player, String platformBaseUrl, String token) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", player);
		model.put("mailRecoveryUrl", platformBaseUrl + "/#/recovery/" + URLEncoder.encode(token, "UTF-8"));
		String text = null;//VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/new-account.vm", "UTF-8", model);
		//this.mailSender.send(new MailPreparator(player.getMail(), "Création de compte", text));
	}


}
