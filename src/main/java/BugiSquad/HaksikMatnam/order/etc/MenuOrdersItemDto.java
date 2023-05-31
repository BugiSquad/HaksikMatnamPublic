package BugiSquad.HaksikMatnam.order.etc;

import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MenuOrdersItemDto {

    private Long menuOrdersItemId;
    private int count;
    private int sumPrice;
    private Long orderId;
    private Long menuId;


    public MenuOrdersItemDto(Long menuOrdersItemId, int count, int sumPrice, Long orderId, Long menuId) {
        this.menuOrdersItemId = menuOrdersItemId;
        this.count = count;
        this.sumPrice = sumPrice;
        this.orderId = orderId;
        this.menuId = menuId;
    }
}
