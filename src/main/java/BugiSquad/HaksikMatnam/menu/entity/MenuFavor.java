package BugiSquad.HaksikMatnam.menu.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MenuFavor extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "menu_favor_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    private int countsNum;
    private int countsYear;
    private int countsMonth;

    public void plusCount(int sum) {
        this.countsNum = countsNum + sum;
    }

    public MenuFavor(Menu menu, int count, int year, int month) {
        this.menu = menu;
        this.countsNum = count;
        this.countsYear = year;
        this.countsMonth = month;
    }
}
