package com.example.study.repository;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.enumclass.OrderStatus;
import com.example.study.model.enumclass.OrderType;
import com.example.study.model.enumclass.PaymentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderGroupRepositoryTest {

    @Autowired
    OrderGroupRepository orderGroupRepository;

    @Test
    public void create(){
        OrderGroup orderGroup =
                OrderGroup.builder()
                        .status(OrderStatus.ONGOING)
                        .orderType(OrderType.ALL)
                        .revAddress("서울시 강남구")
                        .revName("홍길동")
                        .paymentType(PaymentType.CARD)
                        .totalPrice(BigDecimal.valueOf(900000))
                        .totalQuantity(1)
                        .orderAt(LocalDateTime.now().minusDays(2))
                        .arrivalDate(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .createdBy("AdminServer")
                        //.userId(1L)
                        .build();
        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        assertNotNull(newOrderGroup);
    }
}