package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.dtos.TypeAddresDto;
import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.PasswordResetToken;
import com.mskn.vaadinspringproject.backend.repositories.IPasswordResetTokenRepository;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import com.mskn.vaadinspringproject.backend.services.base.BaseServiceImpl;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl extends BaseServiceImpl<PasswordResetToken> implements IPasswordResetService {

    private final IPasswordResetTokenRepository tokenRepository;
    private final IMailService mailService;

    @Value("${app.reset-password.base-url}")
    private String resetBaseUrl;

    public PasswordResetServiceImpl(IPasswordResetTokenRepository tokenRepository, IMailService mailService) {
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    @Override
    protected IBaseRepository<PasswordResetToken> getRepository() {
        return tokenRepository;
    }

    /**
     * Kullanıcı için yeni bir şifre sıfırlama token'ı oluşturur veya mevcut token'ı günceller.
     */
    @Override
    @Transactional
    public void createResetToken(AppUser user) {
        String newToken = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(1);

        // Mevcut token varsa güncelle, yoksa yeni oluştur
        PasswordResetToken resetToken = tokenRepository.findByUser(user)
                .map(existing -> {
                    existing.setToken(newToken);
                    existing.setExpiryDate(expiry);
                    return existing;
                })
                .orElseGet(() -> new PasswordResetToken(newToken, user, expiry));

        tokenRepository.save(resetToken);

        // E-posta içeriği
        String resetLink = resetBaseUrl + "?token=" + newToken;
        String content = "<p>Merhaba " + user.getUsername() + ",</p>"
                + "<p>Şifrenizi sıfırlamak için aşağıdaki bağlantıya tıklayın:</p>"
                + "<p><a href=\"" + resetLink + "\">Şifreyi Sıfırla</a></p>"
                + "<p>Bu bağlantı yalnızca <strong>1Q dakika</strong> geçerlidir.</p>";

        try {
            TypeAddresDto address = new TypeAddresDto(new InternetAddress(user.getEmail()), Message.RecipientType.TO);
            mailService.sendMail(new TypeAddresDto[]{address}, content, "Şifre Sıfırlama Talebi");
        } catch (Exception e) {
            e.printStackTrace(); // Geliştirme ortamında loglama yapılabilir
        }
    }

    /**
     * Token geçerliyse kullanıcıyı döner.
     */
    @Override
    public Optional<AppUser> validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> !t.isExpired())
                .map(PasswordResetToken::getUser);
    }

    /**
     * Token'ı geçersiz kılar (silme işlemi).
     */
    @Override
    public void invalidateToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
