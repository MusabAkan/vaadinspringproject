package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.dtos.TypeAddresDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Mail gönderim işlemleri için kullanılan yardımcı sınıf
 *
 * @author Musakan
 */

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Boolean sendMail(TypeAddresDto[] typeAddres, String content, String subject, File file) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content, "text/html");

            for (TypeAddresDto typeAddress : typeAddres) {
                mimeMessage.addRecipient(typeAddress.getType(), typeAddress.getAddress());
            }

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            FileSystemResource resource = new FileSystemResource(file);
            mimeMessageHelper.addAttachment(resource.getFilename(), resource);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sendMail(TypeAddresDto[] typeAddres, String content, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML olarak gönder

            for (TypeAddresDto typeAddress : typeAddres) {
                helper.addTo(typeAddress.getAddress().toString()); // Alternatif
            }

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}



