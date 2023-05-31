package BugiSquad.HaksikMatnam.member.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private double rating;
    private String text;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;


    public Review changeRating(double rating) {
        this.rating = rating;
        return this;
    }

    public Review changeText(String text) {
        this.text = text;
        return this;
    }

    public Review changeTitle(String title) {
        this.title = title;
        return this;
    }

    public Review(double rating, String text, String title, Member member, Menu menu) {
        this.rating = rating;
        this.text = text;
        this.title = title;
        this.member = member;
        this.menu = menu;
    }
}
