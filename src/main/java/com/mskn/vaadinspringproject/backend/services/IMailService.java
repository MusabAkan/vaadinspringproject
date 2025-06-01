package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.dtos.TypeAddresDto;

import java.io.File;

public interface IMailService {

    Boolean sendMail(TypeAddresDto[] typeAddres, String content, String subject, File file);

    Boolean sendMail(TypeAddresDto[] typeAddres, String content, String subject);
}
