package com.bcc.controller;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcc.config.Routes;
import com.bcc.config.captcha.ICaptchaService;
import com.bcc.dto.ContactDto;
import com.bcc.service.GenericResponse;

@Controller
public class MainController {
	
	@Autowired
    private ICaptchaService captchaService;
		
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private Environment env;

	@GetMapping(Routes.INDEX)
	public String GetIndex()
	{
		return "index";
	}
		
	@PostMapping(Routes.CONTACT)
	@ResponseBody
	public GenericResponse contact(@Valid ContactDto userForm, HttpServletRequest request) {
				
		final String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(response);

        try {
        		mailSender.send(constructWelcome(userForm));
        		mailSender.send(constructContact(userForm));
        } catch(Exception ex) {
        		System.out.println(ex.getMessage());
        		return new GenericResponse("Error Message", ex.getMessage());
        }
		
        return new GenericResponse("success");
	}
	
	private MimeMessage constructWelcome(final ContactDto user) throws Exception {
		
        final String message = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Contact Sent</title> <style type='text/css'> body{padding-top: 0 !important; padding-bottom: 0 !important; padding-top: 0 !important; padding-bottom: 0 !important; margin:0 !important; width: 100% !important; -webkit-text-size-adjust: 100% !important; -ms-text-size-adjust: 100% !important; -webkit-font-smoothing: antialiased !important;}.tableContent img{border: 0 !important; display: block !important; outline: none !important;}a{color:#382F2E;}p, h1{color:#382F2E; margin:0;}p{text-align:left; color:#999999; font-size:14px; font-weight:normal; line-height:19px;}a.link1{color:#382F2E;}a.link2{font-size:16px; text-decoration:none; color:#ffffff;}h2{text-align:left; color:#222222; font-size:19px; font-weight:normal;}div,p,ul,h1{margin:0;}.bgBody{background: #ffffff;}.bgItem{background: #ffffff;}@media only screen and (max-width:480px){table[class='MainContainer'], td[class='cell']{width: 100% !important;height:auto !important;}td[class='specbundle']{width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}td[class='spechide']{display:none !important;}img[class='banner']{width: 100% !important; height: auto !important;}td[class='left_pad']{padding-left:15px !important;padding-right:15px !important;}}@media only screen and (max-width:540px){table[class='MainContainer'], td[class='cell']{width: 100% !important;height:auto !important;}td[class='specbundle']{width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}td[class='spechide']{display:none !important;}img[class='banner']{width: 100% !important; height: auto !important;}.font{font-size:18px !important;line-height:22px !important;}.font1{font-size:18px !important;line-height:22px !important;}}</style><script type='colorScheme' class='swatch active'>{'name':'Default', 'bgBody':'ffffff', 'link':'382F2E', 'color':'999999', 'bgItem':'ffffff', 'title':'222222'}</script> </head> <body paddingwidth='0' paddingheight='0' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0'> <table bgcolor='#ffffff' width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent' align='center' style='font-family:Helvetica, Arial,serif;'> <tbody> <tr> <td><table width='600' border='0' cellspacing='0' cellpadding='0' align='center' bgcolor='#ffffff' class='MainContainer'> <tbody> <tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' width='40'>&nbsp;</td><td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='75' class='spechide'></td></tr><tr> <td class='movableContentContainer ' valign='top'> <div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='35'></td></tr><tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' align='right' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable'> <p style='text-align:right;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#222222;'><span class='specbundle2'><span class='font1'>Contact Sent&nbsp;</span></span></p></div></div></td><td valign='top' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable'> <p style='text-align:left;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#1A54BA;'><span class='font'>Bill Cooke Creative</span> </p></div></div></td></tr></tbody></table></td></tr></tbody></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'> <tr> <td valign='top' align='center'> <div class='contentEditableContainer contentImageEditable'> <div class='contentEditable'> <img src=\"cid:fullsize.png\" height='100' alt='' data-default='placeholder' data-max-width='560'> </div></div></td></tr></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'> <tr><td height='55'></td></tr><tr> <td align='left'> <div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <h2 >Thanks for reaching out!</h2> </div></div></td></tr><tr><td height='15'> </td></tr><tr> <td align='left'> <div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <p > " + user.getName() + ", <br><br>We will be contacting you soon to get more information. <br><br>Please follow me on <a target='_blank' href='https://www.facebook.com/'>Facebook</a> and <a target='_blank' href='https://www.instagram.com/'>Instagram</a>. <br><br><span style='color:#222222;'>Bill Cooke</span> </p></div></div></td></tr><tr><td height='20'></td></tr></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='65'> </tr><tr> <td style='border-bottom:1px solid #DDDDDD;'></td></tr><tr><td height='25'></td></tr><tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <p style='text-align:left;color:#CCCCCC;font-size:12px;font-weight:normal;line-height:20px;'> <span style='font-weight:bold;'>Bill Cooke Creative</span> </p></div></div></td><td valign='top' width='30' class='specbundle'>&nbsp;</td><td valign='top' class='specbundle'><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' width='52'> <div class='contentEditableContainer contentFacebookEditable'> <div class='contentEditable'> <a target='_blank' href='https://www.facebook.com/'><img src='cid:facebook_logo.png' width='52' height='53' alt='facebook icon' data-default='placeholder' data-max-width='52' data-customIcon='true'></a> </div></div></td><td valign='top' width='16'>&nbsp;</td><td valign='top' width='52'> <div class='contentEditableContainer contentTwitterEditable'> <div class='contentEditable'> <a target='_blank' href='https://www.instagram.com/'><img src='cid:instagram_logo.png' width='52' height='53' alt='twitter icon' data-default='placeholder' data-max-width='52' data-customIcon='true'></a> </div></div></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td height='88'></td></tr></tbody></table> </div></td></tr></tbody></table></td><td valign='top' width='40'>&nbsp;</td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table> </body> </html>";
        return constructEmail("Contact Sent", message, user.getEmail());        
    }
	
	private MimeMessage constructContact(final ContactDto user) throws Exception {
		final String message = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>" + user.getSubject() + "</title> <style type='text/css'> body{padding-top: 0 !important; padding-bottom: 0 !important; padding-top: 0 !important; padding-bottom: 0 !important; margin:0 !important; width: 100% !important; -webkit-text-size-adjust: 100% !important; -ms-text-size-adjust: 100% !important; -webkit-font-smoothing: antialiased !important;}.tableContent img{border: 0 !important; display: block !important; outline: none !important;}a{color:#382F2E;}p, h1{color:#382F2E; margin:0;}p{text-align:left; color:#999999; font-size:14px; font-weight:normal; line-height:19px;}a.link1{color:#382F2E;}a.link2{font-size:16px; text-decoration:none; color:#ffffff;}h2{text-align:left; color:#222222; font-size:19px; font-weight:normal;}div,p,ul,h1{margin:0;}.bgBody{background: #ffffff;}.bgItem{background: #ffffff;}@media only screen and (max-width:480px){table[class='MainContainer'], td[class='cell']{width: 100% !important;height:auto !important;}td[class='specbundle']{width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}td[class='spechide']{display:none !important;}img[class='banner']{width: 100% !important; height: auto !important;}td[class='left_pad']{padding-left:15px !important;padding-right:15px !important;}}@media only screen and (max-width:540px){table[class='MainContainer'], td[class='cell']{width: 100% !important;height:auto !important;}td[class='specbundle']{width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}td[class='spechide']{display:none !important;}img[class='banner']{width: 100% !important; height: auto !important;}.font{font-size:18px !important;line-height:22px !important;}.font1{font-size:18px !important;line-height:22px !important;}}</style><script type='colorScheme' class='swatch active'>{'name':'Default', 'bgBody':'ffffff', 'link':'382F2E', 'color':'999999', 'bgItem':'ffffff', 'title':'222222'}</script> </head> <body paddingwidth='0' paddingheight='0' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0'> <table bgcolor='#ffffff' width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent' align='center' style='font-family:Helvetica, Arial,serif;'> <tbody> <tr> <td><table width='600' border='0' cellspacing='0' cellpadding='0' align='center' bgcolor='#ffffff' class='MainContainer'> <tbody> <tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' width='40'>&nbsp;</td><td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='75' class='spechide'></td></tr><tr> <td class='movableContentContainer ' valign='top'> <div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='35'></td></tr><tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' align='right' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable'> <p style='text-align:right;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#222222;'><span class='specbundle2'><span class='font1'>Contact Received&nbsp;</span></span></p></div></div></td><td valign='top' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable'> <p style='text-align:left;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#1A54BA;'><span class='font'>Bill Cooke Creative</span> </p></div></div></td></tr></tbody></table></td></tr></tbody></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'> <tr> <td valign='top' align='center'> <div class='contentEditableContainer contentImageEditable'> <div class='contentEditable'> <img src='cid:fullsize.png' height='100' alt='' data-default='placeholder' data-max-width='560'> </div></div></td></tr></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'> <tr><td height='55'></td></tr><tr> <td align='left'> <div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <h2 >You got contacted!</h2> </div></div></td></tr><tr><td height='15'> </td></tr><tr> <td align='left'> <div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <p > Hey, <br><br>" + user.getName() + " at " + user.getEmail() + " is contacting you. <br><br><b>" + user.getSubject() + "</b> <br><hr> <p> " + user.getMessage() + " </p><br></p></div></div></td></tr><tr><td height='20'></td></tr></table> </div><div class='movableContent' style='border: 0px; padding-top: 0px; position: relative;'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td height='65'> </tr><tr> <td style='border-bottom:1px solid #DDDDDD;'></td></tr><tr><td height='25'></td></tr><tr> <td><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' class='specbundle'><div class='contentEditableContainer contentTextEditable'> <div class='contentEditable' align='center'> <p style='text-align:left;color:#CCCCCC;font-size:12px;font-weight:normal;line-height:20px;'> <span style='font-weight:bold;'>Bill Cooke Creative</span> </p></div></div></td><td valign='top' width='30' class='specbundle'>&nbsp;</td><td valign='top' class='specbundle'><table width='100%' border='0' cellspacing='0' cellpadding='0'> <tbody> <tr> <td valign='top' width='52'> <div class='contentEditableContainer contentFacebookEditable'> <div class='contentEditable'> <a target='_blank' href='https://www.facebook.com/'><img src='cid:facebook_logo.png' width='52' height='53' alt='facebook icon' data-default='placeholder' data-max-width='52' data-customIcon='true'></a> </div></div></td><td valign='top' width='16'>&nbsp;</td><td valign='top' width='52'> <div class='contentEditableContainer contentTwitterEditable'> <div class='contentEditable'> <a target='_blank' href='https://www.instagram.com/'><img src='cid:instagram_logo.png' width='52' height='53' alt='twitter icon' data-default='placeholder' data-max-width='52' data-customIcon='true'></a> </div></div></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td height='88'></td></tr></tbody></table> </div></td></tr></tbody></table></td><td valign='top' width='40'>&nbsp;</td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table> </body> </html>";
		return constructEmail("Contact Received", message, env.getProperty("support.toemail"));
    }
	
	private MimeMessage constructEmail(String subject, String body, String to) throws Exception {
		
		MimeMessage message = mailSender.createMimeMessage();	      
	    MimeMessageHelper email;
		try {			
			email = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	        email.setSubject(subject);
	        email.setTo(to);	        
	        email.setFrom(env.getProperty("support.email"));
	        email.setText(body, true);
	        	        
	        // Add logos
	        email.addInline("fullsize.png", new ClassPathResource("/static/app/images/fullsize.png"));
	        email.addInline("facebook_logo.png", new ClassPathResource("/static/app/images/facebook_logo.png"));
	        email.addInline("instagram_logo.png", new ClassPathResource("/static/app/images/instagram_logo.png"));	        
	        
		} catch (MessagingException e) {
			throw new Exception("Message could not be sent");
		}
        
        return message;
    }
}
