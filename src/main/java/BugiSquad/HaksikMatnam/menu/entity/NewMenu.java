package BugiSquad.HaksikMatnam.menu.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class NewMenu extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "new_menu_id")
    private Long id;
    private String name;
    private String detail;
    private String imageUrl;
    private int votes;
    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDetail(String detail) {
        this.detail = detail;
    }

    public void changeImageUrl(String url) {
        this.imageUrl = url;
    }

    public void changeMenuCategory(MenuCategory category) {
        this.category = category;
    }

    public void increaseVotes() {
        this.votes += 1;
    }

    public NewMenu(String name, String detail, String imageUrl, int votes, MenuCategory category) {
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.votes = votes;
        this.category = category;
    }
}
