package BugiSquad.HaksikMatnam.order.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class MenuOrdersItem extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "menu_orders_item_id")
    private Long id;
    @Column(name = "item_counts")
    private int count;
    private int sumPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public MenuOrdersItem(int count, int sumPrice, Orders orders, Menu menu) {
        this.count = count;
        this.sumPrice = sumPrice;
        this.orders = orders;
        this.menu = menu;
    }
}
