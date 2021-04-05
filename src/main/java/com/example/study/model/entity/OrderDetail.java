package com.example.study.model.entity;

import com.example.study.model.enumclass.OrderStatus;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity //자동적으로 sql 의 order_detail 과 연결됨.
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"orderGroup","item"})    //무한히 user와 item이 불리는것을 막기 위함.
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @NotNull
    private LocalDateTime arrivalDate;

    private Integer quantity;

    private BigDecimal totalPrice;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private String updatedBy;
    // orderDetail : orderGroup = N : 1
    // 연관관계 설정시 객체 속성으로 설정하면 자동으로 hibernate가 user id를 찾아가게됨.
    @ManyToOne
    private OrderGroup orderGroup;

    // orderDetail : item = N : 1
    @ManyToOne
    private Item item;
}
