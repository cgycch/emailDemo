package com.cch.common;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailWithAttachment {
	private JavaMailSender mailSender; // 必须使用 JavaMailSender

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void send() throws MessagingException, IOException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setFrom("hq@mail.com");
		helper.setTo("wj@mail.com");

		helper.setSubject("哈哈哈");
		helper.setText("每日一笑，开开心心！！！");
		// 添加附件1
		ClassPathResource file1 = new ClassPathResource("/cn/bdqn/attachfiles/test.doc");
		helper.addAttachment(file1.getFilename(), file1.getFile());
		// 添加附件2：附件的文件名为中文时，需要对文件名进行编码转换，解决乱码问题
		ClassPathResource file2 = new ClassPathResource("/cn/bdqn/attachfiles/附件测试文件.doc");
		helper.addAttachment(MimeUtility.encodeWord(file2.getFilename()), file2.getFile());
		mailSender.send(mimeMessage);
	}
	
	public static void main(String[] args) throws MessagingException, IOException {
		JavaMailSender sender = new JavaMailSenderImpl();
		//sender setting pro
		//.... 可以通过@Bean/xml 配置注入
		//host,port/username/password/defaultEncoding,JavaMainlProperties
		MailWithAttachment mail = new MailWithAttachment();
		mail.setMailSender(sender);
		mail.send();
		System.out.println("ok");
	}
}
