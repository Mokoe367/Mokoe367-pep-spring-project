package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        return ResponseEntity.ok()
                .body(accountService.register(account));
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthException {
        return ResponseEntity.ok()
                .body(accountService.login(account));
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok()
                .body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.ok()
                .body(messageService.getMessageById(messageId));
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        return ResponseEntity.ok()
                .body(messageService.createMessage(newMessage));
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        return ResponseEntity.ok()
                .body(messageService.updateMessage(messageId, message));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        int rowsAffected = messageService.deleteMessage(messageId);
        if(rowsAffected > 0) {
            return ResponseEntity.ok()
                    .body(rowsAffected);
        } else {
            return ResponseEntity.ok()
                    .body(null);
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable Integer accountId) {
        return ResponseEntity.ok()
                .body(messageService.getAllUserMessages(accountId));
    }
}
