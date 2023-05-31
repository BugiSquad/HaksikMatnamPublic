package BugiSquad.HaksikMatnam.message.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.message.entity.Subscriptions;
import BugiSquad.HaksikMatnam.message.etc.SubscriptionPostDto;
import BugiSquad.HaksikMatnam.message.repository.SubscriptionsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    @Value("${vapid.public-key}")
    private String publicKey;
    @Value("${vapid.private-key}")
    private String privateKey;
    private PushService pushService;
    private final SubscriptionsRepository subscriptionsRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Transactional
    public ResponseEntity<NoDataResponse> subscribe(SubscriptionPostDto subscriptionPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Optional<Subscriptions> findSub = subscriptionsRepository.findByMemberId(member.getId());
        if (findSub.isEmpty()) {
            log.info("{} is subscribed to {}", member.getName(), subscriptionPostDto.getEndpoint());
            subscriptionsRepository.save(new Subscriptions(subscriptionPostDto.getEndpoint(), subscriptionPostDto.getAuth(), subscriptionPostDto.getP256dh(), member.getId()));
        } else {
            log.info("{}'s subscriptions is changed", member.getName());
            log.info("changed subs endpoint = {}", subscriptionPostDto.getEndpoint());
            Subscriptions subscriptions = findSub.get();
            subscriptions.updateAllField(subscriptionPostDto.getEndpoint(), subscriptionPostDto.getAuth(), subscriptionPostDto.getP256dh());
        }

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> unsubscribe(String endpoint, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Subscriptions subscriptions = subscriptionsRepository.findByEndpoint(endpoint)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_SUBSCRIPTION));
        if (!member.getId().equals(subscriptions.getMemberId())) {
            throw new CustomException(ErrorCode.INVALID_USER_SUBSCRIPTION);
        }
        log.info("{} is unsubscribed from {}", member.getName(), endpoint);
        subscriptionsRepository.delete(subscriptions);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public void sendNotification(Long memberId, String payload) {

        Optional<Subscriptions> findSubs = subscriptionsRepository.findByMemberId(memberId);
        if (findSubs.isPresent()) {
            Subscriptions subscriptions = findSubs.get();
            Subscription subscription = new Subscription(subscriptions.getEndpoint(), new Subscription.Keys(subscriptions.getP256dh(), subscriptions.getAuth()));
            //이게 없으면 애플디바이스는 전송불가능
            pushService.setSubject("mailto:mail@example.com");
            try {
                pushService.send(new Notification(subscription, payload));
            } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            log.info("{}번 회원의 구독이 존재하지 않습니다. 구독 여부를 확인해주세요.", memberId);
        }
    }
}
