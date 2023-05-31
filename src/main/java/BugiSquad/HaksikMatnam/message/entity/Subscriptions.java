package BugiSquad.HaksikMatnam.message.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Subscriptions extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;
    @Column(nullable = false)
    private String endpoint;
    @Column(nullable = false)
    private String auth;
    @Column(nullable = false)
    private String p256dh;
    @Column(nullable = false)
    private Long memberId;

    public void updateAllField(String endpoint, String auth, String p256dh) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
    }

    public Subscriptions(String endpoint, String auth, String p256dh, Long memberId) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
        this.memberId = memberId;
    }
}
