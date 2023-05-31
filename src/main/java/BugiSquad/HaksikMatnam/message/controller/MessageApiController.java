package BugiSquad.HaksikMatnam.message.controller;

import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.message.etc.SubscriptionPostDto;
import BugiSquad.HaksikMatnam.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageApiController {

    private final MessageService messageService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/publicKey")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(messageService.getPublicKey());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<NoDataResponse> subscribe(@Valid @RequestBody SubscriptionPostDto subscriptionPostDto,
                                                    @RequestHeader("accessToken") String token) {
        return messageService.subscribe(subscriptionPostDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<NoDataResponse> unsubscribe(@RequestParam String endpoint,
                                      @RequestHeader("accessToken") String token) {
        return messageService.unsubscribe(endpoint, jwtTokenProvider.getUserPk(token));
    }
}
