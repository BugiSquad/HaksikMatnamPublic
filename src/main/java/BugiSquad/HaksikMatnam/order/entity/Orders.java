package BugiSquad.HaksikMatnam.order.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.order.etc.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;
    private int totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @OneToMany(mappedBy = "orders")
    private List<MenuOrdersItem> menuOrdersItemList;

    public void completeOrder() {
        this.orderType = OrderType.COMPLETE;
    }

    public void initTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void initMenuOrderItemList(List<MenuOrdersItem> list) {
        this.menuOrdersItemList = list;
    }

    public void changeOrderType(OrderType type){
        this.orderType = type;
    }

    public Orders(OrderType orderType, Member member, Payment payment) {
        this.orderType = orderType;
        this.member = member;
        this.payment = payment;
    }
}
