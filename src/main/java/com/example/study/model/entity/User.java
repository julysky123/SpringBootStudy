package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Accessors(chain = true)    //update시 set구문을 체인해서 업데이트 하는데에 사용.
@ToString(exclude ={"orderGroupList"})
//@Table(name = "user") 테이블 이름과 같으면 굳이 선언하지 않아도 됨.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    //@Column(name="account") 칼럼명과 같으면 선언하지 않아도됨.
    private String account;
    @NotNull
    private String password;

    @NotNull
    //Enum의 숫자형이 아닌 Enum의 name이 저장된다.
    @Enumerated(EnumType.STRING)
    private UserStatus status;  //REGISTERED / UNREGISTERTED / WAITING 등..

    private String email;
    @NotNull
    private String phoneNumber;

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

    //orderDetail에 user 라고 선언한 변수와 매칭시키겠다.
    //LAZY : 지연 로딩 : select * from item where id = ?  추천!
    //EAGER : 즉시 로딩 :
    // item_id = order_group.item_id
    // user_id = order_group.user_id   //연관관계가 있는 모든 데이터를 조인으로 찾음 (성능 저하 위험)
    // where item.id = ?
    // 1:1 에서는 EAGER 사용 나머지는 LAZY 쓰기.
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "user")
    private List<OrderGroup> orderGroupList;
}

