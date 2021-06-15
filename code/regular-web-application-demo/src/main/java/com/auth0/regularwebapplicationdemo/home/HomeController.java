package com.auth0.regularwebapplicationdemo.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/")
class HomeController {
    @GetMapping
    Mono<String> getHome(final Model model, @AuthenticationPrincipal final OidcUser principal) {
        if (principal == null) {
            return Mono.just("index");
        } else {
            model.addAttribute("profile", principal.getClaims());
            model.addAttribute("claimsKeys", principal.getClaims().keySet().toString());
            return Mono.just("profile");
        }
    }
}
