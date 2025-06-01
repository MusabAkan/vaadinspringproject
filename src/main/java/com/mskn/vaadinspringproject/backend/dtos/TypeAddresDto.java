package com.mskn.vaadinspringproject.backend.dtos;

import jakarta.mail.Address;
import jakarta.mail.Message;

public record TypeAddresDto(Address address, Message.RecipientType type) {
    public TypeAddresDto(Address address, Message.RecipientType type) {
        this.address = address;
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public Message.RecipientType getType() {
        return type;
    }
}
