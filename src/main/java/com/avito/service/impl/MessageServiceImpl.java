package com.avito.service.impl;

import com.avito.models.Message;
import com.avito.repository.MessageRepository;
import com.avito.service.interfaces.MessageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    private final MessageRepository messageRepository;

    @Override
    public long getCountUnreadMessagesForUserId(Long id){
        return messageRepository.countMessagesByReadStatusAndRecipientId(Message.ReadStatus.NOT_READ, id);

    }
}