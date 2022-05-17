package ru.sergeysemenov.webmarketspring.core.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class Order{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "id")
    private List<OrderItem> orderItemsList;

    @Column(name = "address")
    private String address;

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("User:" + username + " ");
       sb.append("ordered: ");
       for (OrderItem item:orderItemsList) {
           sb.append("prod_id: " + item.getProduct().getId() + " qty " + item.getQuantity() + " price" + item.getPrice() + "; ");
       }
       sb.append("total price: "+ totalPrice);
       return sb.toString();
    }

}
