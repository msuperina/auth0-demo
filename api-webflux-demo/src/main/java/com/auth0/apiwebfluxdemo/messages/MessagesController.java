package com.auth0.apiwebfluxdemo.messages;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
class MessagesController {
    private final List<Message> messages = new ArrayList<>();

    @GetMapping
    @CrossOrigin
    @PreAuthorize("hasAuthority('SCOPE_read:messages')")
    Mono<List<Message>> getMessages() {
        return Mono.just(new ArrayList<>(messages));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_write:messages')")
    Mono<Message> postMessage(@RequestBody Message message) {
        messages.add(message);
        return Mono.just(message);
    }
}