package BugiSquad.HaksikMatnam.information.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Information extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "information_id")
    private Long id;
    private String title;
    private String text;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateText(String text) {
        this.text = text;
    }

    @Builder
    public Information(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
