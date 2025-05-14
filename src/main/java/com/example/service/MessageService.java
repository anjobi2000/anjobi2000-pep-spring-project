package com.example.service;

import java.util.Optional;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

//import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
//import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {
    
    @Autowired
    MessageRepository messageRepo;

    @Autowired
    AccountRepository accountRepo;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || 
            message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
        //if (accountRepo.findByUsername(String.valueOf(message.getPostedBy())) == null) {
         //   throw new IllegalArgumentException("User does not exist");

         if (!accountRepo.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User does not exist");
        }
        return messageRepo.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Message getMessageById(int id) {
        //return messageRepo.findById(id).get();
        return messageRepo.findById(id).orElse(null);
    }

    public boolean deleteMessageById(int id) {
        //messageRepo.deleteById(id);
        //if (!messageRepo.existsById(id)) {
            //throw new IllegalArgumentException("Message not found");
        //}
        //messageRepo.deleteById(id);

        if (messageRepo.existsById(id)) {
            messageRepo.deleteById(id);
            return true;
        }
        return false;
    }
    
    public int updateMessage(int id, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
        Optional<Message> optionalMessage = messageRepo.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newText);
            messageRepo.save(message);
            return 1; // success
        } else {
            return 0; // message not found
        }
        
    }
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepo.findMessagesByPostedBy(accountId);
    }
    
}
