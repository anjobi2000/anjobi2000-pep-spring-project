package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
//import java.util.Optional;
import java.util.Map;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//@RequestMapping("/api")
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account registered = accountService.registerAccount(account);
            return ResponseEntity.ok(registered);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<Account>> login(@RequestBody Account account) {
        //try {
          //  return ResponseEntity.ok(accountService.login(account));
        //} catch (SecurityException e) {
         //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       // }

       Optional<Account> result = accountService.login(account);
    if (result.isPresent()) {
        return ResponseEntity.ok(result);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("/messages/{message_id}")
    //public ResponseEntity<?> deleteMessage(@PathVariable int message_id) {
        // messageService.deleteMessageById(message_id);
        //return ResponseEntity.ok().build(); 

        public ResponseEntity<?> deleteMessage(@PathVariable int message_id) {
            boolean deleted = messageService.deleteMessageById(message_id);
            if (deleted) {
                return ResponseEntity.ok(1); // message deleted successfully
            } else {
                return ResponseEntity.ok().build(); // 200 OK with no body
            }
    }

    @PatchMapping("/messages/{message_id}")
    //public ResponseEntity<?> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
       // try {
          // int rows = messageService.updateMessage(message_id, message.getMessageText());
          // return ResponseEntity.ok(rows);
        //} catch (IllegalArgumentException e) {
         //  return ResponseEntity.badRequest().build();
        //}

        public ResponseEntity<?> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
            try {
                int rows = messageService.updateMessage(message_id, message.getMessageText());
                if (rows == 0) {
                    return ResponseEntity.badRequest().build(); 
                }
                return ResponseEntity.ok(rows);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        
    }

    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessagesByAccountId(@PathVariable int account_id) {
        return messageService.getMessagesByAccountId(account_id);
    }
    
}

