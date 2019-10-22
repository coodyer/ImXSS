package org.coody.framework.util;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.coody.framework.context.base.BaseLogger;


public class EmailSenderUtil {

	public static ConcurrentHashMap<String, Session> sessionPool=new ConcurrentHashMap<String, Session>();
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(EmailSenderUtil.class);
	
	
	public static boolean sendEmail(String smtp, String email,
			String password, String title, String txt, String targeEmail) {
		// 用刚刚设置好的props对象构建一个session
		Session session = getSession(smtp, targeEmail, password);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(email));
			// 加载收件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					targeEmail));
			// 加载标题
			message.setSubject(title);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(txt);
			multipart.addBodyPart(contentPart);
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Transport transport = session.getTransport("smtp");
			try {
				if(!session.getTransport().isConnected()){
					// 连接服务器的邮箱
					transport.connect(smtp, email, password);
				}
				// 把邮件发送出去
				transport.sendMessage(message, message.getAllRecipients());
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Session getSession(String smtp, String email,
			String password){
		String key=EncryptUtil.md5Code(smtp+"|"+email+"|"+password);
		Session session=sessionPool.get(key);
		if(!StringUtil.isNullOrEmpty(session)){
			return session;
		}
		session=createSession(smtp, email, password);
		sessionPool.put(key, session);
		return session;
	}
	
	private static Session createSession(String smtp, String email,
			String password){
		try {
			Properties props = new Properties();
			// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
			props.put("mail.smtp.host", smtp);
			props.put("mail.transport.protocol", "smtp");
			// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// 用刚刚设置好的props对象构建一个session
			Session session = Session.getInstance(props, new Authenticator(){
	             protected PasswordAuthentication getPasswordAuthentication() {
	                 return new PasswordAuthentication(email, password);
	             }});
			// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
			// 用（你可以在控制台（console)上看到发送邮件的过程）
			session.setDebug(true);
			return session;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		}
		
	}
	
	public static boolean connectionTest(String smtp, String email,
			String password){
		Transport transport =null;
		try {
			Session	session = getSession(smtp, email, password);
			transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			if(session.getTransport().isConnected()){
				return true;
			}
			transport.connect(smtp, email, password);
			if(transport.isConnected()){
				return true;
			}
			return false;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return false;
		}
		
	}
}
