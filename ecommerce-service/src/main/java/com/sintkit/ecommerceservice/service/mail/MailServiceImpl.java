package com.sintkit.ecommerceservice.service.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sintkit.ecommerceservice.config.AppProperties;
import com.sintkit.ecommerceservice.model.User;

import freemarker.template.Configuration;

/**
 * @author hp
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LogManager.getLogger(getClass());
    private static final String SUPPORT_EMAIL = "support.email";
    public final static String BASE_URL = "baseUrl";

    @Autowired
    private MessageService messageService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    Configuration freemarkerConfiguration;

    @Autowired
    AppProperties appProperties;

    @Async
    @Override
    public void sendVerificationToken(String token, User user) {
        final String confirmationUrl = appProperties.getClient().getBaseUrl() + "verify?token=" + token;
        final String message = messageService.getMessage("message.mail.verification");
        System.out.println(message);
        sendHtmlEmail(message, confirmationUrl, user);
    }

    private String geFreeMarkerTemplateContent(Map<String, Object> model) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("mail/verification.ftl"), model));
            return content.toString();
        } catch (Exception e) {
            System.out.println("Đã xảy ra trường hợp ngoại lệ khi xử lý fmtemplate:" + e.getMessage());
        }
        return "";
    }

    private void sendHtmlEmail(String msg, String confirmationHref, User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getDisplayName());
        model.put("msg", msg);
        model.put("href", confirmationHref);
        model.put("title", "Xác thực tài khoản");
        model.put(BASE_URL, appProperties.getClient().getBaseUrl());
        try {
            sendHtmlMail(env.getProperty(SUPPORT_EMAIL), user.getEmail(), "Xác thực tài khoản", geFreeMarkerTemplateContent(model));
        } catch (MessagingException e) {
            logger.error("Gửi thư không thành công", e);
        }
    }

    public void sendHtmlMail(String from, String to, String subject, String body) throws MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setFrom(from);
        if (to.contains(",")) {
            helper.setTo(to.split(","));
        } else {
            helper.setTo(to);
        }
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(mail);
        logger.info("Gửi thư: {0}", subject);
    }
}
