package com.example.service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.badFormatException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getAllMessages() { return (List<Message>)messageRepository.findAll(); }

    public Message createMessage(Message message) throws badFormatException, ResourceNotFoundException {
        accountRepository.findById(message.getPostedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Try again"));
        if(!message.getMessageText().isEmpty() && message.getMessageText().length() <= 255) {
            message.setMessageText(message.getMessageText());
            message.setPostedBy(message.getPostedBy());
            message.setTimePostedEpoch(message.getTimePostedEpoch());
        } else {
            throw new badFormatException("Message is not in correct format");
        }
        return messageRepository.save(message);
    }

    public Message getMessageById(Integer Id) {
        return messageRepository.findById(Id)
                .orElse(null);
    }

    public int deleteMessage(Integer Id) {
        return messageRepository.deleteMessage(Id);
    }

    public int updateMessage(Integer messageId, Message newMessage) throws badFormatException, ResourceNotFoundException {
        Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new ResourceNotFoundException("resource not found try again."));
        if(!newMessage.getMessageText().isEmpty() && newMessage.getMessageText().length() <= 255) {
            return messageRepository.updateMessage(newMessage.getPostedBy(),
                   newMessage.getMessageText(),newMessage.getTimePostedEpoch(), message.getMessageId());
        } else {
            throw new badFormatException("Message is not in correct format");
        }
    }

    public List<Message> getAllUserMessages(Integer accountId) {
        return (List<Message>) messageRepository.findMessagesPostedByUser(accountId);
    }
}
