package com.example.study.model.entity;

import com.example.study.model.enumclass.ItemStatus;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"orderDetailList","partner"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemStatus status;  //등록 /해지 / 검수중

    @NotNull
    private String name;
    @NotNull
    private String title;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String content;

    private String brandName;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private String updatedBy;

    //item : partner = N : 1
    @ManyToOne
    private Partner partner;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "item")
    private List<OrderDetail> orderDetailList;

}
