package com.auth0.regularwebapplicationdemo.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/messages")
class MessagesController {
    @Autowired
    @Qualifier("DemoApiWebClient")
    private WebClient demoApiWebClient;

    @GetMapping
    Mono<String> getMessages(final Model model) {
        return demoApiWebClient
                .get()
                .uri("/api/messages")
                .retrieve()
                .bodyToFlux(Message.class)
                .collectList()
                .doOnNext(messages -> model.addAttribute("messages", messages))
                .map(messages -> "messages");
    }
}
